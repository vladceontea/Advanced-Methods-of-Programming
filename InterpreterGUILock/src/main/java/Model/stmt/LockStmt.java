package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.ILockTable;
import Model.type.IType;
import Model.type.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class LockStmt implements IStmt{

    private String var;

    public LockStmt(String v){
        this.var = v;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IDict<String, IValue> symTable = state.getSymTable();
        ILockTable<Integer, Integer> lockTable = state.getLockTable();

        if(!symTable.isDefined(var)){
            throw new MyException("Not in the symbol table.");
        }

        IValue val = symTable.lookup(var);

        int index = ((IntValue) val).getValue();

        synchronized (lockTable){
            if(!lockTable.contains(index)){
                throw new MyException("Not in lock table.");
            }

            if(lockTable.get(index) == -1){
                lockTable.update(index, state.getId());
            }
            else{
                state.getExeStack().push(this);
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
            throw new MyException("newLock is not an int.");
        }
    }

    public String toString()
    {
        return "Lock(" + var + ")";
    }
}
