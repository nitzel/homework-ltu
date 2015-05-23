use_module(library(lists)). % list-functionality like nth0

%http://www.cs.uni-potsdam.de/wv/lehre/Material/Prolog/Eclipse-Doc/bips/lib/lists/nonmember-2.html
non_member(Arg,[Arg|_]) :- !,fail.
non_member(Arg,[_|Tail]) :- !,non_member(Arg,Tail).
non_member(_,[]).

% change colors from white to black or the other way around
swap(white,black).
swap(black,white).

% is the move on playground (values in range: black/white, a-h, 1-8)
position(P,X,Y) :- member(P, [white,black]) , member(X, [a,b,c,d,e,f,g,h]), member(Y, [1,2,3,4,5,6,7,8]).
position([P,X,Y]) :- position(P,X,Y).

% check if the position is empty
posempty(Color, Board, X, Y) :- position(Color, X, Y) , non_member((_, X, Y), Board). % position empty

% neighbors: get the coordinates of a neighbor and also the direction Dx/Dy
nb(X,Y, Nx, Ny, Dx, Dy) :- member(Dx,[-1,0,1]),               % try different X-offsets (left,middle,right) 
                           nth0(Px,  [a,b,c,d,e,f,g,h], X),   % position of X coord in the list
                           nth0(Pxe, [a,b,c,d,e,f,g,h], Nx),  % neighbors X coordinate
                           member(Dy,[-1,0,1]),               % try different Y-offsets (upper,middle,lower)
                           Pxe is Px+Dx,                      % increase/decrease/keep the X-Coordinate
                           Ny is Y+Dy,                        % increase/decrease/keep the Y-Coordinate to find the neighbors
                           (Nx \= X; Ny \= Y).                % avoid (Nx,Ny)=(X,Y) aka Dx=Dy=0

% go in a direction hopping over enemies, until you encounter a friend
friendinsight(X, Y, Dx, Dy, FriendlyColor, Board, []) :-        nb(X,Y, Nx, Ny, Dx, Dy),                % get neighbor coordinates in same dir
                                                                member((FriendlyColor, Nx, Ny), Board). % found friend in sight 
friendinsight(X, Y, Dx, Dy, FriendlyColor, Board, Changes) :-   swap(FriendlyColor, EnemyColor),
                                                                nb(X,Y, Nx, Ny, Dx, Dy),                          % get neighbor coordinates in same dir
                                                                member((EnemyColor, Nx, Ny), Board),              % found enemy in row  
                                                                friendinsight(Nx,Ny,Dx,Dy,FriendlyColor, Board, NChanges),  % hop on it, ontinue searching
                                                                Changes = [(EnemyColor,Nx,Ny) | NChanges].  % add enemy stone to Changes list. (the one to flip)
        
% Define a predicate legalmove(Color, Board, X, Y), that given a color, a board, and
% a postion, succeeds if they constitute a legal move according to the rules of Reversi. Observe
% that this predicate is useful both for finding coordinates and for determining if a particular
% coordinate constitutes a legal move. I.e. X and Y free (output) or bound (input)
legalmove(Color, Board, X, Y) :-  posempty(Color, Board, X, Y), friendinsight(X,Y, _, _, Color, Board, Changes), Changes \= []. % if no changes, there was no stone to flip. not what we want.

%?- legalmove(black, [(white,d,4),(black,e,4),(black,d,5),(white,e,5)], X,Y).
%?- legalmove(black, [(white,d,4),(black,e,4),(black,d,5),(white,e,5)], X,Y).
%?- friendinsight(b, 4, 1, 0, black, [(white,c,4),(white,d,4),(black,e,4),(black,d,5),(white,e,5)], Changes).

%Define a predicate makemove(+Color, +Board, +X, +Y, -NewBoard), that computes
%the new board given a color, a board, and the x and y coordinates of the move. The
%goal should fail if the move is illegal.
updateboard(Board, [], Board).
updateboard(Board, [Change|Changes], Newboard) :- delete(Board, Change, CBoard),!, % remove flipped stone "Change" from Board.
                                                  (C,X,Y)=Change, swap(C,E), CNewboard = [(E,X,Y)|CBoard], % insert removed stone with changed color to CNewboard
                                                  updateboard(CNewboard, Changes, Newboard).  % continue with removing "Changes"-Stones from Board

makemove(Color, Board, X, Y, NewBoard) :- legalmove(Color, Board, X, Y), % check if move is legal
                                          findall(Changes, friendinsight(X,Y, _, _, Color, Board, Changes), AllChanges),  % find all stones that are to be changed
                                          flatten(AllChanges,FlatChanges), % flatten the list of lists
                                          updateboard(Board, FlatChanges, NewBoardPre), % apply changes to board
                                          list_to_set([(Color, X, Y) | NewBoardPre], NewBoard). % add the last move, remove duplicates
% makemove(black, [(white,e,4),(white,d,5),(white,e,5),(black,f,4),(black,d,6),(black,e,6)], d,4, Nuboard).
% friendinsight(d,4, DX, DY, black, [(white,e,4),(white,d,5),(white,e,5),(black,f,4),(black,d,6)], Changes).
% updateboard([(white, d, 5)], [(white, d, 5)],N).

% its all about turning the pieces around, so do the same as in friendsinsight (jump from one to the next), store the swapped in a list and return the list in the end
% if you do not find a friendlycolor in the end, just fail or return an empty list
% then GATHER these lists from all friendsinsights and replace the corresponding pieces in the Board-State-List ... findall or so


