/*
In the subset-range problem, we are given a sequence of integers and two parameters m
and n. The problem is to decide if there exists a subset of integers from the sequence such
that their sum is between (inclusive) m and n (greater than or equal to m but smaller than
or equal to n).
Declare a predicate
subsetrange(List, M, N)
which succeeds if an only if there exists a solution.
*/
test :- subsetrange([4,7,9,3],10,12). %true
test2 :- \+ subsetrange([4,7,9,3],25,30). %true

% basecase. If this is true, than we can use the empty-list as a subset which has the
% sum 0 and 0 is in [M,N]
subsetrange(_, M, N) :- M=<0, N>=0.
subsetrange(List, M, N) :-
  append(L, [E|R], List), % get random element
  append(L, R, NewList), % assemble list without E
  NewM is M-E,  % decrease borders
  NewN is N-E,
  subsetrange(NewList, NewM, NewN). %continue with new borders and without E