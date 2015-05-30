--------
-- D7012E_100315
-- Task 1

-- a) 
data BinTree t = Nil | Node t (BinTree t) (BinTree t) deriving Show
-- b) count nodes
countnodes2 :: BinTree a -> Int
countnodes2 (Node _ t1 t2) = (countnodes2 t1)+(countnodes2 t2)+1
countnodes2 (Nil) = 0
-- c) check if it is a bin-search tree,
-- where elements in the left subtree are smaller than in the right
issearchtree :: Ord a => BinTree a -> Bool
issearchtree (Nil) = True
issearchtree (Node v Nil Nil) = True
issearchtree (Node v t1 t2) = (v >= fmax t1) && (v <= fmin t2) && issearchtree t1 && issearchtree t2

fmax :: Ord a => BinTree a -> a
fmax(Node v Nil Nil) = v
fmax (Node v t1 t2) = max v $ max (fmax t1) (fmax t2)

fmin :: Ord a => BinTree a -> a
fmin(Node v Nil Nil) = v
fmin (Node v t1 t2) = min v $ min (fmin t1) (fmin t2)
--issearchtree (Node 2 (Node 1 (Nil) (Nil)) (Node 2 (Nil) (Nil)))


--d) insert
insert :: Ord a => BinTree a -> a -> BinTree a
insert (Nil) i = (Node i Nil Nil)
insert (Node v l r) i
  | i >= v = (Node v l (insert r i))
  | otherwise = (Node v (insert l i) r)
-- foldl (insert) Nil [4,3,5,2,9]

--------
-- D7012E_100315
-- Task 5 highorder

-- a) take function and bintree from 1
collapse :: BinTree a -> (a -> b -> b -> b) -> b -> b
collapse Nil _ start = start
collapse (Node v l r) f s = f v (collapse l f s) (collapse r f s)
-- example: sum up content of tree
-- collapse (foldl (insert) Nil [1..100]) (\x y z -> x+y+z) 0


-- b) pre order traversal with collapse!!
treetolist :: BinTree a -> [a]
treetolist t = collapse t (\m l r -> m:(l++r)) []
-- or equivalent
--treetolist (Nil) = []
--treetolist (Node v l r) = v:(treetolist l)++(treetolist r)
-- example: treetolist (foldl (insert) Nil [4,3,2,6,4,23,3])







