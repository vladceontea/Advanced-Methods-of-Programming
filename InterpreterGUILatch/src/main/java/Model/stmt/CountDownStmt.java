package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.type.IType;
import Model.value.IValue;
import Model.value.IntValue;

public class CountDownStmt implements IStmt{

    private String var;
    public CountDownStmt(String v){
        this.var = v;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!state.getSymTable().isDefined(var)){
            return null;
        }

        IValue val = state.getSymTable().lookup(var);
        int index = ((IntValue) val).getValue();

        synchronized (state.getLatchTable()){
            if(state.getLatchTable().get(index) == null){
                return null;
            }
            int count = state.getLatchTable().get(index);
            if(count > 0){
                state.getLatchTable().put(index, count - 1);
                state.getOut().add(new IntValue(state.getId()));
            }
        }
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        return null;
    }

    public String toString() {
        return "countDown(" + this.var + ")";
    }
}
