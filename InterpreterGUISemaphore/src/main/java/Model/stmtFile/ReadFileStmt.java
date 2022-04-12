package Model.stmtFile;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.stmt.IStmt;
import Model.type.IType;
import Model.type.IntType;
import Model.type.RefType;
import Model.value.IValue;
import Model.value.IntValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt {

    Exp exp;
    String varName;

    public ReadFileStmt(Exp ex, String varnm){
        this.exp = ex;
        this.varName = varnm;
    }

    public PrgState execute(PrgState state) throws MyException{

        IStack<IStmt> stk = state.getExeStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        IDict<IValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<IValue> heapTbl = state.getHeapTable();
        if(symTbl.isDefined(varName) && symTbl.lookup(varName).getType() instanceof IntType){
            if(this.exp.eval(symTbl, heapTbl) instanceof StringValue){
                IValue expValue = this.exp.eval(symTbl, heapTbl);
                if(fileTable.isDefined(expValue)){
                    BufferedReader reader = fileTable.lookup(expValue);
                    String line;
                    try {
                        line = reader.readLine();
                        if (line == null){
                            IntValue tableValue = new IntValue(0);
                            symTbl.update(varName, tableValue);
                        }
                        else{
                            int value = Integer.parseInt(line);
                            IntValue tableValue = new IntValue(value);
                            symTbl.update(varName, tableValue);
                        }
                    }
                    catch(IOException e){
                        throw new MyException(e.getMessage());
                    }
                }
                else{
                    throw new MyException("The expression has no value attached.");
                }
            }
            else{
                throw new MyException("The expression is not a string value.");
            }
        }
        else{
            throw new MyException("The variable is not defined correctly.");
        }

        return null;
    }

    public String toString(){
        return "(READ " + this.exp.toString() + " FROM " + this.varName + ")";
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typevar = typeEnv.lookup(varName);
        IType typexp = exp.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp))){
            return typeEnv;
        }
        else{
            throw new MyException("READ stmt: right hand side and left hand side have different types.");
        }
    }
}
