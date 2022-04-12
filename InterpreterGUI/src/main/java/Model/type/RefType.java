package Model.type;

import Model.value.IValue;
import Model.value.RefValue;

public class RefType implements IType{

    IType inner;

    public RefType(IType inn){
        this.inner = inn;
    }

    public IType getInner(){
        return this.inner;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof RefType){
            return this.inner.equals(((RefType) o).getInner());
        }
        else{
            return false;
        }
    }

    @Override
    public IValue defaultValue() {
        return new RefValue(0, this.inner);
    }

    @Override
    public IType deepCopy() {
        return new RefType(this.inner);
    }

    public String toString(){
        return "Ref(" + this.inner.toString() + ")";
    }
}
