% PadList: Pads with different Heighs
% ResultList: Pads from PadList with a summed up height of PillarHeight
% It should be as close as possible to PillarHeight
% combinepads([10,20,45,30,80],200,ResultL).
combinepads(PadList, PillarHeight, ResultList) :- 
  findall(
    (List,Diff), 
    cbdiff(PadList, PillarHeight, List, Diff), 
    Results),
  fmin(Results, _, ResultList).
  

% find the list with the minimal Difference
fmin([(L,Diff)], Diff, L). % basecase
fmin([(L,Diff)|Rest], MinDiff, Result) :- 
  fmin(Rest, MinDiff2, Result2), %!, %if you only want one solution
  ((MinDiff2=<Diff, MinDiff = MinDiff2, Result = Result2)
  ;(MinDiff2>=Diff, MinDiff = Diff, Result = L)).

% combine pads randomly and add their difference from the PillarHeight we want
% cbdiff([10,20,45,30,80],200,ResultL,Diff).
cbdiff(PadList, PillarHeight, ResultList, Diff) :-
  cb(PadList, Height, ResultList), 
  (Diff is PillarHeight-Height;Diff is Height-PillarHeight), Diff >= 0.

% combine pads randomly, calculate their height
% cb([10,20,45,30,80],H,R).
cb([X|PadList], Height, [X|ResultList]) :- 
  cb(PadList, Height2, ResultList),
  Height is Height2 + X.
cb([_|PadList], Height, ResultList) :- 
  cb(PadList, Height, ResultList).
cb(_, 0, []).
