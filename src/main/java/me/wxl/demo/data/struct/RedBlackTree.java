package me.wxl.demo.data.struct;

import me.wxl.demo.utils.ArrayUtil;
import me.wxl.demo.utils.MockUtil;

/**
 * @author wxl
 * @date 2019-02-13
 */
public class RedBlackTree<T extends Comparable> {
    // 统计插入调整和删除调整时，各个分支出现的次数。
    public static int[] insertFixUpStats = new int[3];
    public static int[] deleteFixUpStats = new int[4];

    public static byte RED = 0;
    public static byte BLACK = 1;
    private Node nil;
    private Node root;

    public RedBlackTree() {
        nil = new Node();
        nil.color = BLACK;
        root = nil;
    }

    // 创建待插入的对象
    public Node buildNodeToBeInserted(T key) {
        Node node = new Node();
        node.key = key;
        node.color = RED;
        node.left = nil;
        node.right = nil;
        node.parent = nil;
        return node;
    }

    // 中序遍历
    private void inOrder(Node node) {
        if (node == nil) {
            return;
        }
        inOrder(node.left);
        System.out.printf("%s\n", node);
        inOrder(node.right);
    }

    public void inOrderPrint() {
        if (root == nil) {
            System.out.println("nil");
        } else {
            inOrder(root);
        }
        System.out.println();
    }

