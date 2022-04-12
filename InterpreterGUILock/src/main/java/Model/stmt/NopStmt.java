package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.type.IType;

public class NopStmt implements IStmt{

    public PrgState execute(PrgState state){
        return null;
    }

    public String toString(){
        return "No operation.";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        return typeEnv;
    }
}
