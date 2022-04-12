package Model.exp;

import Exceptions.MyException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.IType;
import Model.value.IValue;

public class ValueExp implements Exp {
    IValue e;

    public ValueExp(IValue value){
        this.e = value;
    }

    public IValue eval(IDict<String, IValue> sybTable, IHeap<IValue> heapTable){
        return this.e;
    }

    public String toString(){
        return e.toString();
    }

    public Exp deepCopy() {
        return new ValueExp(e);
    }

    public IType typecheck(IDict<String,IType> typeEnv) throws MyException{
        return e.getType();
    }
}
