package Model.exp;
import Exceptions.ExpressionEvalException;
import Exceptions.MyException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.IType;
import Model.value.IValue;

public class VarExp implements Exp {
    String id;

    public VarExp(String id){
        this.id = id;
    }

    public IValue eval(IDict<String,IValue> symTable, IHeap<IValue> heapTable) throws MyException {
        if(!symTable.isDefined(id)){
            throw new ExpressionEvalException("Variable was not defined.");
        }
        return symTable.lookup(id);
    }

    public String toString() {
        return id;
    }

    public Exp deepCopy() {
        return new VarExp(id);
    }

    public IType typecheck(IDict<String,IType> typeEnv) throws MyException{
        return typeEnv.lookup(id);
    }

}