% todo
%Also, define a predicate makemoves(+Color, +Board, +N, -Moves, -NewBoard)
%that computes a new board and a list of moves leading to that board given a starting color,
%a board and the number of moves to be made. Observe that makemove might fail due to
%the fact that the player might not be able to make a move. According to the rules of Reversi,
%the turn should go over to the other player. In makemoves, this inability to make a
%move should be handled in such way that the element (Color,n,n) should be added to
%the list of moves and the no-move should count as any other move. However, the no-move
%shall NOT be a possible move if there exists a legal move. As you might already have understood,
%the makemoves predicate will never fail.
%
% makemoves makes just some random moves ...
makemoves(_,     Board, 0, [],    Board).%basecase
makemoves(Color, Board, N, Moves, NewBoard) :-  N>0,
                                                makemove(Color, Board, X, Y, TBoard),
                                                swap(Color,Enemy), Nn is N-1, 
                                                Moves = [(Color, X, Y) | TMoves],                       
                                                makemoves(Enemy, TBoard, Nn, TMoves, NewBoard).
% makemoves(black, [(white,d,4),(black,e,4),(black,d,5),(white,e,5)], 2, Moves, Nuboard).
% makemoves(black, [(white,e,4),(white,d,5),(white,e,5),(black,f,4),(black,d,6),(black,e,6)], 5, Moves, Nuboard).
% makemove(black, [(white,d,4),(black,e,4),(black,d,5),(white,e,5)], c,4, Nuboard).              
% makemove(white, [ (black, c, 4), (black, e, 4), (black, d, 5), (white, e, 5), (black, d, 4)], c,3, Nuboard).              
% friendinsight(c, 3, DX, DY, white, [ (black, c, 4), (black, e, 4), (black, d, 5), (white, e, 5), (black, d, 4)], Changes).                                   
%Determining the best possible move in Reversi is quite hard. However, we can make a
%good approximation based on a predicate valueof(+Color,+Board,-Value), that
%computes a value of a given board for a certain color. This predicate can be very sophisticated
%(see the Wikipedia page http://en.wikipedia.org/wiki/Reversi#Strategy ) but in this
%assignment you only have to consider corners and edges (feel free to try out the other key
%elements of Reversi strategy). Define the valueof predicate that performs a weighted
%count of the number of pieces of a certain color, where corners weigh three times, and
%edges two times, more than regular positions. Feel free to try out different weights.

% value assigns each position a value 1,2,3 or 0 if owned by enemy
value(Color, (Color, X, Y), 3) :-  (X==a;X==h),(Y==1;Y==8) ,!.  % corners
value(Color, (Color, X, Y), 2) :- ((X==a;X==h);(Y==1;Y==8)),!.  % edges
value(Color, (Color, _, _), 1).                             % other
value(Color, (Enemy, _, _), 0) :- swap(Color, Enemy).       % enemy stones get you nothing

%valueof(Color,[B|Board],Value) :- swap(Color,Enemy), (Enemy, X,Y) = B, valueof(Color, Board, Value). 
valueof(Color, [B|Board], Value) :- valueof(Color, Board, V), value(Color, B, ThisVal), Value is V+ThisVal.
valueof(_, [], 0). %base case, empty field is 0 for every player

% valueof(black, [(white,e,4),(white,d,5),(white,e,5),(black,f,4),(black,d,6),(black,e,6)], V).

% todo
%Define a predicate findbestmove(+Color, +Board, +N, -X, -Y) that computes
%the “best” position to put a piece for the given color, given a board and the number moves
%it should look ahead. In this predicate you are supposed to use the findall predicate to
%generate a list of plays leading to different boards (using makemoves), use the valueof
%predicate to rank the resulting boards, and assign X and Y the position of the first move of
%the one with the highest value.
%In this lies a subtle anomaly, namely that generating all possible plays and optimizing on
%that leads to the problem that the moves of the opponent are also optimized with respect
%to the current player. Of course, the easiest way to win is to control the opponent. However,
%if the opponent is not controlled, basing findbestmove on that assumption is utterly
%bad, probably not even close to a good approximation. Thus, the makemoves predicate
%has to be modified in order to handle a “realistic” opponent. That is, only generating list of
%moves where the opponent is assumed to play good. There are many ways to deal with
%this but the simplest solution is probably to alternate the makemoves predicate to, at each
%step, consider all moves of the current player but only one for the opponent. This can be
%achieved by choosing a greedy approach of the opponent, i.e. only looking ahead one
%move using the predicate findbestmove. Again, feel free to try out different approaches.
%findbestmove(Color, Board, N, X, Y) :- makemoves(Color, Board, N, [(Color,X,Y)|Moves], NewBoard), findbestmove(Color, NewBoard, Nn, _, _).
vmovs(Color, Board, N, X, Y, V) :- makemoves(Color, Board, N, [(Color,X,Y)|_], NewBoard), valueof(Color, NewBoard, V).
bestmov(Color, Board, N, Xs, Ys, Value) :- findall(((Color,X,Y),V), vmovs(Color, Board, N, X, Y, V), VMoves), fmax(VMoves, ((Color, Xs, Ys), Value)).
 
fmax( [],(MM,MV)) :- MM = (white,a,0), MV = (-1). 
fmax([(M,V)|Moves],Best) :- fmax(Moves, (M2,V2)), ((V >= V2, Best = (M,V));(V < V2, Best = (M2,V2))). %sths wrong ... :S 
% todo also there are too many equal solutions suggested!

%vmovs(black, [(white,d,4),(black,e,4),(black,d,5),(white,e,5)], 2, X,Y, Value).
%bestmov(black, [(white,d,4),(black,e,4),(black,d,5),(white,e,5)], 2, X,Y, Value).


%fbm(Friendly, Color, Board, N, X, Y) :- 
%valuemove(Color, Board, X, Y, Value) :- makemove(Color, Board, X, Y, NewBoard), valueof(Color, NewBoard, Value).




