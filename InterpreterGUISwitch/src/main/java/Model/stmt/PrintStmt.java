package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IList;
import Model.exp.Exp;
import Model.type.IType;
import Model.value.IValue;

public class PrintStmt implements IStmt{

    Exp exp;
    public PrintStmt(Exp ex) {
        this.exp = ex;

    }

    public PrgState execute(PrgState state) throws MyException {
        IList<IValue> output = state.getOut();
        output.add(exp.eval(state.getSymTable(), state.getHeapTable()));
        state.setOut(output);
        return null;
    }

    public String toString(){
        return "print(" + exp.toString()+")";
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}
