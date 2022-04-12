package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.ISemaphore;
import Model.adt.Pair;
import Model.type.IType;
import Model.type.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.List;
import java.util.Objects;

public class ReleaseStmt implements IStmt{

    private String var;

    public ReleaseStmt(String v){
        this.var = v;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        ISemaphore<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();

        if(!symTable.isDefined(var)){
            throw new MyException("Not a value in symTable.");
        }

        IValue val = symTable.lookup(var);
        if(!Objects.equals(val.getType(), new IntType())){
            throw new MyException("Not an int.");
        }
        int index = ((IntValue) val).getValue();

        if(!semaphoreTable.contains(index)){
            throw new MyException("Not a value in semaphoreTable.");
        }
        else{
            Pair<Integer, List<Integer>> pair = semaphoreTable.get(index);

            if(pair.getSecond().contains(state.getId())){
                pair.getSecond().remove(Integer.valueOf(state.getId()));
            }
        }
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.lookup(var);
        if(typevar.equals(new IntType())){
            return typeEnv;
        }
        else{
            throw new MyException("Release is not an int.");
        }
    }

    public String toString() {
        return "Release(" + var + ")";
    }
}
