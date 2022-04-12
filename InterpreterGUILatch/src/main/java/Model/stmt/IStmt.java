package Model.stmt;

import Exceptions.*;
import Model.PrgState;
import Model.adt.IDict;
import Model.type.IType;

public interface IStmt {

    PrgState execute(PrgState state) throws MyException;
    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException;
}
