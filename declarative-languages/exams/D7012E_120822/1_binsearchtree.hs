------------
-- EX 1, A1 trees
------------
--a)
data ETree = Add ETree  ETree | Minus ETree | Number Int deriving Show
--b) (Minus (Add (Number 4) (Minus (Number 6))))
--c)
eval :: ETree -> Int
eval (Number n) = n
eval (Minus tree) = - eval tree
eval (Add t1 t2) = eval t1 + eval t2
--d)
data CTree t = Data t | UnOp (t->t) (CTree t) | BiOp (t->t->t) (CTree t) (CTree t)
--e) convert from ETree to CTree
conv :: ETree -> (CTree Int)
conv (Number n) = Data n
conv (Minus tree) = UnOp (0-) (conv tree)
conv (Add t1 t2) = (BiOp (+) (conv t1) (conv t2))
--f)
ceval :: CTree t -> t
ceval (Data n) = n
ceval (UnOp op tree) = op $ ceval tree
ceval (BiOp op t1 t2) = (op (ceval t1) (ceval t2))




