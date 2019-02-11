package me.wxl.demo.data.struct;

/**
 * @author wxl
 * @date 2019-02-11
 */
public class LinkQueue<T> {
    private Node head;
    private Node tail;

    public LinkQueue() {
        head = new Node();
        tail = head;
        tail.next = head;
    }

    public boolean isEmpty() {
        return head.next == tail;
    }

    public void enqueue(T data) {
        Node node = new Node();
        node.data = data;

        node.next = tail.next;
        tail.next = node;
        tail = node;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        Node node = head.next;
        head.next = node.next;
        return (T) node.data;
    }

    private static class Node {
        private Node next;
        private Object data;
    }

    public static void main(String[] args) {
        LinkQueue<Integer> queue = new LinkQueue<>();
        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }
        while (!queue.isEmpty()) {
            System.out.printf("%d, ", queue.dequeue());
        }
        System.out.println();
    }

}
