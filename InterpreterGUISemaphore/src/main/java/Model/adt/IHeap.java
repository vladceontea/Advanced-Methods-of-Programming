package Model.adt;

import java.util.HashMap;
import java.util.Map;

public interface IHeap<V> {

    void add(V v);
    void update(int key, V v);
    V lookup(int id);
    boolean isDefined(int id);
    Map<Integer, V> getHeap();
    void setHeap(Map<Integer, V> map);
    int getFreeLocation();
    String toString();
}
