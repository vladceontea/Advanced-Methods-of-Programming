package Model.adt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyLockTable<K,V> implements ILockTable<K,V>{

    private Map<K,V> dict;

    public Map<K, V> getContent() { return dict; }

    public MyLockTable()
    {
        dict = new HashMap<K, V>();
    }

    public void add(K key, V value)
    {
        dict.put(key, value);
    }

    public void update(K key, V value)
    {
        dict.put(key, value);
    }

    public V get(K key)
    {
        return dict.get(key);
    }

    public boolean contains(K key)
    {
        return dict.containsKey(key);
    }

    public Iterable<K> getAll()
    {
        return dict.keySet();
    }
    private MyLockTable(Map<K,V> d){
        dict = d;
    }

    @Override
    public Collection<V> getValues()
    {
        return dict.values();
    }

    @Override
    public Iterable<K> getKeys()
    {
        return dict.keySet();
    }

    @Override
    public ILockTable<K, V> deepcopy()
    {
        return new MyLockTable<K,V>(new HashMap<K,V>(dict));
    }

    public String toString()
    {
        return this.dict.toString();
    }
}