    public void layerPrint() {
        if (root == nil) {
            return;
        }
        LinkQueue<Node> queue = new LinkQueue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node node = queue.dequeue();
            System.out.printf("%s\n", node);
            if (node.left != nil) {
                queue.enqueue(node.left);
            }
            if (node.right != nil) {
                queue.enqueue(node.right);
            }
        }
    }

    public void insert(T key) {
        Node node = buildNodeToBeInserted(key);
        if (root == nil) {
            node.color = BLACK;
            root = node;
            return;
        }
        Node tmpNode = root;
        Node p = nil;
        while (tmpNode != nil) {
            p = tmpNode;
            if (key.compareTo(tmpNode.key) < 0) {
                tmpNode = tmpNode.left;
            } else {
                tmpNode = tmpNode.right;
            }
        }
        node.parent = p;
        if (key.compareTo(p.key) < 0) {
            p.left = node;
        } else {
            p.right = node;
        }

        insertFixup(node);
    }

    private void insertFixup(Node node) {
        while (node.parent != nil && node.parent.color == RED) {
            Node p = node.parent;
            // 因为父节点为红色，而根节点为黑色，所以父节点肯定不为根，故祖父节点存在，所以这里不需要判断为nil。
            Node pp = node.parent.parent;
            if (pp.left == p) {
                Node pb = pp.right;
                if (pb.color == RED) {
                    insertFixUpStats[0]++;
                    p.color = BLACK;
                    pb.color = BLACK;
                    pp.color = RED;
                    node = pp;
                    continue;
                }

                if (node != p.left) {
                    insertFixUpStats[1]++;
                    rotateLeft(p);
                    node = p;
                    p = node.parent;
                }
                insertFixUpStats[2]++;
                p.color = BLACK;
                pp.color = RED;
                rotateRight(pp);
            } else {
                Node pb = pp.left;
                if (pb.color == RED) {
                    insertFixUpStats[0]++;
                    p.color = BLACK;
                    pb.color = BLACK;
                    pp.color = RED;
                    node = pp;
                    continue;
                }

                if (node != p.right) {
                    insertFixUpStats[1]++;
                    rotateRight(p);
                    node = p;
                    p = node.parent;
                }
                insertFixUpStats[2]++;
                p.color = BLACK;
                pp.color = RED;
                rotateLeft(pp);
            }
        }
        root.color = BLACK;
    }

    private void rotateLeft(Node x) {
        Node y = x.right;

        x.right = y.left;
        if (y.left != nil) {
            y.left.parent = x;
        }

        Node parent = x.parent;
        y.parent = parent;

        if (parent == nil) {
            root = y;
        } else if (parent.left == x) {
            parent.left = y;
        } else {
            parent.right = y;
        }

        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;

        x.left = y.right;
        if (y.right != nil) {
            y.right.parent = x;
        }

        Node parent = x.parent;
        y.parent = parent;

        if (parent == nil) {
            root = y;
        } else if (parent.left == x) {
            parent.left = y;
        } else {
            parent.right = y;
        }

        y.right = x;
        x.parent = y;
    }

    public Node search(T data) {
        if (root == nil) {
            return null;
        }
        Node node = root;
        while (node != nil) {
            int compare = data.compareTo((T) node.key);
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
        Node replacement = nil;
        byte replacedColor = node.color;

        if (node.left == nil) {
            replacement = node.right;
            replace(node, replacement);
        } else if (node.right == nil) {
            replacement = node.left;
            replace(node, replacement);
        } else {
            Node successor = min(node.right);
            replacement = successor.right;
            // replacement不是已经是successor的孩子了吗，这里是不是多余了？
            // 并不是的，因为success.right可能为nil，而nil没有parent或parent指向不正确，需要重新给nil指定parent，否则进行旋转的时候会出错。
            replacement.parent = successor;

            if (successor.parent != node) {
                replace(successor, replacement);
                node.right.parent = successor;
                successor.right = node.right;
            }

            replace(node, successor);
            node.left.parent = successor;
            successor.left = node.left;

            // 将node的颜色给successor，所以被替换的颜色为successor原来的颜色。
            replacedColor = successor.color;
            successor.color = node.color;
        }

        if (replacedColor == BLACK) {
            deleteFixUp(replacement);
        }

        return true;
    }

    private Node min(Node node) {
        while (node.left != nil) {
            node = node.left;
        }
        return node;
    }

    private Node max(Node node) {
        while (node.right != nil) {
            node = node.right;
        }
        return node;
    }

    private void replace(Node oldNode, Node newNode) {
        Node parent = oldNode.parent;
        if (parent == nil) {
            root = newNode;
        } else {
            if (parent.left == oldNode) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }
        }
        newNode.parent = oldNode.parent;
    }

    private void deleteFixUp(Node node) {
        while (node != root && node.color == BLACK) {
            Node parent = node.parent;
            if (node == parent.left) {
                Node brother = parent.right;
                if (brother.color == RED) {
                    deleteFixUpStats[0]++;
                    brother.color = BLACK;
                    parent.color = RED;
                    rotateLeft(parent);
                    brother = parent.right;
                }

                // 因为有哨兵nil，所以这里不需要判断brother.left是否存在。
                if (brother.left.color == BLACK && brother.right.color == BLACK) {
                    deleteFixUpStats[1]++;
                    brother.color = RED;
                    node = parent;
                } else {
                    if (brother.left.color == RED) {
                        deleteFixUpStats[2]++;
                        brother.left.color = BLACK;
                        brother.color = RED;
                        rotateRight(brother);
                        brother = parent.right;
                    }

                    deleteFixUpStats[3]++;
                    // 旋转与变色是相互独立的，所以顺序不是固定的，随意就行。不妨把下面这句rotateLeft(parent)换个地方试试。
//                    rotateLeft(parent);
                    brother.color = parent.color;
                    parent.color = BLACK;
                    brother.right.color = BLACK;
                    rotateLeft(parent);
                    node = root;
                }
            } else {
                Node brother = parent.left;
                if (brother.color == RED) {
                    deleteFixUpStats[0]++;
                    brother.color = BLACK;
                    parent.color = RED;
                    rotateRight(parent);
                    brother = parent.left;
                }

                if (brother.left.color == BLACK && brother.right.color == BLACK) {
                    deleteFixUpStats[1]++;
                    brother.color = RED;
                    node = parent;
                } else {
                    if (brother.right.color == RED) {
                        deleteFixUpStats[2]++;
                        brother.right.color = BLACK;
                        brother.color = RED;
                        rotateLeft(brother);
                        brother = parent.left;
                    }

                    deleteFixUpStats[3]++;
                    brother.color = parent.color;
                    parent.color = BLACK;
                    brother.left.color = BLACK;
                    rotateRight(parent);
                    node = root;
                }
            }
        }
        node.color = BLACK;
    }

    // 检验红黑树的性质：1. 根为黑色；2. 没有两个连续红色节点；3. 从根到任何一个叶子节点的黑节点数目（黑高）相等。
    // 如果任意一条性质不满足，中序遍历输出节点信息（便于定位问题）并抛异常，否则返回从根到当前节点的黑高，用于递归处理。
    private int validateTreeProperties(Node node, int bh) {
        if (node == nil) {
            return bh;
        }
        if (root.color == RED) {
            inOrderPrint();
            throw new RuntimeException("根节点为红色");
        }
        if (node.color == RED) {
            if ((node.left.color == RED || node.right.color == RED || node.parent.color == RED)) {
                inOrderPrint();
                throw new RuntimeException("连续两个节点为红色");
            }
        } else {
            bh += 1;
        }
        // 左子树的黑高
        int bhLeft = 0;
        // 右子树的黑高
        int bhRight = 0;
        if (node.left != nil) {
            bhLeft = validateTreeProperties(node.left, bh);
        }
        if (node.right != nil) {
            bhRight = validateTreeProperties(node.right, bh);
        }
        // 如果左右子树都存在且黑高不相等
        if (bhLeft > 0 && bhRight > 0 && bhLeft != bhRight) {
            inOrderPrint();
            throw new RuntimeException("左右子树黑色节点数目不一致");
        }

        int result = bh;
        if (bhLeft > result) {
            result = bhLeft;
        }
        if (bhRight > result) {
            result = bhRight;
        }
        return result;
    }

    private void validateTreeProperties() {
        validateTreeProperties(root, 0);
    }

    private static class Node {
        private Node parent;
        private Node left;
        private Node right;
        private Object key;
        private byte color;

        @Override
        public String toString() {
            if (key == null) {
                return "nil";
            }
            return String.format("(key: %s, color: %s)", key.toString(), color == RED ? "RED" : "BLACK");
        }
    }

    public static void main(String[] args) {
//        int[] a = {11, 2, 14, 1, 7, 15, 5, 8, 4}; // 算法导论书上的数据示例
        // 构造大批量的随机树，然后再随机删除节点，用来测试代码的正确性。
        int lenMin = 7;
        int lenMax = 8;
        int times = 1;
//        int lenMin = 1;
//        int lenMax = 30;
//        int times = 20;
        for (int i = lenMin; i < lenMax; i++) {
            for (int j = 0; j < times; j++) {
                Integer[] elements = MockUtil.getRandomIntegerArray(i, false);

                RedBlackTree<Integer> tree = new RedBlackTree<>();
                for (int element : elements) {
                    tree.insert(element);
                    System.out.printf("\n插入：%d ------------------------------\n", element);
                    tree.inOrderPrint();
                    tree.layerPrint();
                }

                MockUtil.shuffle(elements, false);
//            elements = new Integer[]{10, 3, 5, 1, 11, 9, 12};
                for (int element : elements) {
                    tree.delete(element);
                    System.out.printf("\n删除：%d ------------------------------\n", element);
                    tree.inOrderPrint();
                    tree.layerPrint();
                    tree.validateTreeProperties();
                }
            }
        }
        ArrayUtil.printIntArray(insertFixUpStats, "insertFixUpStats");
        ArrayUtil.printIntArray(deleteFixUpStats, "deleteFixUpStats");
    }
}
