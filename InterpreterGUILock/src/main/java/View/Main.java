package View;
import Exceptions.MyException;
import Model.adt.*;
import Model.exp.*;
import Model.stmt.*;
import Model.stmtFile.CloseRFileStmt;
import Model.stmtFile.OpenRFileStmt;
import Model.stmtFile.ReadFileStmt;
import Model.type.*;
import Model.value.*;
import Repo.IRepo;
import Repo.Repo;
import Controller.Controller;
import Model.PrgState;

import java.io.BufferedReader;


public class Main {

    public static void main(String[] args) throws MyException {

        IDict<String, IType> typeEnv = new MyDict<String, IType>();

    // ex 1:  int v; v = 2; Print(v)
        IStmt ex1= new CompStmt(new VarDeclStmt("v",new IntType()),
                new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));
        IRepo repo1 = new Repo("log1b.txt");
        Controller ctr1 = new Controller(repo1);
        IStack<IStmt> exeStack1 = new MyStack<IStmt>();
        IDict<String, IValue> symTable1 = new MyDict<String, IValue>();
        IList<IValue> out1 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable1 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable1 = new MyHeap<IValue>();
        ILockTable<Integer, Integer> lockTable1 = new MyLockTable<Integer, Integer>();

    // ex 2: a=2+3*5;b=a+1;Print(b)
        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()), new CompStmt(new VarDeclStmt("b",new IntType()),
                new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',
                        new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),  new CompStmt(
                        new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))),
                        new PrintStmt(new VarExp("b"))))));
        IRepo repo2 = new Repo("log2.txt");
        Controller ctr2 = new Controller(repo2);
        IStack<IStmt> exeStack2 = new MyStack<IStmt>();
        IDict<String, IValue> symTable2 = new MyDict<String, IValue>();
        IList<IValue> out2 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable2 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable2 = new MyHeap<IValue>();
        ILockTable<Integer, Integer> lockTable2 = new MyLockTable<Integer, Integer>();

    // ex 3: bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()), new CompStmt(new VarDeclStmt("v",
            new IntType()),new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
            new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))),
                    new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
                    VarExp("v"))))));
        IRepo repo3 = new Repo("log3.txt");
        Controller ctr3 = new Controller(repo3);
        IStack<IStmt> exeStack3 = new MyStack<IStmt>();
        IDict<String, IValue> symTable3 = new MyDict<String, IValue>();
        IList<IValue> out3 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable3 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable3 = new MyHeap<IValue>();
        ILockTable<Integer, Integer> lockTable3 = new MyLockTable<Integer, Integer>();

    // ex4: string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
        IStmt ex4 =new CompStmt(new VarDeclStmt("varf",new StringType()), new CompStmt(new AssignStmt("varf",new ValueExp(new StringValue("test.in"))),
                     new CompStmt(new OpenRFileStmt(new VarExp("varf")), new CompStmt(new VarDeclStmt("varc", new IntType()),
                             new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                     new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"), new CompStmt(new PrintStmt(new VarExp("varc")),
                                             new CloseRFileStmt(new VarExp("varf"))))))))));
        IRepo repo4 = new Repo("log4.txt");
        Controller ctr4 = new Controller(repo4);
        IStack<IStmt> exeStack4 = new MyStack<IStmt>();
        IDict<String, IValue> symTable4 = new MyDict<String, IValue>();
        IList<IValue> out4 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable4 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable4 = new MyHeap<IValue>();
        ILockTable<Integer, Integer> lockTable4 = new MyLockTable<Integer, Integer>();

        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
                     new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v"))), new CompStmt(new HeapWriteStmt("v", new ValueExp(new IntValue(30))),
                             new PrintStmt(new ArithExp('+', new HeapReadExp(new VarExp("v")), new ValueExp(new IntValue(5))))))));

        /*
        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
                     new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocStmt("a", new VarExp("v")),
                            new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));



        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocStmt("a", new VarExp("v")),
                        new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v"))), new PrintStmt(new ArithExp('+',new HeapReadExp(
                                new HeapReadExp(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));


        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(20))),
                new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(new HeapAllocStmt("a", new VarExp("v")),
                        new CompStmt(new HeapAllocStmt("v", new ValueExp(new IntValue(30))), new PrintStmt(new HeapReadExp(new HeapReadExp(new VarExp("a")))))))));

        */
        IRepo repo5 = new Repo("log5.txt");
        Controller ctr5 = new Controller(repo5);
        IStack<IStmt> exeStack5 = new MyStack<IStmt>();
        IDict<String, IValue> symTable5 = new MyDict<String, IValue>();
        IList<IValue> out5 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable5 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable5 = new MyHeap<IValue>();
        ILockTable<Integer, Integer> lockTable5 = new MyLockTable<Integer, Integer>();


        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                new CompStmt(new WhileStmt(new RelatExp(">", new VarExp("v"), new ValueExp(new IntValue(0))), new CompStmt(
                        new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))),
                        new PrintStmt(new VarExp("v")))));
        IRepo repo6 = new Repo("log6.txt");
        Controller ctr6 = new Controller(repo6);
        IStack<IStmt> exeStack6 = new MyStack<IStmt>();
        IDict<String, IValue> symTable6 = new MyDict<String, IValue>();
        IList<IValue> out6 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable6 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable6 = new MyHeap<IValue>();
        ILockTable<Integer, Integer> lockTable6 = new MyLockTable<Integer, Integer>();

        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(new HeapAllocStmt("a", new ValueExp(new IntValue(22))),
                        new CompStmt(new ForkStmt(new CompStmt(new HeapWriteStmt("a", new ValueExp(new IntValue(30))), new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExp(new VarExp("a"))))))),
                                    new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new HeapReadExp(new VarExp("a")))))))));

        IRepo repo10 = new Repo("log10.txt");
        Controller ctr10 = new Controller(repo10);
        IStack<IStmt> exeStack10 = new MyStack<IStmt>();
        IDict<String, IValue> symTable10 = new MyDict<String, IValue>();
        IList<IValue> out10 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable10 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable10 = new MyHeap<IValue>();
        ILockTable<Integer, Integer> lockTable10 = new MyLockTable<Integer, Integer>();

        IStmt ex11 = new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())), new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("x", new IntType()), new CompStmt(new VarDeclStmt("q", new IntType()), new CompStmt(new HeapAllocStmt("v1",
                                new ValueExp(new IntValue(20))), new CompStmt(new HeapAllocStmt("v2", new ValueExp(new IntValue(30))), new CompStmt(
                                        new NewLockStmt("x"), new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(new CompStmt(new LockStmt("x"),
                                new CompStmt(new HeapWriteStmt("v1", new ArithExp('-', new HeapReadExp(new VarExp("v1")), new ValueExp(new IntValue(1)))),
                                        new UnlockStmt("x")))), new CompStmt(new LockStmt("x"), new CompStmt(new HeapWriteStmt("v1", new ArithExp('*', new HeapReadExp(new VarExp("v1")),
                                new ValueExp(new IntValue(10)))), new UnlockStmt("x"))))), new CompStmt(new NewLockStmt("q"), new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(new CompStmt(new LockStmt("q"),
                                new CompStmt(new HeapWriteStmt("v2", new ArithExp('+', new HeapReadExp(new VarExp("v2")), new ValueExp(new IntValue(5)))),
                                        new UnlockStmt("q")))), new CompStmt(new LockStmt("q"), new CompStmt(new HeapWriteStmt("v2", new ArithExp('*', new HeapReadExp(new VarExp("v2")),
                                new ValueExp(new IntValue(10)))), new UnlockStmt("q"))))), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),
                                new CompStmt(new NopStmt(), new CompStmt(new LockStmt("x"), new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v1"))), new CompStmt(
                                        new UnlockStmt("x"), new CompStmt(new LockStmt("q"), new CompStmt(new PrintStmt(new HeapReadExp(new VarExp("v2"))),
                                        new UnlockStmt("q"))))))))))))))))))));

        IRepo repo11 = new Repo("log11.txt");
        Controller ctr11 = new Controller(repo11);
        IStack<IStmt> exeStack11 = new MyStack<IStmt>();
        IDict<String, IValue> symTable11 = new MyDict<String, IValue>();
        IList<IValue> out11 = new MyList<IValue>();
        IDict<IValue, BufferedReader> fileTable11 = new MyDict<IValue, BufferedReader>();
        IHeap<IValue> heapTable11 = new MyHeap<IValue>();
        ILockTable<Integer, Integer> lockTable11 = new MyLockTable<Integer, Integer>();

        PrgState newProg1 = new PrgState(exeStack1, symTable1, out1, fileTable1, heapTable1, lockTable1, ex1);
        PrgState newProg2 = new PrgState(exeStack2, symTable2, out2, fileTable2, heapTable2, lockTable2, ex2);
        PrgState newProg3 = new PrgState(exeStack3, symTable3, out3, fileTable3, heapTable3, lockTable3, ex3);
        repo1.addPrg(newProg1);
        repo2.addPrg(newProg2);
        repo3.addPrg(newProg3);

        PrgState newProg4 = new PrgState(exeStack4, symTable4, out4, fileTable4, heapTable4, lockTable4, ex4);
        repo4.addPrg(newProg4);

        PrgState newProg5 = new PrgState(exeStack5, symTable5, out5, fileTable5, heapTable5, lockTable5, ex5);
        repo5.addPrg(newProg5);

        PrgState newProg6 = new PrgState(exeStack6, symTable6, out6, fileTable6, heapTable6, lockTable6, ex6);
        repo6.addPrg(newProg6);

        PrgState newProg10 = new PrgState(exeStack10, symTable10, out10, fileTable10, heapTable10, lockTable10, ex10);
        repo10.addPrg(newProg10);
        //ex10.typecheck(typeEnv);

        PrgState newProg11 = new PrgState(exeStack11, symTable11, out11, fileTable11, heapTable11, lockTable11, ex11);
        repo11.addPrg(newProg11);
        ex11.typecheck(typeEnv);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunCommand("1",ex1.toString(),ctr1));
        menu.addCommand(new RunCommand("2",ex2.toString(),ctr2));
        menu.addCommand(new RunCommand("3",ex3.toString(),ctr3));
        menu.addCommand(new RunCommand("4",ex4.toString(),ctr4));
        menu.addCommand(new RunCommand("5",ex5.toString(),ctr5));
        menu.addCommand(new RunCommand("6",ex6.toString(),ctr6));
        menu.addCommand(new RunCommand("10",ex10.toString(),ctr10));
        menu.addCommand(new RunCommand("11",ex11.toString(),ctr11));
        menu.show();

    }
}


