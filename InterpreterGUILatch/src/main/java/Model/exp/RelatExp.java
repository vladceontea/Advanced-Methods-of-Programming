package Model.exp;

import Exceptions.DivisionByZeroException;
import Exceptions.MyException;
import Exceptions.NotIntegerException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.IType;
import Model.type.IntType;
import Model.value.BoolValue;
import Model.value.IValue;
import Model.value.IntValue;

public class RelatExp implements Exp{
    String op;
    Exp e1, e2;

    public RelatExp(String operator, Exp ex1, Exp ex2){
        this.e1 = ex1;
        this.e2 = ex2;
        this.op = operator;

    }

    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(symTable, heapTable);

        if(v1.getType().equals(new IntType())){
            v2 = e2.eval(symTable, heapTable);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();

                if(op.equals("<")){
                    return new BoolValue(n1<n2);
                }
                if(op.equals("<=")){
                    return new BoolValue(n1<=n2);
                }
                if(op.equals("==")){
                    return new BoolValue(n1==n2);
                }
                if(op.equals("!=")){
                    return new BoolValue(n1!=n2);
                }
                if(op.equals(">")){
                    return new BoolValue(n1>n2);
                }
                if(op.equals(">=")){
                    return new BoolValue(n1>=n2);
                }
            }
            else{
                throw new NotIntegerException("Second operand is not an integer.");
            }
        }
        else{
            throw new NotIntegerException("First operand is not an integer.");
        }
        return null;
    }

    public String getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    @Override
    public String toString() {
        return e1.toString() + op + e2.toString();
    }

    public Exp deepCopy() {
        return new RelatExp(op,e1,e2);
    }

    public IType typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType t1, t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);

        if (t1.equals(new IntType())){
            if (t2.equals(new IntType())){
                return new IntType();
            }
            else{
                throw new MyException("Second operand is not an integer.");
            }
        }
        else{
            throw new MyException("First operand is not an integer.");
        }
    }
}
