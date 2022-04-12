package Model;
import Exceptions.EmptyStackException;
import Exceptions.MyException;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

public class PrgState {

    IStack<IStmt> exeStack;
    IDict<String, IValue> symTable;
    IList<IValue> out;
    IDict<IValue, BufferedReader> fileTable;
    IHeap<IValue> heapTable;
    ILatchTable<Integer, Integer> latchTable;
    IStmt originalProgram;
    int id;
    static int lastId = 0;

    public PrgState(IStack<IStmt> stk, IDict<String, IValue> symtbl, IList<IValue> ut, IDict<IValue, BufferedReader> filetab, IHeap<IValue> heaptbl, ILatchTable<Integer, Integer> latchtbl, IStmt prg){
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ut;
        this.fileTable = filetab;
        this.heapTable = heaptbl;
        this.latchTable = latchtbl;
        this.id = PrgState.newId();
        this.originalProgram = prg;

        this.exeStack.push(prg);

    }
    public PrgState oneStep() throws MyException {
        if(this.exeStack.isEmpty()) {
            throw new EmptyStackException("The program stack is empty.");
        }
        IStmt crtStmt = this.exeStack.pop();
        return crtStmt.execute(this);
    }

    public boolean isNotCompleted(){
        return !this.exeStack.isEmpty();
    }

    public static int newId(){
        lastId++;
        return lastId;
    }

    public int getId(){
        return this.id;
    }

    public IStack<IStmt> getExeStack(){
        return this.exeStack;
    }

    public IDict<String, IValue> getSymTable(){
        return this.symTable;
    }

    public IList<IValue> getOut(){
        return this.out;
    }

    public void setExeStack(IStack<IStmt> stk){
        this.exeStack = stk;
    }

    public void setSymTable(IDict<String, IValue> symtbl){
        this.symTable = symtbl;
    }

    public void setOut(IList<IValue> ut){
        this.out = ut;
    }

    public IDict<IValue, BufferedReader> getFileTable(){
        return this.fileTable;
    }

    public void setFileTable(IDict<IValue, BufferedReader>filetab){
        this.fileTable = filetab;
    }

    public IHeap<IValue> getHeapTable(){
        return this.heapTable;
    }

    public void setHeapTable(IHeap<IValue> heaptbl){
        this.heapTable = heaptbl;
    }

    public ILatchTable<Integer, Integer> getLatchTable(){
        return this.latchTable;
    }

    public void setLatchTable(ILatchTable<Integer, Integer> latchtbl){
        this.latchTable = latchtbl;
    }

    public IStmt getOriginalProgram(){
        return this.originalProgram;
    }

    public void setOriginalProgram(IStmt oriprog){
        this.originalProgram = oriprog;
    }

    public String toString(){
        return "Current State:" + "\nStateID = " + this.id + "\nExeStack = " + this.exeStack + "\nSymTable = " + this.symTable + "\nFileTable = " + this.fileTable + "\nHeapTable = " + this.heapTable + "\nLatchTable" + this.latchTable + "\nOut = " + this.out + "\n";
    }

}