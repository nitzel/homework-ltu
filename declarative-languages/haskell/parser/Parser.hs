module Parser(module CoreParser, T, digit, digitVal, chars, letter, err,
              lit, number, iter, accept, require, token,
              spaces, word, (-#), (#-)) where
import Prelude hiding (return, fail)
import Data.Char
import CoreParser
infixl 7 -#, #- 

type T a = Parser a

-- swap parameters for function
sw :: (a -> b -> c) -> b -> a -> c
sw f y x = f x y
-- isNothing for maybe
isNothing :: Maybe a -> Bool
isNothing Nothing = True
isNothing _ = False


err :: String -> Parser a
err message cs = error (message++" near "++cs++"\n")

iter :: Parser a -> Parser [a]  
iter m = m # iter m >-> cons ! return [] 

cons(a, b) = a:b

-- todo
(-#) :: Parser a -> Parser b -> Parser b
(m -# n) cs = ((m # n) >-> \(_,x)-> x) cs
--(m -# n) cs = 
--    case (m#spaces) cs of
--    Nothing -> Nothing
--    Just((a,b), cs') -> n cs'
--m -# n = error "-# not implemented"

(#-) :: Parser a -> Parser b -> Parser a
(m #- n) cs = ((m # n) >-> \(x,_)-> x) cs
--  case (m#spaces#n) cs of
--  Nothing -> Nothing
--  Just(((a,b),c), cs') -> Just(a, cs')
--m #- n = error "#- not implemented"

spaces :: Parser String
spaces = iter (char ? sw elem [' ','\t'])
--spaces =  error "spaces not implemented"

token :: Parser a -> Parser a
token m = m #- spaces

letter :: Parser Char
letter = char ? (sw elem (['A'..'Z']++['a'..'z']))
--letter =  error "letter not implemented"

word :: Parser String
word = token (letter # iter letter >-> cons)

chars :: Int -> Parser String
chars 0 = return []
chars n = char #chars (n-1) >-> cons

accept :: String -> Parser String
accept w = (token (chars (length w))) ? (==w)

-- todo use parsers instead
require :: String -> Parser String
require s = (accept s ! err ("expected " ++ s))
--if isNothing (accept s t) then error ("expecting "++s++" near "++t) else accept s t  
--require s t = error "require not implemented"

lit :: Char -> Parser Char
lit c = token char ? (==c)

digit :: Parser Char 
digit = char ? isDigit 

digitVal :: Parser Integer
digitVal = digit >-> digitToInt >-> fromIntegral

number' :: Integer -> Parser Integer
number' n = digitVal #> (\ d -> number' (10*n+d))
          ! return n
number :: Parser Integer
number = token (digitVal #> number')

