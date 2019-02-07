/**
 * N皇后问题：非递归算法
 */
public class Queen extends RecurrenceQueen {
    public void printSolutions() {
        // 1. 深度优先填充列
        fillPointsDeepFirst(columns, 0, 0);
        // 2. 回溯，并且：判断结果；开始下一个位置的查找（如果当前行还有下一列的话）
        while (stackTopRow >= 0) {
            // 如果行号为最后一行，则为解
            if (stackTopRow == queenNumber - 1) {
                solutionNumber += 1;
                RecurrenceQueen.printResult(columns);
            }
            // 找到本行的下一个合法的列进行搜索
            for (int col = columns[stackTopRow] + 1; col < queenNumber; col++) {
                if (fillPointsDeepFirst(columns, stackTopRow, col)) {
                    break;
                }
            }
            stackTopRow--;
        }
    }

    // 深度优先（本行找到合法的点，就找下一行），填充节点
    private boolean fillPointsDeepFirst(int[] columns, int row, int col) {
        columns[row] = col;
        if (!isValid(row, columns)) {
            return false;
        }
        row += 1;
        stackTopRow = row;

        while (row < queenNumber) {
            boolean find = false;
            for (int c = 0; c < queenNumber; c++) {
                columns[row] = c;
                if (isValid(row, columns)) {
                    find = true;
                    row += 1;
                    stackTopRow = row;
                    break;
                }
            }
            if (!find) {
                break;
            }
        }

        return true;
    }

    private int stackTopRow = 0;
    int[] columns;

    public Queen(int queenNumber) {
        // 一眼就能看出来，3以下无解
        assert queenNumber > 3;
        this.queenNumber = queenNumber;
        this.columns = new int[queenNumber];
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Queen queen = new Queen(8);
        queen.printSolutions();
        System.out.println("总解数：" + queen.solutionNumber);
        long end = System.currentTimeMillis();
        System.out.println("循环次数：" + queen.times + "， 耗时(ms)：" + (end - start));
    }
}
