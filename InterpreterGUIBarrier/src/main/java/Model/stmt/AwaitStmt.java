package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.type.IType;
import Model.type.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.Objects;

public class AwaitStmt implements IStmt{

    private String var;

    public AwaitStmt(String v){
        this.var=v;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        boolean isFound = state.getSymTable().isDefined(var);
        if(!isFound){
            return null;
        }
        IntValue val = (IntValue) state.getSymTable().lookup(var);
        int index = val.getValue();

        if(!state.getBarrierTable().contains(index)){
            return null;
        }
        synchronized (state.getBarrierTable()){
            if(state.getBarrierTable().get(index).getFirst() == state.getBarrierTable().get(index).getSecond().size()){
                return null;
            }
            else{
                state.getExeStack().push(new AwaitStmt(var));
                state.getBarrierTable().get(index).getSecond().add(state.getId());
                return null;
            }
        }
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.lookup(var);
        if(typevar.equals(new IntType())){
            return typeEnv;
        }
        else{
            throw new MyException("newLock is not an int.");
        }
    }

    public String toString()
    {
        return "Await("+var+")";
    }
}
