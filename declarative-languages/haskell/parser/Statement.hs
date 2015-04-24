module Statement(T, parse, toString, fromString, exec) where
import Prelude hiding (return, fail)
import Parser hiding (T)
import qualified Dictionary
import qualified Expr
type T = Statement
data Statement =
      Assignment String Expr.T
    | If Expr.T Statement Statement
    | Skip
    | Read String
    | Write Expr.T
    | Beginend [Statement] --Begin ... End
    | While Expr.T Statement
    deriving Show

-- statements
stmt = 
    assignStmt
  ! ifStmt
  ! skipStmt
  ! readStmt
  ! writeStmt
  ! beginendStmt
  ! whileStmt
-- define the stmt's and the corresponding build-functions
assignStmt = word #- accept ":=" # Expr.parse #- require ";" >-> buildAss
buildAss (v, e) = Assignment v e

ifStmt = accept "if" -# Expr.parse #- require "then" # stmt #- require "else" # stmt >-> buildIf
buildIf ((condition, statement1), statement2) = If condition statement1 statement2

skipStmt = accept "skip" #- require ";" >-> buildSkip
buildSkip _ = Skip

readStmt = accept "read" -# word #- require ";" >-> buildRead
buildRead (varname) = Read varname

writeStmt = accept "write" -# Expr.parse #- require ";" >-> buildWrite
buildWrite (expression) = Write expression

beginendStmt = accept "begin" -# iter stmt #- require "end" >-> buildBegin
buildBegin (statements) = Beginend statements

whileStmt = accept "while" -# Expr.parse #- require "do" # stmt >-> buildWhile
buildWhile (condition, statement) = While condition statement



-- exec fun
exec :: [T] -> Dictionary.T String Integer -> [Integer] -> [Integer]
exec (If cond thenStmts elseStmts: stmts) dict input = 
    if (Expr.value cond dict)>0 
    then exec (thenStmts: stmts) dict input
    else exec (elseStmts: stmts) dict input

exec (Assignment varname expression: stmts) dict input =
    exec stmts changedDict input where changedDict = Dictionary.insert(varname, (Expr.value expression dict)) dict

exec (Skip: stmts) dict input =
    exec stmts dict input

exec (Read varname: stmts) dict (input:inputQueue) =
    exec stmts changedDict inputQueue where changedDict = Dictionary.insert(varname, input) dict

exec (Write expression: stmts) dict input = 
    [(Expr.value expression dict)] ++ (exec stmts dict input)
    
exec (Beginend statements:stmts) dict input = 
    exec (statements ++ stmts) dict input -- add statements in begin/end-block to the remaining list
    
exec (While condition statement: stmts) dict input =
    if (Expr.value condition dict)>0
    then exec newStmts dict input
    else exec stmts dict input
    where newStmts = (statement:(While condition statement):stmts)
exec [] dict input = [] -- recursion-end  

-- printing and formatting
format :: Int -> String -- n*2 spaces on the left, indentation
format 0 = ""
format l = "  "++format (l-1)

printStmt :: Int -> Statement -> String
printStmt level (Assignment var expr)   = format level ++ var ++ " := " ++ Expr.toString expr ++ ";\n"
printStmt level (If cond stmt1 stmt2)   = format level ++ "if " ++ Expr.toString cond ++ "\n" ++ 
                                          format level ++ "then\n" ++ 
                                            printStmt (level+1) stmt1 ++ 
                                          format level ++ "else\n" ++ 
                                            printStmt (level+1) stmt2
printStmt level (Skip)                  = format level ++ "skip;\n"
printStmt level (Read s)                = format level ++ "read " ++ s ++ ";\n"
printStmt level (Write s)               = format level ++ "write " ++ Expr.toString s ++ ";\n"
printStmt level (Beginend stmts)        = format level ++ "begin\n" ++ 
                                            concat (map(printStmt (level+1)) stmts) ++ 
                                          format level ++ "end\n"
printStmt level (While cond stmts)      = format level ++ "while " ++ Expr.toString cond ++ "\n" ++
                                          format level ++ "do\n" ++  printStmt (level+1) stmts

instance Parse Statement where
  parse = stmt --error "Statement.parse not implemented"
  toString = printStmt 0 --error "Statement.toString not implemented"
