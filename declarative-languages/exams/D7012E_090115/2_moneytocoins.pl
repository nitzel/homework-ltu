/*
In this assignment, we are to study the problem of exchanging money. The problem is that,
if we have money of one large value, we want to exchange it for several smaller values.
Depending on what values are available, the problem can be non-trivial.
Declare, in prolog, a predicate
exchange(ValueList, Value, ResultList)
where ValueList is a list of integer values and Value is an integer value. The
predicate should compute one possible way to compose the value Value from the values
in ValueList by summing values from ValueList. The result should be returned in
ResultList. That is, the sum of all values in ResultList should be equal to
Value. Each value in ResultList should be chosen from ValueList. It is OK to
choose the same value several times. The predicate should be declared in such a way that
all possible ways of obtaining the value Value could be generated through backtracking
over the predicate. If it is not possible to choose values such that their sum becomes
Value, the predicate should fail.
*/
test(V,R) :- exchange([500,100,50,20,10], V , R).
test2(V,R) :- exchange([8,6,4,2], V , R).

% works both, but both is way too slow if it does not find the result ... however ;)
exchange([], Value, _) :- Value \= 0, fail.
exchange(_, 0, []).
exchange([C|ValueList], Value, ResultList) :- 
  N is Value div C,
  numlist(0,N, NList), member(Nn, NList),
  V is Value - Nn*C,
  exchange(ValueList, V, ResList),
  ResultList = [N*C|ResList].

/*
exchange(_, 0, []).
exchange(ValueList, Value, ResultList) :- 
  member(Coin, ValueList),  % choose one coin
  Coin =< Value,
  NValue is Value-Coin,
  ((Coin > NValue, delete(ValueList,Coin,NValueList));(Coin =< NValue,NValueList=ValueList)),
  exchange(NValueList, NValue, List),
  ResultList = [Coin|List]. 
*/

