package me.wxl.demo.data.struct;

/**
 * 单链表示例：head作为哨兵，不允许null值；演示链表反转；
 *
 * @author wxl
 * @date 2019-02-11
 */
public class SinglyLinkedList<T> {
    private Node head;

    public SinglyLinkedList() {
        head = new Node();
        head.next = head;
    }

    public boolean isEmpty() {
        return head.next == head;
    }

    public void insert(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data不能为空");
        }
        Node node = new Node();
        node.data = data;
        node.next = head.next;
        head.next = node;
    }

    public boolean delete(T data) {
        if (isEmpty() || data == null) {
            return false;
        }
        // 通过给head.data赋值，只做一次判断
        head.data = data;
        Node prev = head;
        Node node = prev.next;
        while (!data.equals(node.data)) {
            prev = node;
            node = prev.next;
        }
        if (node == head) {
            return false;
        }
        prev.next = node.next;
        return true;
    }

    public void reverse() {
        if (isEmpty()) {
            return;
        }
        Node newHead = new Node();
        newHead.next = newHead;

        Node node = head.next;
        while (node != head) {
            Node tmpNode = node;
            node = node.next;
            tmpNode.next = newHead.next;
            newHead.next = tmpNode;
        }
        head = newHead;
    }

    public void printAll() {
        Node node = head.next;
        while (node != head) {
            System.out.printf("%s\t", node.data);
            node = node.next;
        }
        System.out.println();
    }

    public static class Node {
        private Node next;
        private Object data;
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        for (int i = 0; i < 5; i++) {
            list.insert(i);
        }
        list.printAll();
        list.delete(0);
        list.printAll();
        list.reverse();
        list.printAll();
    }
}
