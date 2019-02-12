package me.wxl.demo.data.struct;

/**
 * 用左孩子右兄弟法表示多孩子树。
 *
 * @author wxl
 * @date 2019-02-12
 */
public class MultiChildTree<T> {
    private Node root;

    private void preOrderPrint(Node node) {
        if (node == null) {
            return;
        }
        System.out.printf("%s\t", node.data);

        Node lChild = node.lChild;
        if (lChild == null) {
            return;
        }
        preOrderPrint(lChild);

        Node rSibling = lChild.rSibling;
        while (rSibling != null) {
            preOrderPrint(rSibling);
            rSibling = rSibling.rSibling;
        }
    }

    private void inOrderPrint(Node node) {
        if (node == null) {
            return;
        }

        Node lChild = node.lChild;
        if (lChild != null) {
            inOrderPrint(lChild);
            System.out.printf("%s\t", node.data);

            Node rSibling = lChild.rSibling;
            while (rSibling != null) {
                inOrderPrint(rSibling);
                rSibling = rSibling.rSibling;
            }
        } else {
            System.out.printf("%s\t", node.data);
        }
    }

    private void postOrderPrint(Node node) {
        if (node == null) {
            return;
        }

        Node lChild = node.lChild;
        if (lChild != null) {
            postOrderPrint(lChild);

            Node rSibling = lChild.rSibling;
            while (rSibling != null) {
                postOrderPrint(rSibling);
                rSibling = rSibling.rSibling;
            }
            System.out.printf("%s\t", node.data);
        } else {
            System.out.printf("%s\t", node.data);
        }
    }

    public void preOrderPrintTree() {
        preOrderPrint(root);
        System.out.println();
    }

    public void inOrderPrintTree() {
        inOrderPrint(root);
        System.out.println();
    }

    public void postOrderPrintTree() {
        postOrderPrint(root);
        System.out.println();
    }

    public void layerPrint() {
        if (root == null) {
            return;
        }
        LinkQueue<Node> queue = new LinkQueue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node node = queue.dequeue();
            System.out.printf("%s\t", node.data);
            node = node.lChild;
            while (node != null) {
                queue.enqueue(node);
                node = node.rSibling;
            }
        }
        System.out.println();
    }

    /**
     * 构建树
     *
     * @param elements 数组
     * @param n        一个节点的孩子个数
     */
    public void buildTree(T[] elements, int n) {
        if (elements == null || elements.length == 0) {
            throw new IllegalArgumentException("elements不能为空");
        }
        if (n < 2) {
            throw new IllegalArgumentException("n不能小于2");
        }
        LinkQueue<Node> queue = new LinkQueue<>();
        int i = 0;
        Node root = new Node();
        root.data = elements[i++];
        this.root = root;
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            Node parent = queue.dequeue();
            Node lChild = null;
            for (int j = 0; j < n && i < elements.length; j++) {
                if (lChild == null) {
                    lChild = new Node();
                    lChild.data = elements[i++];
                    lChild.parent = parent;
                    parent.lChild = lChild;
                    queue.enqueue(lChild);
                } else {
                    Node rSibling = new Node();
                    rSibling.data = elements[i++];
                    rSibling.parent = parent;
                    queue.enqueue(rSibling);

                    lChild.rSibling = rSibling;
                    lChild = rSibling;
                }
            }
        }
    }

    private static class Node {
        private Object data;
        private Node parent;
        private Node lChild;
        private Node rSibling;
    }

    public static void main(String[] args) {
        int len = 20;
        Integer[] data = new Integer[len];
        for (int i = 0; i < len; i++) {
            data[i] = i + 1;
        }
        MultiChildTree<Integer> tree = new MultiChildTree<>();
        tree.buildTree(data, 4);
        tree.layerPrint();
        tree.preOrderPrintTree();
        tree.inOrderPrintTree();
        tree.postOrderPrintTree();
    }
}
