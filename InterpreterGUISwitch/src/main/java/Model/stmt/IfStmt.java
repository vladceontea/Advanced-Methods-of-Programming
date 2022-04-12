package Model.stmt;

import Exceptions.MyException;
import Exceptions.NotBooleanException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.type.BoolType;
import Model.type.IType;
import Model.value.BoolValue;
import Model.value.IValue;

public class IfStmt implements IStmt{

    Exp exp;
    IStmt thenS;
    IStmt elseS;

    public IfStmt(Exp ex, IStmt thenR, IStmt elseR){
        this.exp = ex;
        this.thenS = thenR;
        this.elseS = elseR;
    }

    public PrgState execute(PrgState state) throws MyException{
        IStack<IStmt> stk = state.getExeStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<IValue> heapTbl = state.getHeapTable();
        IType cond = exp.eval(symTbl, heapTbl).getType();
        BoolValue obj = new BoolValue(true);
        if(cond instanceof BoolType){
            if (exp.eval(symTbl, heapTbl).equals(obj)){
                stk.push(thenS);
            }
            else{
                stk.push(elseS);
            }
        }
        else{
            throw new NotBooleanException("Not a boolean result.");
        }

        return null;
    }

    public String toString(){
        return "IF(" + this.exp.toString() + ")THEN(" + this.thenS.toString() + ")ELSE(" + this.elseS.toString() + "))";
    }

    public IDict<String, IType> typecheck(IDict<String,IType> typeEnv) throws MyException{
        IType typexp = exp.typecheck(typeEnv);
        if(typexp.equals(new BoolType())){
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else {
            throw new MyException("The condition of IF has not the type bool.");
        }
    }
}
