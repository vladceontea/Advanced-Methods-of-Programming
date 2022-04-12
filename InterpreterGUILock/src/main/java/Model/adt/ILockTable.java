package Model.adt;

import java.util.Collection;
import java.util.Map;

public interface ILockTable<K,V>
{
    void add(K key, V value);

    void update(K key, V value);

    boolean contains(K key);

    V get(K key);

    Iterable<K> getAll();

    Collection<V> getValues();

    Iterable<K> getKeys();

    ILockTable<K, V> deepcopy();

    Map<K, V> getContent();
}
