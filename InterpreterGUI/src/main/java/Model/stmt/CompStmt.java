package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.MyDict;
import Model.type.IType;

public class CompStmt implements IStmt{

    IStmt first;
    IStmt second;

    public CompStmt(IStmt first, IStmt second){
        this.first = first;
        this.second = second;
    }

    public String toString(){
        return "(" + this.first.toString() + ";" + this.second.toString() + ")";
    }

    public PrgState execute(PrgState state){
        IStack<IStmt> stack = state.getExeStack();
        stack.push(this.second);
        stack.push(this.first);


        return null;
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        return second.typecheck(first.typecheck(typeEnv));
    }
}
