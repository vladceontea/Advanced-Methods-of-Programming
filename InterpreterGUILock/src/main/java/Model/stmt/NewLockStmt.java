package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.ILockTable;
import Model.type.IType;
import Model.type.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class NewLockStmt implements IStmt{

    private String var;
    private static int nextFree = 1;

    public NewLockStmt(String v){
        this.var = v;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        ILockTable<Integer, Integer> lockTable = state.getLockTable();
        IDict<String, IValue> symTable = state.getSymTable();
        System.out.println(nextFree);

        synchronized (lockTable){
            lockTable.add(nextFree, -1);
        }

        if(symTable.isDefined(var)){
            symTable.update(var, new IntValue(nextFree));
        }
        else{
            symTable.add(var, new IntValue(nextFree));
        }

        nextFree++;
        return null;
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
        return "NewLock(" + var +")";
    }
}
