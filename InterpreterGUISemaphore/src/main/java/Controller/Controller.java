package Controller;
import Exceptions.*;
import Model.PrgState;
import Model.adt.IList;
import Model.adt.IStack;
import Model.stmt.IStmt;
import Model.value.IValue;
import Model.value.RefValue;
import Repo.IRepo;
import Controller.GarbageCollector;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller {

    IRepo repository;
    public ExecutorService executor;
    public GarbageCollector garbageCollector;

    public Controller(IRepo repo){
        this.repository = repo;
        this.garbageCollector = new GarbageCollector();
    }

    public IRepo getRepo(){
        return this.repository;
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAll(List<PrgState> prgList) throws MyException, InterruptedException {

        prgList.forEach(prg-> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println("One step failed " + e.toString());
            }
        });


        List<Callable<PrgState>>callList = prgList.stream()
                .map((PrgState p)-> (Callable<PrgState>)(()->{return p.oneStep();}))
                .collect(Collectors.toList());


        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future-> {
                    try{
                        return future.get();
                    } catch(InterruptedException | ExecutionException e){
                        System.out.println("One step failed " + e.toString());
                    }
                    return null;})
                .filter(p->p!=null)
                .collect(Collectors.toList());

        prgList.addAll(newPrgList);


        prgList.forEach(prg-> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                System.out.println("One step failed " + e.toString());
            }
        });



        repository.setPrgList(prgList);
    }

    public void allStep() throws MyException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repository.getPrgList());
        while (prgList.size()>0){
            repository.getPrgList().get(0).getHeapTable().setHeap(garbageCollector.unsafeGarbageCollector(
                            garbageCollector.getAddrFromSymTable(repository.getPrgList()),
                            garbageCollector.getAddrFromHeapTable(prgList.get(0).getHeapTable().getHeap().values()),
                            prgList.get(0).getHeapTable().getHeap()));

            oneStepForAll(prgList);

            for(Object a : prgList){
                System.out.println(a.toString());
            }
            prgList = removeCompletedPrg(repository.getPrgList());
        }

        executor.shutdownNow();
        repository.setPrgList(prgList);

    }

}
