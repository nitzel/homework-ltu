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
