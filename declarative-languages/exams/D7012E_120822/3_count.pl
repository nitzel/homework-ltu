% N occurrences of E in L
count(_,[],0).
count(E,[E|L],N) :- count(E,L,Nn), N is Nn+1.
count(E,[X|L],N) :- X \= E, count(E,L,N).
/*
on screen output after count(A, [a,b,a,a,c,d,a], N)
A = a
N = 4
false
*/