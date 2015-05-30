%--------
%-- D7012E_100315
%-- Task 6


%a: In prolog, declare a predicate findfirst(+E,+ L, -F) that binds the first occurrence of E in the list L to F. The predicate should only bind the first occurrence to F and return no more answers regardless of the number of occurences of E in F. You may not use any “helper-functions” such as member and similar.
findfirst(E,[E|_],E) :- !.
findfirst(E,[_|L],F) :- findfirst(E,L,F).

%b: What is the difference between a red and a green cut? Explain.
/*
a green cut cut's away backtracing-routes that would not have been used 
  in the solution either way and thus the results are never changed 
  and the program can be viewed as if there would be no cuts (except that its faster)
a red cut sometimes cuts also routes away that otherwise would have been 
  considered as a result and thus when viewing code with red-cuts,
  you have to take note of the effects of these cuts!
*/

%c: Using findfirst from assignment a, declare a predicate multiples(+L1, -L2) that binds L2 to a list containing all elements from L1 that appears more than once in L1.
%%%%%
% NO IDEA!