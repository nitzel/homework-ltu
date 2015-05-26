% N occurrences of E in L
count(_,[],0).
count(E,[E|L],N) :- count(E,L,Nn), N is Nn+1.
count(E,[X|L],N) :- X \= E, count(E,L,N).

% what happens with
% count(A,[a,b,a,a,c,d,a],N).
/*
count(A,[a,b,a,a,c,d,a],N).
A=a
  count(a,[a | b,a,a,c,d,a],_N1).
    count(a,[b | a,a,c,d,a],_N2).
      count(a,[a | a,c,d,a],_N3).
        count(a,[a | c,d,a],_N4).
          count(a,[c | d,a],_N5).
            count(a,[d | a],_N6).
              count(a,[a |],_N7).
                count(?,[],0).
              N7 = 1
            N6 = N7
          N5 = N6
        N4 = 2
      N3 = 3
    N2 = N3
  N1 = N2
N = 4
redo count(a, [a], _N)
a \= a ? 
Fail


or the real:

[trace] 9 ?- count(A,[a,b,a,a,c,d,a],N).
   Call: (6) count(_G551, [a, b, a, a, c, d, a], _G553) ? creep
   Call: (7) count(a, [b, a, a, c, d, a], _G661) ? creep
   Call: (8) b\=a ? creep
   Exit: (8) b\=a ? creep
   Call: (8) count(a, [a, a, c, d, a], _G661) ? creep
   Call: (9) count(a, [a, c, d, a], _G661) ? creep
   Call: (10) count(a, [c, d, a], _G661) ? creep
   Call: (11) c\=a ? creep
   Exit: (11) c\=a ? creep
   Call: (11) count(a, [d, a], _G661) ? creep
   Call: (12) d\=a ? creep
   Exit: (12) d\=a ? creep
   Call: (12) count(a, [a], _G661) ? creep
   Call: (13) count(a, [], _G661) ? creep
   Exit: (13) count(a, [], 0) ? creep
   Call: (13) _G662 is 0+1 ? creep
   Exit: (13) 1 is 0+1 ? creep
   Exit: (12) count(a, [a], 1) ? creep
   Exit: (11) count(a, [d, a], 1) ? creep
   Exit: (10) count(a, [c, d, a], 1) ? creep
   Call: (10) _G665 is 1+1 ? creep
   Exit: (10) 2 is 1+1 ? creep
   Exit: (9) count(a, [a, c, d, a], 2) ? creep
   Call: (9) _G668 is 2+1 ? creep
   Exit: (9) 3 is 2+1 ? creep
   Exit: (8) count(a, [a, a, c, d, a], 3) ? creep
   Exit: (7) count(a, [b, a, a, c, d, a], 3) ? creep
   Call: (7) _G553 is 3+1 ? creep
   Exit: (7) 4 is 3+1 ? creep
   Exit: (6) count(a, [a, b, a, a, c, d, a], 4) ? creep
A = a,
N = 4 ;
   Redo: (12) count(a, [a], _G661) ? creep
   Call: (13) a\=a ? creep
   Fail: (13) a\=a ? creep
   Fail: (12) count(a, [a], _G661) ? creep
   Fail: (11) count(a, [d, a], _G661) ? creep
   Fail: (10) count(a, [c, d, a], _G661) ? creeps
   Redo: (9) count(a, [a, c, d, a], _G661) ? creep
   Call: (10) a\=a ? creep
   Fail: (10) a\=a ? creep
   Fail: (9) count(a, [a, c, d, a], _G661) ? creep
   Redo: (8) count(a, [a, a, c, d, a], _G661) ? creep
   Call: (9) a\=a ? creep
   Fail: (9) a\=a ? creep
   Fail: (8) count(a, [a, a, c, d, a], _G661) ? creep
   Fail: (7) count(a, [b, a, a, c, d, a], _G661) ? creep
   Redo: (6) count(_G551, [a, b, a, a, c, d, a], _G553) ? creep
   Call: (7) a\=_G551 ? creep
   Fail: (7) a\=_G551 ? creep
   Fail: (6) count(_G551, [a, b, a, a, c, d, a], _G553) ? creep
false.
*/