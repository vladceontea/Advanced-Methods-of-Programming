package Model.stmt;

import Exceptions.AlreadyDeclaredException;
import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.type.IType;
import Model.value.IValue;


public class VarDeclStmt implements IStmt{

    String id;
    IType typ;

    public VarDeclStmt(String id, IType varType){
        this.id = id;
        this.typ = varType;
    }

    public PrgState execute(PrgState state) throws MyException{
        IDict<String, IValue> symTbl = state.getSymTable();

        if (symTbl.isDefined(id)){
            throw new AlreadyDeclaredException("This variable was already declared.");
        }
        else{
            symTbl.add(id, typ.defaultValue());
        }

        return null;
    }

    public String toString(){
        return this.id + " is " + this.typ;
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{

        typeEnv.add(id, typ);
        return typeEnv;
    }
}
