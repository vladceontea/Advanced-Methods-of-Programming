package Model.adt;
import java.util.HashMap;
import java.util.Map;

public class MyDict<T1,T2> implements IDict<T1,T2> {
    HashMap<T1, T2> dictionary;

    public MyDict() {
        this.dictionary = new HashMap<T1,T2>();
    }

    @Override
    public void add(T1 v1, T2 v2){
        dictionary.put(v1, v2);
    }

    @Override
    public void update(T1 v1, T2 v2) {
        dictionary.replace(v1, v2);
    }

    @Override
    public T2 lookup(T1 id) {
        return dictionary.get(id);
    }

    @Override
    public boolean isDefined(T1 id) {
        return dictionary.containsKey(id);
    }

    public void delete(T1 id){
        this.dictionary.remove(id);
    }

    public HashMap<T1, T2> getDict(){
        return this.dictionary;
    }

    public String toString(){
        return this.dictionary.toString();
    }

    @Override
    public IDict<T1, T2> deepCopy(){
        IDict<T1, T2>  newDictionary = new MyDict<T1, T2>() ;
        for(Map.Entry<T1, T2> entry: this.dictionary.entrySet()){
            newDictionary.add(entry.getKey(), entry.getValue());
        }
        return newDictionary;
    }

}
