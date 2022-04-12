package Model.adt;

import Exceptions.MyException;

import java.util.Stack;

public interface IList<T> {
    void add(T v);
    T pop() throws MyException;
    String toString();
    boolean isEmpty();
    Stack<T> getList();
    T getFirst() throws MyException;
    void clear();
}
