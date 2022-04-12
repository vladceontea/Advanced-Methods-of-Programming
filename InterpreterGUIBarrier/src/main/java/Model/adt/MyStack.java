package Model.adt;
import Exceptions.EmptyStackException;
import Exceptions.MyException;

import java.util.Stack;

public class MyStack<T> implements IStack<T> {
    Stack<T> stack;

    public MyStack(){
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() throws MyException {
        if (stack.isEmpty()){
            throw new EmptyStackException("The stack is empty.");
        }
        return this.stack.pop();
    }

    public void push(T value){
        this.stack.push(value);
    }

    public boolean isEmpty(){
        return this.stack.isEmpty();
    }

    public Stack<T> getStack(){
        return this.stack;
    }

    public String toString(){
        return this.stack.toString();
    }


}
