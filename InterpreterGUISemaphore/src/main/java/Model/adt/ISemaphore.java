package Model.adt;

import java.util.List;
import java.util.Map;

public interface ISemaphore<K,V> {
    void add(K key, V value);

    V get(K key);

    void update(K key, V value);

    boolean contains(K key);

    Map<K, V> getDict();

    Iterable<K> getAll();
}
