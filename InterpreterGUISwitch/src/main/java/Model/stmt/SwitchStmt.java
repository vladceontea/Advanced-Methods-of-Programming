package Model.stmt;

import Exceptions.MyException;
import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.IStack;
import Model.exp.Exp;
import Model.type.IType;
import Model.type.IntType;
import Model.value.IValue;

public class SwitchStmt implements IStmt{

    Exp exp, exp1, exp2;
    IStmt stmt1, stmt2, stmt3;

    public SwitchStmt(Exp ex, Exp ex1, IStmt st1, Exp ex2, IStmt st2, IStmt st3){
        this.exp = ex;
        this.exp1 = ex1;
        this.exp2 = ex2;
        this.stmt1 = st1;
        this.stmt2 = st2;
        this.stmt3 = st3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        IStack<IStmt> stk = state.getExeStack();
        IDict<String, IValue> symTbl = state.getSymTable();
        IHeap<IValue> heapTbl = state.getHeapTable();

        if(exp.eval(symTbl, heapTbl).equals(exp1.eval(symTbl,heapTbl))){
            stk.push(stmt1);
        }
        else{
            if(exp.eval(symTbl, heapTbl).equals(exp2.eval(symTbl,heapTbl))){
                stk.push(stmt2);
            }
            else{
                stk.push(stmt3);
            }
        }

        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws MyException {
        IType typexp = exp.typecheck(typeEnv);
        IType typexp1 = exp1.typecheck(typeEnv);
        IType typexp2 = exp2.typecheck(typeEnv);
        if(typexp.equals(typexp1) && typexp.equals(typexp2) && typexp.equals(new IntType())){
            stmt1.typecheck(typeEnv.deepCopy());
            stmt2.typecheck(typeEnv.deepCopy());
            stmt3.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else{
            throw new MyException("The conditions of SWITCH has different types.");
        }
    }

    public String toString() {
        return "SWITCH(" + this.exp.toString() + ")\n" +
                "CASE(" + this.exp1 + "): " + this.stmt1.toString() + ")\n" +
                "CASE(" + this.exp2 + "): " + this.stmt2.toString() + ")\n" +
                "DEFAULT: " + this.stmt3.toString() + ")";
    }
}
