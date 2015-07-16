% a) write a predicate that is true only if P fails
note(P) :- P, !, fail.
note(_) :- true.

% b)
notmember(X,L):- note(mamber(X,L)).
mamber(X,[X,_]). 
mamber(X, [_|Rest]):-mamber(X, Rest).
/*
In the following, assume no other bindings than the above two predicates.
Now, will notmember(a, [b, c, d, e]) succeed? Explain why.
  yes, because there is no way member could succeed.
Will notmember(A, [b,c,d,e]) succeed? Explain why.
  no, because member(A,[b,c,d,e]) can succeed by choosing A=b/c/d/e.
  As soon as it succeeds, not will fail.
*/