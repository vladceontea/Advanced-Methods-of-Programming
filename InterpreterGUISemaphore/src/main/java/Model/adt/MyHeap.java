package Model.adt;

import Model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<V> implements IHeap<V>{

    Map<Integer, V> heapMap;
    int freeLocation = 1;

    public MyHeap(){
        this.heapMap = new HashMap<Integer, V>();
    }

    @Override
    public void add(V v) {
        this.heapMap.put(freeLocation, v);
        freeLocation++;
    }

    @Override
    public void update(int key, V v) {
        this.heapMap.replace(key, v);
    }

    @Override
    public V lookup(int id) {
        return this.heapMap.get(id);
    }

    @Override
    public boolean isDefined(int id) {
        return this.heapMap.containsKey(id);
    }

    public int getFreeLocation(){
        return this.freeLocation;
    }

    @Override
    public Map<Integer, V> getHeap() {
        return this.heapMap;
    }

    @Override
    public void setHeap(Map<Integer, V> map) {
        this.heapMap = map;
    }

    public String toString(){
        return this.heapMap.toString();
    }
}
