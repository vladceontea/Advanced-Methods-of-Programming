package Model.stmtFile;

import Exceptions.MyException;
import Exceptions.NotDeclaredException;
import Exceptions.WrongAssignmentException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.stmt.IStmt;
import Model.type.IType;
import Model.type.StringType;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFileStmt implements IStmt {

    Exp exp;

    public OpenRFileStmt(Exp ex){
        this.exp = ex;
    }

    public PrgState execute(PrgState state) throws MyException {

        IStack<IStmt> stk = state.getExeStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<IValue> heapTbl = state.getHeapTable();
        IValue expValue = this.exp.eval(symTbl, heapTbl);
        IType expType = this.exp.eval(symTbl, heapTbl).getType();
        IDict<IValue, BufferedReader> fileTable = state.getFileTable();

        if(expType instanceof StringType){
            if(!fileTable.isDefined(expValue)){
                try {
                    FileReader in = new FileReader(expValue.toString());
                    BufferedReader reader = new BufferedReader(in);
                    fileTable.add(expValue, reader);
                }
                catch(IOException e){
                    throw new MyException(e.getMessage());
                }
            }
            else{
                throw new NotDeclaredException("The expression was declared before.");
            }
        }
        else {
            throw new WrongAssignmentException("The type of the expression is not correct.");
        }

        return null;
    }

    public String toString(){
        return "(OPEN " + this.exp.toString() + ")";
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{

        IType typ = exp.typecheck(typeEnv);
        if(typ.equals(new StringType())) {
            return typeEnv;
        }
        else{
            throw new MyException("Types do not match.");
        }
    }
}
