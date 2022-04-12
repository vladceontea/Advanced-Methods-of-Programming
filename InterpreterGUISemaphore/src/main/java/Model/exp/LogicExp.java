package Model.exp;

import Exceptions.*;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.BoolType;
import Model.type.IType;
import Model.type.IntType;
import Model.value.BoolValue;
import Model.value.IValue;


public class LogicExp implements Exp {
    int op;
    Exp e1, e2;

    public LogicExp(String operator, Exp ex1, Exp ex2){

        this.e1 = ex1;
        this.e2 = ex2;

        if(operator.equals("&&")){
            this.op = 1;
        }
        if(operator.equals("||")){
            this.op = 2;
        }

    }

    public IValue eval(IDict<String, IValue> symTable, IHeap<IValue> heapTable) throws MyException {
        IValue v1, v2;
        v1 = e1.eval(symTable, heapTable);

        if(v1.getType().equals(new BoolType())){
            v2 = e2.eval(symTable, heapTable);
            if(v2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue) v1;
                BoolValue i2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = i1.getValue();
                n2 = i2.getValue();

                if(op == 1){
                    return new BoolValue(n1&&n2);
                }
                if(op == 2){
                    return new BoolValue(n1||n2);
                }
            }
            else{
                throw new NotBooleanException("Second operand is not a boolean.");
            }
        }
        else{
            throw new NotBooleanException("First operand is not a boolean.");
        }
        return null;
    }

    public int getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    public String toString() {
        String operator = null;
        if(op == 1){
            operator = "&&";
        }
        if(op == 2){
            operator = "||";
        }
        return e1.toString() + " " + operator + " " + e2.toString();
    }

    public Exp deepCopy(){
        String operator = null;
        if(op == 1){
            operator = "&&";
        }
        if(op == 2){
            operator = "||";
        }
        return new LogicExp(operator, e1, e2);
    }

    public IType typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType t1, t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);

        if (t1.equals(new BoolType())){
            if (t2.equals(new BoolType())){
                return new BoolType();
            }
            else{
                throw new MyException("Second operand is not a boolean.");
            }
        }
        else{
            throw new MyException("First operand is not a boolean.");
        }
    }
}

