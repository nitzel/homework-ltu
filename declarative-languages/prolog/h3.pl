del(X, [X | Tail], Tail).
del(X, [Y | Tail], [Y | Tail1]) :- del(X, Tail, Tail1).

% who eats whom? goes in both directions
eats(f,g).
eats(g,s).
eat(X,Y) :- eats(X,Y) 
          ; eats(Y,X).
            
% list of animals with "food collisions"
deadly([A|List]) :- del(X, List, _), eat(A,X).
deadly([_|List]) :- deadly(List).

% check if state is "bad", that is, "food collisions" left alone on one side
stateBad([_,E,west]) :- deadly(E).
stateBad([W,_,east]) :- deadly(W).

% sail from P1 to P2
sail(west,east).
sail(east,west).

% move an object from one side to another
moveObj([W,E,east],[W2,E2,west],Obj) :- del(Obj, E, E2), append(W, [Obj], W2).
moveObj([W,E,west],[W2,E2,east],Obj) :- del(Obj, W, W2), append(E, [Obj], E2).

% init state, fgs on the West, nothing on the East, boat in the west
initstate(S) :- S = [[f,g,s],[],west].

% state consists of [W,E,P], animals in the West, animals in the East, boatposition
solvefgb([_,[],west], west, Na, _) :- Na > 0. %other side empty
solvefgb([[],_,east], east, Na, _) :- Na > 0. %other side empty
solvefgb([W,E,P], Dest, Na, Trace) :- Na > 0, \+ stateBad([W,E,P]), Na2 is Na-1, moveObj([W,E,P],[W2,E2,P2],Obj), solvefgb([W2,E2,P2], Dest, Na2, append(Trace,[P2,Obj])).



?- initstate(S), solvefgb(S, west, 5, Trace).
