-- UI that asks user for numbers, one at a time, printing the amount of numbers and their product 

blob :: Int -> Int -> IO()
blob acc n = do
  putStrLn ("acc="++(show acc) ++ " n=" ++ (show n))
  putStr "Next factor: "
  s <- getLine
  let f = read s :: Int
  if f == 1 then return() else blob (acc*f) (n+1)
  return ()
