package Repo;
import Exceptions.EmptyStackException;
import Exceptions.MyException;
import Model.PrgState;
import Model.adt.*;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.StringValue;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class Repo implements IRepo {

    List<PrgState> myPrgStates;
    String fileName;

    public Repo(String filenm) {
        myPrgStates = new ArrayList<PrgState>();
        fileName = filenm;

    }

    /*
    public PrgState getCrtPrg() throws MyException {
        return myPrgStates.pop();
    }


     */

    @Override
    public List<PrgState> getPrgList() {
        return this.myPrgStates;
    }

    @Override
    public void setPrgList(List<PrgState> prgList) {
        this.myPrgStates = prgList;
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    public void logPrgStateExec(PrgState state) throws MyException {
        try {
            PrintWriter logFile;

            logFile = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Diverse\\MAP\\InterpreterGUISemaphore\\"+fileName, true)));
            IStack<IStmt> stack = state.getExeStack();
            IDict<String, IValue> symTable = state.getSymTable();
            IList<IValue> queue = state.getOut();
            IDict<IValue, BufferedReader> fileTable = state.getFileTable();
            IHeap<IValue> heapTable = state.getHeapTable();
            ISemaphore<Integer, Pair<Integer, List<Integer>>> semaphoreTable = state.getSemaphoreTable();
            int id = state.getId();
            logFile.println("Program State ID:" + id);
            logFile.println("ExeStack:");
            ArrayList<IStmt> a = new ArrayList<IStmt>(stack.getStack());
            ListIterator<IStmt> li = a.listIterator(a.size());
            while (li.hasPrevious()) {
                logFile.println("-> " + li.previous().toString());
            }
            logFile.println("SymTable:");
            for (HashMap.Entry<String, IValue> e : symTable.getDict().entrySet()) {
                logFile.println("-> " + "Key: " + e.getKey().toString() + ", Value: " + e.getValue().toString());
            }
            logFile.println("Out:");
            ArrayList<IValue> a2 = new ArrayList<IValue>(queue.getList());
            ListIterator<IValue> li2 = a2.listIterator(a2.size());
            while (li2.hasPrevious()) {
                logFile.println("-> " + li2.previous().toString());
            }
            logFile.println("FileTable:");
            for (HashMap.Entry<IValue, BufferedReader> e : fileTable.getDict().entrySet()) {
                logFile.println("-> " + "Key: " + e.getKey().toString() + ", Value: " + e.getValue().toString());
            }
            logFile.println("HeapTable:");
            for (HashMap.Entry<Integer, IValue> e : heapTable.getHeap().entrySet()) {
                logFile.println("-> " + "Key: " + e.getKey().toString() + ", Value: " + e.getValue().toString());
            }
            logFile.println("BarrierTable:");
            for (HashMap.Entry<Integer, Pair<Integer, List<Integer>>> e : semaphoreTable.getDict().entrySet()) {
                logFile.println("-> " + "Key: " + e.getKey().toString() + ", Value: " + e.getValue().toString());
            }
            logFile.println("------------------------------------------");
            logFile.close();
        }
        catch(IOException e){
            throw new MyException(e.getMessage());
        }
    }

}
