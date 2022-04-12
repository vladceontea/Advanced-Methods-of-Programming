package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.Pair;
import Model.exp.Exp;
import Model.type.IType;
import Model.value.IntValue;

import java.util.ArrayList;
import java.util.Objects;

public class NewBarrierStmt implements IStmt{

    private String var;
    private Exp exp;
    private int nextFree = 1;

    public NewBarrierStmt(String v, Exp e){
        this.var=v;
        this.exp=e;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        try{
            if(!Objects.equals(exp.eval(state.getSymTable(), state.getHeapTable()), new IntValue(0))){
                synchronized (state.getBarrierTable()){
                    state.getBarrierTable().add(nextFree, new Pair(((IntValue) exp.eval(state.getSymTable(), state.getHeapTable())).getValue(), new ArrayList<Integer>()));
                }
                if(state.getSymTable().isDefined(var)){
                    state.getSymTable().update(var, new IntValue(nextFree));
                }
                else{
                    state.getSymTable().add(var, new IntValue(nextFree));
                }
            }
            nextFree++;
            return null;
        }
        catch (MyException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        return null;
    }

    public String toString() {
        return "NewBarrierStmt(" + var + ", "+ exp + ")";
    }
}
