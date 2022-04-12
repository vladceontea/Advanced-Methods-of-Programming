package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.type.IType;
import Model.value.IValue;
import Model.value.IntValue;

public class AwaitStmt implements IStmt{

    private String var;

    public AwaitStmt(String v){
        this.var = v;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!state.getSymTable().isDefined(var)){
            return null;
        }

        IValue val = state.getSymTable().lookup(var);
        int index = ((IntValue) val).getValue();

        if(!state.getLatchTable().contains(index)){
            return null;
        }
        else{
            if(state.getLatchTable().get(index) == 0){
                return null;
            }
            else {
                state.getExeStack().push(this);
            }
        }

        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        return null;
    }

    public String toString(){
        return "await(" + var + ")";
    }

}
