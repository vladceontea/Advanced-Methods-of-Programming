package Model.adt;

import java.util.HashMap;

public interface IDict<T1,T2>{

    void add(T1 v1, T2 v2);
    void update(T1 v1, T2 v2);
    T2 lookup(T1 id);
    boolean isDefined(T1 id);
    HashMap<T1,T2> getDict();
    void delete(T1 id);
    String toString();
    IDict<T1,T2> deepCopy();
}
