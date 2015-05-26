%a: In prolog, declare a predicate not(P), which succeeds if and only if P fails.
nat(P) :- P,!,fail.
nat(_) :- true.

notmember(X,L):-nat(mamber(X,L)).
mamber(X,[X,_]).
mamber(X, [_|Rest]):-
mamber(X, Rest).
/*
In the following, assume no other bindings than the above two predicates.
Now, will notmember(a, [b, c, d, e]) succeed? Explain why.
Will notmember(A, [b,c,d,e]) succeed? Explain why.
*/
/*
  the first one will succeed, since member(...) fails and the false gets inverted
  the second one will not, since A can be instantiated to any element of the array, so there is a way for member(...) to succeed and thus the true will be inverted to false.
*/