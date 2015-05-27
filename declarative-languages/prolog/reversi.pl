/*
An assignment for D7012E, Declarative Languages.
Jan Schnitker, 2015
jansic-4@student.ltu.se
PGP: 216ECAE9, fingerprint 6275580B659714B935BBA23E5454000C216ECAE9, e.g. from here keys.gnupg.net

Description

A straight forward AI for Reversi aka Othello.
Series of possible moves are tested to and 
the resulting board is evaluated. 
The best move is then chosen.
The player can define how many moves to look ahead,
But the opponent's sight ahead is limited to 1.

To use it, call findbestmove(+Color, +Board, +N, -X, -Y)
with black/white, search-depth N, a board consisting of 
[(black,a,1),(white,b4),...] 
and receive the X,Y coordinates for the, more or less, best move. 
*/

use_module(library(lists)). % list-functionality like nth0

% true if Arg is not a member of the list. from
%http://www.cs.uni-potsdam.de/wv/lehre/Material/Prolog/Eclipse-Doc/bips/lib/lists/nonmember-2.html
non_member(Arg,[Arg|_]) :- !,fail.
non_member(Arg,[_|Tail]) :- !,non_member(Arg,Tail).
non_member(_,[]).

% change colors from white to black or the other way around
swap(white,black).
swap(black,white).

% is the move on playground? (values in range: black/white, a-h, 1-8)
position(P,X,Y) :- member(P, [white,black]) , member(X, [a,b,c,d,e,f,g,h]), member(Y, [1,2,3,4,5,6,7,8]).
position([P,X,Y]) :- position(P,X,Y).

% check if the position is empty / suggest one
posempty(Color, Board, X, Y) :- 
  position(Color, X, Y) ,       % must be a valid position
  non_member((_, X, Y), Board). % position empty

% neighbors: get the coordinates of a neighbor and also the direction Dx/Dy
% this will suggest/test all 8 neighbors
nb(X,Y, Nx, Ny, Dx, Dy) :- 
  member(Dx,[-1,0,1]),               % try different X-offsets (left,middle,right) 
  nth0(Px,  [a,b,c,d,e,f,g,h], X),   % position of X coord in the list
  nth0(Pxe, [a,b,c,d,e,f,g,h], Nx),  % neighbors X coordinate
  member(Dy,[-1,0,1]),               % try different Y-offsets (upper,middle,lower)
  Pxe is Px+Dx,                      % increase/decrease/keep the X-Coordinate
  Ny is Y+Dy,                        % increase/decrease/keep the Y-Coordinate to find the neighbors
  8>=Ny, Ny>=1,                      % Ny e [1,8]
  (Nx,Ny)\= (X,Y).                   % avoid (Nx,Ny)=(X,Y) aka Dx=Dy=0

% go in a direction hopping over enemies, until you encounter a friend
% gather the enemy-stones in the Changes-list. these will change color if the move is made
friendinsight(X, Y, Dx, Dy, FriendlyColor, Board, []) :-        
  nb(X,Y, Nx, Ny, Dx, Dy),                          % get neighbor coordinates in same dir
  member((FriendlyColor, Nx, Ny), Board).           % found friend in sight 
friendinsight(X, Y, Dx, Dy, FriendlyColor, Board, Changes) :-   
  swap(FriendlyColor, EnemyColor),
  nb(X,Y, Nx, Ny, Dx, Dy),                          % get neighbor coordinates in same dir
  member((EnemyColor, Nx, Ny), Board),              % found enemy in row  
  friendinsight(Nx,Ny,Dx,Dy,FriendlyColor, Board, NChanges),  % hop on it, continue searching
  Changes = [(EnemyColor,Nx,Ny) | NChanges].  % add enemy stone to Changes list. (the one to flip)

/* TASK
Define a predicate legalmove(Color, Board, X, Y), that given a color, a board, and
a postion, succeeds if they constitute a legal move according to the rules of Reversi. Observe
that this predicate is useful both for finding coordinates and for determining if a particular
coordinate constitutes a legal move. I.e. X and Y free (output) or bound (input)
*/
legalmove(Color, Board, X, Y) :-  
  posempty(Color, Board, X, Y),                     % check if the position is empty and on the board
  friendinsight(X,Y, _, _, Color, Board, Changes),  % check for stones to be swapped
  Changes \= []. % if no changes, there was no stone to flip. That's not a legal move!

% a set of changes gets applied to the board (list of changes = list of stones to change color)
% this is how it works:
%   a change C looks like (Color, X, Y)
%   C then gets removed from the board, and (Enemy, X, Y) is added.
updateboard(Board, [], Board).
updateboard(Board, [Change|Changes], Newboard) :- 
  delete(Board, Change, CBoard),!,                          % remove flipped stone "Change" from board.
  (C,X,Y)=Change, swap(C,E), CNewboard = [(E,X,Y)|CBoard],  % insert removed stone with changed color to CNewboard
  updateboard(CNewboard, Changes, Newboard).                % continue with removing "Changes"-Stones from Board

/* TASK
Define a predicate makemove(+Color, +Board, +X, +Y, -NewBoard), that computes
the new board given a color, a board, and the x and y coordinates of the move. The
goal should fail if the move is illegal.
*/

% make/propose a move and get the resulting board
makemove(Color, Board, X, Y, NewBoard) :- 
  legalmove(Color, Board, X, Y),                % check if move is legal or generate a legal move
  findall(Changes, friendinsight(X,Y, _, _, Color, Board, Changes), AllChanges),  % stones that are to be changed, in all directions
  flatten(AllChanges,FlatChanges),              % flatten the list of lists of stones to be turned
  updateboard(Board, FlatChanges, NewBoardPre), % apply changes to board (only changes/turned stones!)
  NewBoard = [(Color, X, Y) | NewBoardPre].     % add the last move to the board


