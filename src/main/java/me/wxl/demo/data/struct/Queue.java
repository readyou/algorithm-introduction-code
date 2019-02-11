package me.wxl.demo.data.struct;

/**
 * @author wxl
 * @date 2019-02-11
 */
public class Queue<T> {
    private int head;
    private int tail;
    private int capacity;
    private Object[] data;

    public Queue() {
        this(10);
    }

    public Queue(int capacity) {
        this.capacity = capacity;
        data = new Object[capacity];
        head = 0;
        tail = 0;
    }

    public boolean isEmpty() {
        return head == tail;
    }

    public int size() {
        return tail - head;
    }

    public void enqueue(T value) {
        if (size() >= data.length) {
            throw new RuntimeException("队列已满");
        }
        data[tail % capacity] = value;
        tail++;
    }

    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空");
        }
        int i = head % capacity;
        T ret = (T) data[i];
        data[i] = null;
        head++;
        return ret;
    }

    public static void main(String[] args) {
        int size = 5;
        Queue<Integer> queue = new Queue<>(size);
        for (int i = size; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                queue.enqueue(j);
            }
            while (!queue.isEmpty()) {
                System.out.printf("%d, ", queue.dequeue());
            }
            System.out.println();
        }
    }
}
