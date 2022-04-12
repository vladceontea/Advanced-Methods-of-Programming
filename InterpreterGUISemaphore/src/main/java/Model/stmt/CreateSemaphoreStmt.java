package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.ISemaphore;
import Model.adt.Pair;
import Model.exp.Exp;
import Model.type.IType;
import Model.type.IntType;
import Model.value.IValue;
import Model.value.IntValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateSemaphoreStmt implements IStmt{

    private String var;
    private Exp exp;
    private int nextFree = 0;

    public CreateSemaphoreStmt(String v, Exp e){
        this.var = v;
        this.exp = e;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        IDict<String, IValue> symTable = state.getSymTable();
        IHeap<IValue> heapTable = state.getHeapTable();
        ISemaphore<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();

        if(!Objects.equals(exp.eval(symTable, heapTable).getType(), new IntType())){
            throw new MyException("Exp is not an integer.");
        }

        synchronized (semaphoreTable){
            semaphoreTable.add(nextFree, new Pair(((IntValue) exp.eval(symTable, heapTable)).getValue(), new ArrayList<Integer>()));
        }
        if(symTable.isDefined(var) && Objects.equals(symTable.lookup(var).getType(), new IntType())){
            state.getSymTable().update(var, new IntValue(nextFree));
        }
        else{
            throw new MyException("Not in symTable or not an integer.");
        }
        nextFree++;
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        IType typevar = typeEnv.lookup(var);
        IType typexp = exp.typecheck(typeEnv);
        if(typevar.equals(new IntType()) && typexp.equals(new IntType())){
            return typeEnv;
        }
        else{
            throw new MyException("NewSemaphore is not an int.");
        }
    }

    public String toString() {
        return "newSemaphore( " + var + ", " + exp.toString() + ")";
    }
}
