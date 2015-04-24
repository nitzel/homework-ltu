module Program(T, parse, fromString, toString, exec) where
import Parser hiding (T)
import qualified Statement
import qualified Dictionary
import Prelude hiding (return, fail)
newtype T = Program [Statement.T] deriving (Show) --Program () -- to be defined
instance Parse T where
  parse = iter Statement.parse >-> Program -- error "Program.parse not implemented"
  toString (Program statements) = concat $ map Statement.toString statements -- error "Program.toString not implemented"
  
-- execute program with empty dictionary  
exec (Program prog)= Statement.exec prog Dictionary.empty --error "Program.exec not implemented"
