package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.adt.MyStack;
import Model.type.BoolType;
import Model.type.IType;
import Model.value.IValue;

public class ForkStmt implements IStmt{

    IStmt stmt;

    public ForkStmt(IStmt statement){
        this.stmt = statement;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTbl = state.getSymTable();
        IStack<IStmt> stack = new MyStack<IStmt>();
        IDict<String, IValue> newSymTbl = symTbl.deepCopy();

        PrgState forkedState = new PrgState(stack, newSymTbl, state.getOut(), state.getFileTable(), state.getHeapTable(), state.getBarrierTable(),stmt);

        return forkedState;
    }

    public String toString(){
        return "FORK(" + this.stmt.toString() + ")";
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
