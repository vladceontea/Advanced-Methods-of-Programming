package Model.exp;
import Exceptions.MyException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.IType;
import Model.value.IValue;

public interface Exp {

    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws MyException;
    public String toString();
    public Exp deepCopy();
    public IType typecheck(IDict<String,IType> typeEnv) throws MyException;
}
