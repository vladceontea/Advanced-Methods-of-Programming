package Model.value;

import Model.type.IType;
import Model.type.RefType;

public class RefValue implements IValue{

    int address;
    IType locationType;

    public RefValue(IType loc){
        this.locationType = loc;
        this.address = 0;
    }

    public RefValue(int add, IType loc){
        this.address = add;
        this.locationType = loc;
    }

    public int getAddress() {
        return this.address;
    }

    public String toString(){
        return "(" + Integer.toString(this.address) + ", " + this.locationType.toString() + ")";
    }

    @Override
    public IType getType() {
        return new RefType(locationType);
    }

    public IType getLocationType(){
        return this.locationType;
    }

    @Override
    public IValue deepCopy() {
        return new RefValue(this.address, this.locationType);
    }
}
