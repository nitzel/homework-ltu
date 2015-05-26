test(Subset, Size/Value) :- knapsack(15, [12/4, 2/2, 2/1, 4/10, 1/1], Subset), sv(Subset, Size/Value).

% knapsack solution :)
knapsack(Size, List, Subset) :-
  findall(
    (Sub, V),
    (append(_,Right,List), append(Sub,_,Right), % get a subset of List
      sv(Sub, S/V), S=<Size), 
    VSubsets),
  fmax(VSubsets, Subset, _).

% find the subset with maximal Value.
fmax([], [], 0). %basecase
fmax([(Sub,V)|List], Subset, Value) :-
  fmax(List, _, Value2),
  V >= Value2,
  Value = V,
  Subset = Sub,!.
fmax([_|List], Subset, Value) :-
  fmax(List, Subset, Value).
  
% sum up a list of sizes/values
sv([], 0/0).
sv([(Es/Ev)|List], Size/Value) :- 
  sv(List, Size2/Value2),
  Size is Size2+Es,
  Value is Value2+Ev.