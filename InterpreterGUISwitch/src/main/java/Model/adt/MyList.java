package Model.adt;

import Exceptions.EmptyStackException;
import Exceptions.MyException;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MyList<T> implements IList<T> {
    Stack<T> list;

    public MyList(){
        this.list = new Stack<T>();
    }

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public T pop() throws MyException {
        if (list.isEmpty()){
            throw new EmptyStackException("The stack is empty.");
        }
        return list.pop();
    }

    @Override
    public boolean isEmpty(){
        return this.list.isEmpty();
    }

    @Override
    public void clear(){
        this.list.clear();
    }

    @Override
    public Stack<T> getList(){
        return this.list;
    }

    @Override
    public T getFirst() throws MyException{
        if (list.isEmpty()){
            throw new EmptyStackException("The stack is empty.");
        }
        return list.get(0);
    }

    public String toString(){
        return this.list.toString();
    }

}
