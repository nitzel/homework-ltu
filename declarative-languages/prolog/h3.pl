% delete element from list
del(X, [X | Tail], Tail).
del(X, [Y | Tail], [Y | Tail1]) :- del(X, Tail, Tail1).

% these combinations are "friends" aka go well with each other
fri(f,s). 
fri(f,e). 
fri(g,e).  
fri(s,f).  
fri(e,f). 
fri(e,g). 
fri(X,X). 
% list of friends = list of things without "food collisions"
alive([]) :- !.
alive([_]) :- !.
alive([A,B]) :- fri(A,B),!.
alive([A|List]) :- [B|L2] = List, fri(A,B), alive([A|L2]), alive(L2), !.

% check if state is good, no "food collisions"
stateGood([_,E,west]) :- alive(E).
stateGood([W,_,east]) :- alive(W).

% sail from one riverside to the other (just swapping west & east)
sail(west,east).
sail(east,west).

% Move the boat (changes position in state) and eventually carries an Object obj from one side to the other (changes lists W2,E2)
moveBoat([W,E,P1],[W,E,P2],empty) :- sail(P1,P2).                                % carry nothing
moveBoat([W,E,east],[W2,E2,west],Obj) :- del(Obj, E, E2), append(W, [Obj], W2).  % carry sth from east to west
moveBoat([W,E,west],[W2,E2,east],Obj) :- del(Obj, W, W2), append(E, [Obj], E2).  % carry sth from west to east


% state consists of [W,E,P], animals in the West, animals in the East, boatposition
solvefgb([_,[],west], west, Na, []) :- Na >= 0. % boat at Dest, other Side empty = succeed (since we only move, but never really delete objects)
solvefgb([[],_,east], east, Na, []) :- Na >= 0. % boat at Dest, other Side empty = succeed (since we only move, but never really delete objects)
solvefgb([W,E,P], Dest, Na, Trace) :- Na >= 0                                 % check if we have steps left
                                      ,stateGood([W,E,P])                     % Check if no one gets eaten
                                      ,Na2 is Na-1                            % decrement "steps left"
                                      ,moveBoat([W,E,P],[W2,E2,P2],Obj)       % move the boat(maybe with object)
                                      ,solvefgb([W2,E2,P2],Dest, Na2, Trace1) % solve the remaining 
                                      ,Trace=[[P2,Obj]|Trace1].               % add action to trace

% init state: fgs and boat on the westside, nothing on the eastside
initstate(S) :- S = [[f,g,s],[],west].
% tests
?- initstate(S), solvefgb(S, east, 5, _). %nope
?- initstate(S), solvefgb(S, east, 7, _). %yep!
