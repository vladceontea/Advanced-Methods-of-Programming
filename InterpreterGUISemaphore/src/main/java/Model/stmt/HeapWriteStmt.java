package Model.stmt;

import Exceptions.MyException;
import Exceptions.NotDeclaredException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.type.IType;
import Model.type.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class HeapWriteStmt implements IStmt {

    String varName;
    Exp exp;

    public HeapWriteStmt(String varNm, Exp ex){
        this.varName = varNm;
        this.exp = ex;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stk = state.getExeStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<IValue> heapTbl = state.getHeapTable();

        if(symTbl.isDefined(varName) && symTbl.lookup(varName).getType() instanceof RefType){
            int address = ((RefValue) symTbl.lookup(varName)).getAddress();
            if(heapTbl.isDefined(address)){
                IValue val = exp.eval(symTbl, heapTbl);
                IType locationType = ((RefValue) symTbl.lookup(varName)).getLocationType();
                if(val.getType().getClass() == locationType.getClass()){
                    heapTbl.update(address, val);
                }
                else{
                    throw new MyException("The types do not match.");
                }
            }
            else{
                throw new MyException("The address does not exist.");
            }
        }
        else{
            throw new NotDeclaredException("The used variable was not declared before or wrong type.");
        }

        return null;
    }

    public String toString(){
        return "wH(" + this.varName + ", " + this.exp.toString() + ")";
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typevar = typeEnv.lookup(varName);
        IType typexp = exp.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp))){
            return typeEnv;
        }
        else{
            throw new MyException("WRITE stmt: right hand side and left hand side have different types.");
        }
    }
}
