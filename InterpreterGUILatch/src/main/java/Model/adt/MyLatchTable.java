package Model.adt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyLatchTable<K,V> implements ILatchTable<K,V> {
    private HashMap<K, V> _map;

    public MyLatchTable() {
        _map=new HashMap<>();
    }

    @Override
    public void put(K key, V value) {
        this._map.put(key, value);
    }

    @Override
    public V get(K key) {
        return this._map.get(key);
    }

    @Override
    public Collection<V> values() {
        return this._map.values();
    }

    @Override
    public Collection<K> keys() {
        return this._map.keySet();
    }

    @Override
    public void remove(K fd) {
        _map.remove(fd);
    }

    @Override
    public ILatchTable<K,V> deepcopy() {
        ILatchTable<K, V> dict = new MyLatchTable<K, V>();
        for(K key : _map.keySet())
            dict.put(key, _map.get(key));
        return dict;
    }

    @Override
    public boolean contains(K key)
    {
        return _map.containsKey(key);
    }

    @Override
    public Map<K, V> toMap() {
        return _map;
    }

    @Override
    public HashMap<K, V> getTable(){
        return this._map;
    }

    @Override
    public String toString() {
        return this._map.toString();
    }
}
