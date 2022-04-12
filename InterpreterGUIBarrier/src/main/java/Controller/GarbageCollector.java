package Controller;

import Model.PrgState;
import Model.adt.IHeap;
import Model.value.IValue;
import Model.value.RefValue;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GarbageCollector {
    public Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, List<Integer> heapTableAddr, Map<Integer, IValue> heap){


        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey())  || heapTableAddr.contains(e.getKey()))
                .collect(Collectors.toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public List<Integer> getAddrFromSymTable(List<PrgState> prgList){

        List<Integer> addresses = new ArrayList<Integer>();
        for (PrgState prg: prgList){
            List<Integer> values = prg.getSymTable().getDict().values().stream()
                        .filter(v-> v instanceof RefValue)
                        .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                        .collect(Collectors.toList());
            addresses.addAll(values);
        }
        return addresses;
    }

    public List<Integer> getAddrFromHeapTable(Collection<IValue> heapTableValues) {
        return heapTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

}
