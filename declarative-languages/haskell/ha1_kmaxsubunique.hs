-------------------------------------------------
-- Assignment 1 for Declarative Languages 2015 --
--                kmaxsubunique                --
-------------------------------------------------

-- data to play with
l1 = [4,6,7,98,3,1,2,9,54,3,1,2,4,1,2,3,4,1,2,3,4,1,2,3,4,1,2,3,4] :: [Int]
l2 = [1,4,2,-1,3] :: [Int]

-- first element of triple
el0 :: (Int,Int,Int) -> Int
el0 (a,b,c) = a

-- calculate sum
sum' [] = 0
sum' (x:xs) = x + sum' xs

--remove a from list [a]
remove :: [(Int,Int,Int)] -> (Int,Int,Int) -> [(Int,Int,Int)]
remove [] _ = []
remove (x:xs) y = if x == y then remove xs y else x:(remove xs y)

-- remove element from list
removeFrom :: Int -> [Int] -> [Int]
removeFrom _ [] = []
removeFrom v (x:xs) = if x==v then removeFrom v xs else x: removeFrom v xs

-- remove occurences of first element from rest of list (recursive: removes all duplicates)
initFilter :: [Int] -> [Int]
initFilter [] = []
initFilter (x:xs) = x:initFilter(removeFrom x xs)

-- triples of subsequences (v,i,j) beginning with last element
subs' :: [Int] -> Int -> [(Int, Int, Int)]
subs' [] _ = []
subs' (x:xs) pos = (subs' xs pos) ++ [(sum' (x:xs), pos+1, length (x:xs) + pos)]

-- triples of subsequences (v,i,j) beginning with first element
subs :: [Int] -> Int -> [(Int, Int, Int)]
subs = subs'.reverse

-- all triples, need to start with 1st param=0
allSubs' :: Int -> [Int] -> [(Int, Int, Int)]
allSubs' _ []= []
allSubs' pos (x:xs) = (allSubs' (pos+1) xs) ++ subs (x:xs) pos 

-- all triples
allSubs = allSubs' 0

--- find triple (v,i,j) with maximal v
maxTrip' :: (Int, Int, Int) -> [(Int, Int, Int)] -> (Int, Int, Int)
maxTrip' y [] = y
maxTrip' y (x:xs) = maxTrip' (if el0 x > el0 y then x else y) xs

--- find triple (v,i,j) with maximal v
maxTrip = maxTrip' (0,0,0)

-- from the list of triples (v,i,j) get the k with maximal v
kmaxsubunique' :: [(Int, Int, Int)] -> Int -> [(Int, Int, Int)]
kmaxsubunique' _ 0 = []
kmaxsubunique' [] _ = []
kmaxsubunique' xs k = maxsubset : kmaxsubunique' (remove xs maxsubset) (k-1) 
  where maxsubset = maxTrip xs

-- make the list consist only of unique elements
-- and get the k maximal subsequences as triples (v, i, j)
kmaxsubunique :: [Int] -> Int -> [(Int, Int, Int)]
kmaxsubunique xs = kmaxsubunique' (allSubs xs)

-- autostart kmaxsubunique :)
main = print (kmaxsubunique l2 3)


