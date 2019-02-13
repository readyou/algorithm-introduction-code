package me.wxl.demo.data.struct;

/**
 * @author wxl
 * @date 2019-02-11
 */
public class BinarySearchTree<T extends Comparable> {
    private Node root;

    // 中序遍历
    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.printf("%s\t", node.data);
        inOrder(node.right);
    }

    public void inOrderPrint() {
        if (root == null) {
            System.out.println("null");
        } else {
            inOrder(root);
        }
        System.out.println();
    }

    public Node min(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("node不能为空");
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public Node max(Node node) {
        if (node == null) {
            throw new IllegalArgumentException("node不能为空");
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public Node successor(Node node) {
        if (node.right != null) {
            return max(node.right);
        }
        Node p = node.parent;
        while (p != null && p.right == node) {
            node = p;
            p = p.parent;
        }
        return p;
    }

    public Node predecessor(Node node) {
        if (node.left != null) {
            return min(node.left);
        }
        Node p = node.parent;
        while (p != null && p.left == node) {
            node = p;
            p = p.parent;
        }
        return p;
    }

    public void insert(T data) {
        Node node = new Node();
        node.data = data;
        if (root == null) {
            root = node;
            return;
        }
        Node child = root;
        Node p = null;
        while (child != null) {
            p = child;
            if (data.compareTo((T) child.data) < 0) {
                child = child.left;
            } else {
                child = child.right;
            }
        }
        node.parent = p;
        if (data.compareTo((T) p.data) < 0) {
            p.left = node;
        } else {
            p.right = node;
        }
    }

    public Node search(T data) {
        if (root == null) {
            return null;
        }
        Node node = root;
        while (node != null) {
            int compare = data.compareTo((T) node.data);
            if (compare == 0) {
                return node;
            }
            if (compare < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    public boolean delete(T data) {
        Node node = search(data);
        if (node == null) {
            return false;
        }
        if (node.left == null) {
            replace(node, node.right);
            return true;
        }
        if (node.right == null) {
            replace(node, node.left);
            return true;
        }
        Node successor = min(node.right);
        if (node.right != successor) {
            replace(successor, successor.right);
            node.right.parent = successor;
            successor.right = node.right;
        }

        replace(node, successor);
        successor.left = node.left;
        node.left.parent = successor;
        return true;
    }

    private void replace(Node oldNode, Node newNode) {
        Node parent = oldNode.parent;
        if (parent == null) {
            root = newNode;
        } else {
            if (parent.left == oldNode) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
        if (newNode != null) {
            newNode.parent = oldNode.parent;
        }
    }

    public void buildTree(T[] elments) {
        if (elments == null || elments.length == 0) {
            throw new IllegalArgumentException("elemtns不能为空");
        }
        for (T element : elments) {
            insert(element);
        }
    }

    private static class Node {
        private Node left;
        private Node right;
        private Node parent;
        private Object data;

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public static void main(String[] args) {
        Integer[] a = {6, 4, 9, 2, 5, 8, 1, 3, 7, 12, 10, 11, 13};
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        tree.buildTree(a);
        tree.inOrderPrint();

        Node node = tree.search(4);
        if (node == null) {
            System.out.println("查找失败");
        } else {
            System.out.printf("data: %s, parent: %s, left: %s, right: %s\n", node, node.parent, node.left, node.right);
        }
        int[] tobeDeleted = {4, 9, 6, 12, 8, 7, 5, 3, 2, 1, 10, 11, 13};
        for (int i : tobeDeleted) {
            tree.delete(i);
            tree.inOrderPrint();
        }

        System.out.println();
    }
}
