package Model.exp;
import Exceptions.*;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.type.IType;
import Model.type.IntType;
import Model.value.IValue;
import Model.value.IntValue;

public class ArithExp implements Exp {
    int op;
    Exp e1, e2;

    public ArithExp(char operator, Exp ex1, Exp ex2){
        this.e1 = ex1;
        this.e2 = ex2;

        if(operator == '+'){
            this.op = 1;
        }
        if(operator == '-'){
            this.op = 2;
        }
        if(operator == '*'){
            this.op = 3;
        }
        if(operator == '/'){
            this.op = 4;
        }

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

                if(op == 1){
                    return new IntValue(n1+n2);
                }
                if(op == 2){
                    return new IntValue(n1-n2);
                }
                if(op == 3){
                    return new IntValue(n1*n2);
                }
                if(op == 4){
                    if (n2==0){
                        throw new DivisionByZeroException("Cannot solve division by zero");
                    }
                    else{
                        return new IntValue(n1/n2);
                    }
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

    public int getOp() {return this.op;}

    public Exp getFirst() {
        return this.e1;
    }

    public Exp getSecond() {
        return this.e2;
    }

    public String toString() {
        char operator = 0;
        if(op == 1){
            operator = '+';
        }
        if(op == 2){
            operator = '-';
        }
        if(op == 3){
            operator = '*';
        }
        if(op == 4){
            operator = '/';
        }
        return e1.toString() + " " + operator + " " + e2.toString();
    }

    public Exp deepCopy(){
        char operator = 0;
        if(op == 1){
            operator = '+';
        }
        if(op == 2){
            operator = '-';
        }
        if(op == 3){
            operator = '*';
        }
        if(op == 4){
            operator = '/';
        }
        return new ArithExp(operator, e1, e2);
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
