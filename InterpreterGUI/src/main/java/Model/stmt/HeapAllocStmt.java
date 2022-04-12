package Model.stmt;

import Exceptions.MyException;
import Exceptions.NotDeclaredException;
import Exceptions.WrongAssignmentException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.type.BoolType;
import Model.type.IType;
import Model.type.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class HeapAllocStmt implements IStmt {

    String varName;
    Exp exp;

    public HeapAllocStmt(String varNm, Exp ex){
        this.varName = varNm;
        this.exp = ex;
    }


    @Override
    public PrgState execute(PrgState state) throws MyException {
        IStack<IStmt> stk = state.getExeStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<IValue> heapTbl = state.getHeapTable();

        if (symTbl.isDefined(varName) && symTbl.lookup(varName).getType() instanceof RefType){
            IValue val = exp.eval(symTbl, heapTbl);
            IType locationType = ((RefValue) symTbl.lookup(varName)).getLocationType();

            if(val.getType().getClass() == locationType.getClass()){
                heapTbl.add(val);
                int address = heapTbl.getFreeLocation();
                address--;
                symTbl.update(varName, new RefValue(address, locationType));
            }
            else{
                throw new WrongAssignmentException("The type of the variable and the type of the expression do not match.");
            }
        }
        else{
            throw new NotDeclaredException("The used variable was not declared before or wrong type.");
        }

        return null;
    }

    public String toString(){
        return "new(" + this.varName + ", " + this.exp.toString() + ")";
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typevar = typeEnv.lookup(varName);
        IType typexp = exp.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp))){
            return typeEnv;
        }
        else{
            throw new MyException("NEW stmt: right hand side and left hand side have different types.");
        }
    }
}
