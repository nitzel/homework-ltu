doubleMe x = x + x

doubleUs x y = doubleMe x + doubleMe y

doubleSN x = if x > 100 then x else 2*x

trs = [(a,b,c)| a<-[1..10],b<-[1..10],c<-[1..10], a^2+b^2==c^2, a+b+c==24]

take' :: Int -> [b] -> [b]
take' 0 _ = []
take' _ [] = []
take' i (x:xs) = x:(take' (i-1) xs)

reverse' :: [a] -> [a]
reverse' [] = []
reverse' (x:xs) = (reverse' xs) ++ [x]

zip' :: [a] -> [b] -> [(a,b)]
zip' [] _ = []
zip' _ [] = []
zip' (x:xs) (y:ys) = (x,y):zip' xs ys

elem' :: (Eq a) => a -> [a] -> Bool
elem' _ [] = False
elem' e (x:xs)
  | x == e    = True
  | otherwise = elem' e xs

qs :: (Ord a) => [a] -> [a]
qs [] = []
qs (x:xs) = qs [e | e <- xs, e<=x] ++ [x] ++ qs [e | e <- xs, e>x]

takeWhile' :: (a->Bool) -> [a] -> [a]
takeWhile' p (x:xs)
  | p x = [x]++takeWhile' p xs
  | otherwise = []
 
--' collatz
chain :: (Integral a) => a -> [a]  
chain 1 = [1]  
chain n  
    | even n =  n:chain (n `div` 2)  
    | odd n  =  n:chain (n*3 + 1)  
    
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

------------
-- EX 1, A4 palindromes
------------
pali :: IO()
pali = 
  do 
    putStr "\ntype: "
    str <- getLine
    putStrLn ""
    if str == "" 
      then return() 
    else do 
      putStr (pal str) 
      pali

pal :: String -> String
pal [] = []
pal [a] = [a] -- to remove doubled letters in the middle
pal (x:xs) = x:(pal xs)++[x]



