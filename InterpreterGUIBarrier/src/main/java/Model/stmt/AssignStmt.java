package Model.stmt;

import Exceptions.MyException;
import Exceptions.NotDeclaredException;
import Exceptions.WrongAssignmentException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.type.IType;
import Model.value.IValue;

public class AssignStmt implements IStmt{

    String id;
    Exp exp;

    public AssignStmt(String _id, Exp ex){

        this.id = _id;
        this.exp = ex;
    }

    public PrgState execute(PrgState state) throws MyException{
        IStack<IStmt> stk = state.getExeStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<IValue> heapTbl = state.getHeapTable();

        if (symTbl.isDefined(id)){
            IValue val = exp.eval(symTbl, heapTbl);
            IType typId = (symTbl.lookup(id)).getType();
            if (val.getType().equals(typId)){
                symTbl.update(id, val);
            }
            else{
                throw new WrongAssignmentException("The type of the variable and the type of the expression do not match.");
            }
        }
        else{
            throw new NotDeclaredException("The used variable was not declared before.");
        }

        return null;
    }

    public String toString(){
        return this.id + "=" + this.exp.toString();
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typevar = typeEnv.lookup(id);
        IType typexp = exp.typecheck(typeEnv);
        if(typevar.equals(typexp)){
            return typeEnv;
        }
        else{
            throw new MyException("Assignment: right hand side and left hand side have different types. ");
        }
    }
}
