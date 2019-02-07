import java.util.*;

public class Main {
    public static void main(String[] args) {
        int xLen = 7;
        int yLen = 7;
        boolean[][] area = getInitArray(xLen, yLen);
        area[0][6] = true;

        area[1][4] = true;

        area[6][6] = true;

        area[3][5] = true;
        area[4][6] = true;

        area[4][3] = true;
        area[5][4] = true;
        area[6][3] = true;

        area[1][2] = true;
        area[2][0] = true;
        area[2][1] = true;
        area[2][2] = true;
        area[3][0] = true;
        area[4][0] = true;
        area[4][1] = true;
        area[5][0] = true;
        area[5][1] = true;
        area[6][1] = true;

        List<Set<Point>> result = iceNumber(area);
        System.out.println("共有区块数：" + result.size());
        System.out.println("区域分别是：");
        for (Set<Point> item : result) {
            System.out.println(item);
        }
    }

    private static boolean[][] getInitArray(int xLen, int yLen) {
        boolean[][] area = new boolean[xLen][yLen];
        for (int i = 0; i < xLen; i++) {
            for (int j = 0; j < yLen; j++) {
                area[i][j] = false;
            }
        }
        return area;
    }

    private static List<Set<Point>> iceNumber(boolean[][] area) {
        if (area == null || area.length == 0 || area[0] == null || area[0].length == 0) {
            return Collections.emptyList();
        }

        int xLen = area.length;
        int yLen = area[0].length;
        boolean[][] mark = getInitArray(xLen, yLen);

        List<Set<Point>> result = new ArrayList<Set<Point>>();
        for (int i = 0; i < xLen; i++) {
            for (int j = 0; j < yLen; j++) {
                if (!mark[i][j] && area[i][j]) {
                    result.add(getCloseArea(area, i, j, xLen, yLen, mark));
                }
            }
        }
        return result;
    }

    /**
     * 求相邻区域（这里假定调用方已确定存在冰块区域，此方法用来求出相连的最大区域）。
     * <p>
     * 从节点相邻的8个方向深度搜索，然后回溯。
     */
    private static Set<Point> getCloseArea(boolean[][] area, int x, int y, int xLen, int yLen, boolean[][] mark) {
        Set<Point> result = new HashSet<Point>();

        Stack<Point> stack = new Stack<Point>();
        stack.push(new Point(x, y));
        while (!stack.empty()) {
            // 推入到栈中的都是符合要求的点，pop之后就把它存到结果中去。因为从8个方向回溯，会出现重复（最多8次），所以用Set去重。
            Point point = stack.pop();
            result.add(point);
            mark[point.x][point.y] = true;

            if (!point.topMarked) {
                point.topMarked = true;
                stack.push(point);

                int y1 = point.y;
                int x1 = point.x - 1;
                // 这里的!mark[x1][y1]是关键，因为我们的结果是包含整个区域（从任意起点都一样），所以一旦mark了，
                // 就表示它已经属于之前探索过的区域了，就没必要继续了。
                while (x1 >= 0 && !mark[x1][y1] && area[x1][y1]) {
                    point = new Point(x1, y1);
                    // 因为是往top方向深度优先搜索，所以对于当前搜索到的节点，它的上一个（即bottom）肯定已经探索过了，并是符合条件的。
                    point.bottomMarked = true;
                    stack.push(point);
                    // 往某个方向深度搜索
                    x1 = point.x - 1;
                }
                continue;
            }

            if (!point.topLeftMarked) {
                point.topLeftMarked = true;
                stack.push(point);

                int y1 = point.y - 1;
                int x1 = point.x - 1;
                while (x1 >= 0 && y1 >= 0 && !mark[x1][y1] && area[x1][y1]) {
                    point = new Point(x1, y1);
                    point.bottomRightMarked = true;
                    stack.push(point);
                    y1 = point.y - 1;
                    x1 = point.x - 1;
                }
                continue;
            }

            if (!point.leftMarked) {
                point.leftMarked = true;
                stack.push(point);

                int x1 = point.x;
                int y1 = point.y - 1;
                while (y1 >= 0 && !mark[x1][y1] && area[x1][y1]) {
                    point = new Point(x1, y1);
                    point.rightMarked = true;
                    stack.push(point);
                    y1 = point.y - 1;
                }
                continue;
            }

            if (!point.bottomLeftMarked) {
                point.bottomLeftMarked = true;
                stack.push(point);

                int y1 = point.y - 1;
                int x1 = point.x + 1;
                while (x1 < xLen && y1 >= 0 && !mark[x1][y1] && area[x1][y1]) {
                    point = new Point(x1, y1);
                    point.topRightMarked = true;
                    stack.push(point);
                    y1 = point.y - 1;
                    x1 = point.x + 1;
                }
                continue;
            }

            if (!point.bottomMarked) {
                point.bottomMarked = true;
                stack.push(point);

                int y1 = point.y;
                int x1 = point.x + 1;
                while (x1 < xLen && !mark[x1][y1] && area[x1][y1]) {
                    point = new Point(x1, y1);
                    point.topMarked = true;
                    stack.push(point);
                    x1 = point.x + 1;
                }
                continue;
            }

            if (!point.bottomRightMarked) {
                point.bottomRightMarked = true;
                stack.push(point);

                int y1 = point.y + 1;
                int x1 = point.x + 1;
                while (x1 < xLen && y1 < yLen && !mark[x1][y1] && area[x1][y1]) {
                    point = new Point(x1, y1);
                    point.topLeftMarked = true;
                    stack.push(point);
                    x1 = point.x + 1;
                    y1 = point.y + 1;
                }
                continue;
            }

            if (!point.rightMarked) {
                point.rightMarked = true;
                stack.push(point);

                int x1 = point.x;
                int y1 = point.y + 1;
                while (y1 < yLen && !mark[x1][y1] && area[x1][y1]) {
                    point = new Point(x1, y1);
                    point.leftMarked = true;
                    stack.push(point);
                    y1 = point.y + 1;
                }
            }

            if (!point.topRightMarked) {
                point.topRightMarked = true;
                stack.push(point);

                int y1 = point.y + 1;
                int x1 = point.x - 1;
                while (x1 >= 0 && y1 < yLen && !mark[x1][y1] && area[x1][y1]) {
                    point = new Point(x1, y1);
                    point.bottomLeftMarked = true;
                    stack.push(point);
                    y1 = point.y + 1;
                    x1 = point.x - 1;
                }
            }

        }

        return result;
    }

    private static class Point {
        int x;
        int y;
        boolean topMarked;
        boolean topLeftMarked;
        boolean leftMarked;
        boolean bottomLeftMarked;
        boolean bottomMarked;
        boolean bottomRightMarked;
        boolean rightMarked;
        boolean topRightMarked;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(x: " + x + ", y: " + y + ")";
        }

        @Override
        public int hashCode() {
            return (x + "-" + y).hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Point)) {
                return false;
            }
            Point other = (Point) obj;
            return x == other.x && y == other.y;
        }
    }
}