package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.exp.Exp;
import Model.type.IType;
import Model.value.IValue;
import Model.value.IntValue;

public class NewLatchStmt implements IStmt{

    private static int newFreeLocation = -1;
    private String var;
    private Exp exp;

    public NewLatchStmt(String v, Exp ex){
        this.var = v;
        this.exp = ex;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        try{
            IValue val = this.exp.eval(state.getSymTable(), state.getHeapTable());
            int number = ((IntValue) val).getValue();

            synchronized (state.getLatchTable()){
                ++newFreeLocation;
                state.getLatchTable().put(newFreeLocation, number);
                state.getSymTable().add(this.var, new IntValue(newFreeLocation));
                return null;
            }
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        return null;
    }
    public String toString() {
        return "newLatch(" + this.var + ", " + this.exp.toString() + ")";
    }
}
