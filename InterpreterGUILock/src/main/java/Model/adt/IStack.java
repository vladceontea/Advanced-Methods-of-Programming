package Model.adt;

import Exceptions.MyException;

import java.util.Stack;

public interface IStack<T> {

    T pop() throws MyException;
    void push(T v);
    boolean isEmpty();
    Stack<T> getStack();
    String toString();
}

