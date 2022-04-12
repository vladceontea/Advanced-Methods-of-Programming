package Model.adt;

import java.util.Map;

public interface ICyclicBarrier<K, V> {

    void add(K key, V value);

    V get(K key);

    void update(K key, V value);

    boolean contains(K key);

    Map<K, V> getDict();

    Iterable<K> getAll();

}
