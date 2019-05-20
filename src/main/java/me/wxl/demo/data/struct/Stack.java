package me.wxl.demo.data.struct;

import java.util.EmptyStackException;

/**
 * @author wxl
 * @date 2019-02-10
 */
public class Stack<T> {
    private Object[] elementData;
    private int top = 0;

    public Stack() {
        this(10);
    }

    public Stack(int capacity) {
        this.elementData = new Object[capacity];
    }

    public void push(T element) {
        if (top >= elementData.length) {
            throw new StackOverflowError();
        }
        elementData[top++] = element;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (top == 0) {
            throw new EmptyStackException();
        }
        return (T) elementData[top - 1];
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (top == 0) {
            throw new EmptyStackException();
        }
        T ret = (T) elementData[--top];
        elementData[top] = null;
        return ret;
    }

    public boolean isEmpty() {
        return top == 0;
    }

    public int size() {
        return top;
    }

    public static void main(String[] args) {
        int size = 5;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            System.out.printf("%d, ", stack.pop());
        }
        System.out.println();

    }

}
