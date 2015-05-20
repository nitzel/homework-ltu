use_module(library(lists)). % list-functionality like nth0

%http://www.cs.uni-potsdam.de/wv/lehre/Material/Prolog/Eclipse-Doc/bips/lib/lists/nonmember-2.html
non_member(Arg,[Arg|_]) :- !,fail.
non_member(Arg,[_|Tail]) :- !,non_member(Arg,Tail).
non_member(_,[]).

swap(white,black).
swap(black,white).

% move on playground (black/white, a-h, 1-8)
position(P,X,Y) :- member(P, [white,black]) , member(X, [a,b,c,d,e,f,g,h]), member(Y, [1,2,3,4,5,6,7,8]).
position([P,X,Y]) :- position(P,X,Y).

% check if the position is empty
posempty(Color, Board, X, Y) :- position(Color, X, Y) , non_member((_, X, Y), Board). % position empty

% neighbors 
nb(X,Y, Nx, Ny, Dx, Dy) :- member(Dx,[-1,0,1]),               % try different offsets (left,middle,right) 
                           nth0(Px, [a,b,c,d,e,f,g,h], X),    % position of X coord in the list
                           Pxe is Px+Dx,                      % increase/decrease/keep as it is
                           nth0(Pxe, [a,b,c,d,e,f,g,h], Nx),  % neighbors X coordinate
                           member(Dy,[-1,0,1]),               % try different offsets (upper,middle,lower)
                           Ny is Y+Dy,                        % Neighbors Y coordinate
                           (Nx =\= X; Ny =\= Y).                      % avoid (Nx,Ny)=(X,Y) 

% 
neighbor(Color, Board, X, Y) :- swap(Color, Enemy), nb(X,Y, Nx, Ny, Dx, Dy).

% Define a predicate legalmove(Color, Board, X, Y), that given a color, a board, and
% a postion, succeeds if they constitute a legal move according to the rules of Reversi. Observe
% that this predicate is useful both for finding coordinates and for determining if a particular
% coordinate constitutes a legal move. I.e. X and Y free (output) or bound (input)
legalmove(Color, Board, X, Y) :- posempty(Color, Board, X, Y), neighbor(Color, Board, X, Y).



%?- legalmove(black, [(white,d,4),(black,e,4),(black,d,5),(white,e,5)], X,Y).

%Define a predicate makemove(+Color, +Board, +X, +Y, -NewBoard), that computes
%the new board given a color, a board, and the x and y coordinates of the move. The
%goal should fail if the move is illegal.
%Also, define a predicate makemoves(+Color, +Board, +N, -Moves, -NewBoard)
%that computes a new board and a list of moves leading to that board given a starting color,
%a board and the number of moves to be made. Observe that makemove might fail due to
%the fact that the player might not be able to make a move. According to the rules of Reversi,
%the turn should go over to the other player. In makemoves, this inability to make a
%move should be handled in such way that the element (Color,n,n) should be added to
%the list of moves and the no-move should count as any other move. However, the no-move
%shall NOT be a possible move if there exists a legal move. As you might already have understood,
%the makemoves predicate will never fail.
makemove(+Color, +Board, +X, +Y, -NewBoard).

%Determining the best possible move in Reversi is quite hard. However, we can make a
%good approximation based on a predicate valueof(+Color,+Board,-Value), that
%computes a value of a given board for a certain color. This predicate can be very sophisticated
%(see the Wikipedia page http://en.wikipedia.org/wiki/Reversi#Strategy ) but in this
%assignment you only have to consider corners and edges (feel free to try out the other key
%elements of Reversi strategy). Define the valueof predicate that performs a weighted
%count of the number of pieces of a certain color, where corners weigh three times, and
%edges two times, more than regular positions. Feel free to try out different weights.
 valueof(+Color,+Board,-Value).

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
findbestmove(+Color, +Board, +N, -X, -Y).