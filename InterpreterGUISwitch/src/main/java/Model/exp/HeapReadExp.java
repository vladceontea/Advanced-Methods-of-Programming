package Model.exp;

import Exceptions.MyException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.IType;
import Model.type.IntType;
import Model.type.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class HeapReadExp implements Exp{

    Exp exp;

    public HeapReadExp(Exp ex){
        this.exp = ex;
    }
    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws MyException {

        IValue value = exp.eval(symTable, heapTable);

        if (value instanceof RefValue){
            int address = ((RefValue) value).getAddress();
            if(heapTable.isDefined(address)){
                IValue val = heapTable.lookup(address);
                return val;
            }
            else{
                throw new MyException("The address does not exist.");
            }
        }
        else{
            throw new MyException("Not a ref value.");
        }
    }

    @Override
    public Exp deepCopy() {
        return new HeapReadExp(this.exp);
    }

    public String toString(){
        return "rH(" + this.exp.toString() + ")";
    }

    public IType typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typ = exp.typecheck(typeEnv);
        if(typ instanceof RefType){
            RefType reft = (RefType) typ;
            return reft.getInner();
        }
        else{
            throw new MyException("The rH argument is not a RefType.");
        }
    }
}