/* TASK
Also, define a predicate makemoves(+Color, +Board, +N, -Moves, -NewBoard)
that computes a new board and a list of moves leading to that board given a starting color,
a board and the number of moves to be made. Observe that makemove might fail due to
the fact that the player might not be able to make a move. According to the rules of Reversi,
the turn should go over to the other player. In makemoves, this inability to make a
move should be handled in such way that the element (Color,n,n) should be added to
the list of moves and the no-move should count as any other move. However, the no-move
shall NOT be a possible move if there exists a legal move. As you might already have understood,
the makemoves predicate will never fail.
*/

/*
makemoves tries a moves for the player that started the search,
or takes the best move for the enemy which looks one step ahead.
Then it continues searching for the other player.
*/
makemoves(Color, Board, N, Moves, NewBoard) :-  
  makemoves(Color, Color, Board, N, Moves, NewBoard).

makemoves(_, _,    Board, 0, [],    Board). %basecase
makemoves(Color, OC, Board, N, Moves, NewBoard) :-  
  N>0, Nn is N-1, 
  swap(Color,Enemy),                        % get enemy's color
  ( 
    (Color == OC,                           % starting player makes a move
    makemove(Color, Board, X, Y, TBoard)  
    )   
  ;   
    (Color \== OC,                          % not starting player makes a move
    findbestmove(Color, Board, 1, X, Y),    % it shall be the best one looking one step ahead
    makemove(Color, Board, X, Y, TBoard)    % applying that move to the board
    ) 
  ; 
    (not(legalmove(Color, Board, _, _)),    % no legal move results in doing nothing (Color,n,n)
    TBoard = Board,                         % the board stays unchanged
    X=n, Y=n 
    )
  ),
  makemoves(Enemy, OC, TBoard, Nn, TMoves, NewBoard), % continue with other player
  Moves = [(Color, X, Y) | TMoves].                   % append move

/* TASK 
Determining the best possible move in Reversi is quite hard. However, we can make a
good approximation based on a predicate valueof(+Color,+Board,-Value), that
computes a value of a given board for a certain color. This predicate can be very sophisticated
(see the Wikipedia page http://en.wikipedia.org/wiki/Reversi#Strategy ) but in this
assignment you only have to consider corners and edges (feel free to try out the other key
elements of Reversi strategy). Define the valueof predicate that performs a weighted
count of the number of pieces of a certain color, where corners weigh three times, and
edges two times, more than regular positions. Feel free to try out different weights.
*/

% value assigns each position a value 1,2,3 or 0 if owned by enemy
value(Color, (Color, X, Y), 3) :-  (X==a;X==h),(Y==1;Y==8) ,!.  % corners
value(Color, (Color, X, Y), 2) :- ((X==a;X==h);(Y==1;Y==8)),!.  % edges
value(Color, (Color, _, _), 1).                             % other
value(Color, (Enemy, _, _), 0) :- swap(Color, Enemy).       % enemy stones are valued 0

% calculate the value of a whole board
valueof(_, [], 0). %base case, empty field is 0 for every player
valueof(Color, [B|Board], Value) :- valueof(Color, Board, V), value(Color, B, ThisVal), Value is V+ThisVal.

/* TASK
Define a predicate findbestmove(+Color, +Board, +N, -X, -Y) that computes
the “best” position to put a piece for the given color, given a board and the number moves
it should look ahead. In this predicate you are supposed to use the findall predicate to
generate a list of plays leading to different boards (using makemoves), use the valueof
predicate to rank the resulting boards, and assign X and Y the position of the first move of
the one with the highest value.
In this lies a subtle anomaly, namely that generating all possible plays and optimizing on
that leads to the problem that the moves of the opponent are also optimized with respect
to the current player. Of course, the easiest way to win is to control the opponent. However,
if the opponent is not controlled, basing findbestmove on that assumption is utterly
bad, probably not even close to a good approximation. Thus, the makemoves predicate
has to be modified in order to handle a “realistic” opponent. That is, only generating list of
moves where the opponent is assumed to play good. There are many ways to deal with
this but the simplest solution is probably to alternate the makemoves predicate to, at each
step, consider all moves of the current player but only one for the opponent. This can be
achieved by choosing a greedy approach of the opponent, i.e. only looking ahead one
move using the predicate findbestmove. Again, feel free to try out different approaches.
*/
findbestmove(Color, Board, N, X, Y) :-
  bestmove(Color, Board, N, X, Y, _).  % find best move

% as findbestmove, bestmove finds the best move but also calculates the value of the field of it
bestmove(Color, Board, N, Xs, Ys, Value) :- 
  findall(((Color,X,Y),V), vmoves(Color, Board, N, X, Y, V), VMoves), % gather valued moves
  fmax(VMoves, ((Color, Xs, Ys), Value)).                             % find the maximum

% makemoves with the value for the field -> valued moves
vmoves(Color, Board, N, X, Y, V) :- 
  makemoves(Color, Board, N, [(Color,X,Y)|_], NewBoard),  % make a set of moves
  valueof(Color, NewBoard, V).                            % and value move

% fmax just finds the best (move,value) combination in the array
fmax([(M,V)],(M,V)).          % basecase, the last move in the list is what we start with
fmax([(M,V)|Moves],Best) :- 
  fmax(Moves, (M2,V2)),       % and then we just go for moves with higher values
  (
    (V >= V2, Best = (M,V))
  ;
    (V < V2, Best = (M2,V2))
  ).
