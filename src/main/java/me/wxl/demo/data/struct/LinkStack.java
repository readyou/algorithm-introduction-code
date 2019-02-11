package me.wxl.demo.data.struct;

import java.util.EmptyStackException;

/**
 * @author wxl
 * @date 2019-02-11
 */
public class LinkStack<T> {
    private Node top;

    public LinkStack() {
        top = new Node();
    }

    public boolean isEmpty() {
        return top.next == null;
    }

    public void push(T data) {
        Node node = new Node();
        node.data = data;
        node.next = top.next;
        top.next = node;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return (T) top.next.data;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T ret = (T) top.next.data;
        top.next = top.next.next;
        return ret;
    }

    private static class Node {
        private Object data;
        private Node next;
    }

    public static void main(String[] args) {
        int size = 5;
        LinkStack<Integer> stack = new LinkStack<>();
        for (int i = 0; i < size; i++) {
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            System.out.printf("%d, ", stack.pop());
        }
        System.out.println();
    }
}
