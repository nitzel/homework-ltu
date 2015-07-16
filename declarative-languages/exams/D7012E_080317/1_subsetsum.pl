
test :- subsetsum([9, 8, 5, 3, 8, 1],29).
% is there a subset in List with the sum K?
% subset: a list of elements that are also in the given list
subsetsum(_, 0). % basecase, we could find the empty list here :)
subsetsum(List, K) :- 
  append(L, [M|R], List), % get a random element in the list
  append(L, R, NewList), % reassamble list without M
  NewK is K-M, % subtract extracted elements value from K
  subsetsum(NewList, NewK),!. % continue with list without M and lower K
