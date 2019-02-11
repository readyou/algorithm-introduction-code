package me.wxl.demo.data.struct;

/**
 * @author wxl
 * @date 2019-02-11
 */
public class BinaryTree<T> {
    private Node root;

    // 先序遍历
    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.printf("%s\t", node.data);
        preOrder(node.left);
        preOrder(node.right);
    }

    private void preOrderNonRecursive(Node root) {
        if (root == null) {
            return;
        }
        LinkStack<Node> stack = new LinkStack<>();
        preOrderNonRecursiveHelper(stack, root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            Node right = node.right;
            if (right != null) {
                preOrderNonRecursiveHelper(stack, right);
            }
        }
    }

    private void preOrderNonRecursiveHelper(LinkStack<Node> stack, Node node) {
        System.out.printf("%s\t", node.data);
        stack.push(node);

        while ((node = node.left) != null) {
            System.out.printf("%s\t", node.data);
            stack.push(node);
        }
    }

    // 中序遍历
    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.printf("%s\t", node.data);
        inOrder(node.right);
    }

    private void inOrderNonRecursive(Node root) {
        if (root == null) {
            return;
        }
        LinkStack<Node> stack = new LinkStack<>();
        inOrderNonRecursiveHelper(stack, root);

        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.printf("%s\t", node.data);

            Node right = node.right;
            if (right != null) {
                inOrderNonRecursiveHelper(stack, right);
            }
        }
    }

    private void inOrderNonRecursiveHelper(LinkStack<Node> stack, Node node) {
        stack.push(node);
        while ((node = node.left) != null) {
            stack.push(node);
        }
    }

    // 后序遍历
    private void postOrder(Node node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.printf("%s\t", node.data);
    }

    private void postOrderNonRecursive(Node root) {
        if (root == null) {
            return;
        }
        LinkStack<Node> stack = new LinkStack<>();
        inOrderNonRecursiveHelper(stack, root);

        // 后序遍历稍微复杂一点，需要判断是不从右取子树回退的，如果不是的话则重新入栈然后继续处理右子树。
        Node lastRight = null;
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            // 如果右孩子不存在，则不需要进一步处理了，直接输出本节点，然后继续下一次循环。
            if (node.right == null) {
                System.out.printf("%s\t", node.data);
                continue;
            }

            // 如果是从右子树回退的，则输出本节点，并重置lastRight。
            if (node.right == lastRight) {
                System.out.printf("%s\t", node.data);
                lastRight = node;
            } else {
                // 否则，本节点重新入栈，标记lastRight为右孩子并处理右孩子。
                stack.push(node);

                Node right = node.right;
                lastRight = right;
                inOrderNonRecursiveHelper(stack, right);
            }
        }
    }

    // 层序遍历（广度优先遍历）
    public void layerPrint() {
        if (root == null) {
            return;
        }
        LinkQueue<Node> queue = new LinkQueue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node node = queue.dequeue();
            System.out.printf("%s\t", node.data);
            if (node.left != null) {
                queue.enqueue(node.left);
            }
            if (node.right != null) {
                queue.enqueue(node.right);
            }
        }
    }

    public void preOrderPrint() {
//        preOrder(root);
        preOrderNonRecursive(root);
        System.out.println();
    }

    public void inOrderPrint() {
//        inOrder(root);
        inOrderNonRecursive(root);
        System.out.println();
    }

    public void postOrderPrint() {
//        postOrder(root);
        postOrderNonRecursive(root);
        System.out.println();
    }

    public void buildTree(T[] elments) {
        if (elments == null || elments.length == 0) {
            throw new IllegalArgumentException("elemtns不能为空");
        }
        LinkQueue<Node> nodes = new LinkQueue<>();
        int i = 0;
        root = new Node();
        root.data = elments[i++];
        nodes.enqueue(root);

        while (!nodes.isEmpty() && i < elments.length) {
            Node node = nodes.dequeue();
            Node left = new Node();
            left.data = elments[i++];
            left.parent = node;
            node.left = left;
            nodes.enqueue(left);

            if (i >= elments.length) {
                return;
            }

            Node right = new Node();
            right.data = elments[i++];
            right.parent = node;
            node.right = right;
            nodes.enqueue(right);
        }
    }

    private static class Node {
        private Node left;
        private Node right;
        private Node parent;
        private Object data;
    }

    public static void main(String[] args) {
        Integer[] a = {1, 2, 3, 4, 5, 6, 7};
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.buildTree(a);
        tree.preOrderPrint();
        tree.inOrderPrint();
        tree.postOrderPrint();
        tree.layerPrint();
        System.out.println();
    }
}
