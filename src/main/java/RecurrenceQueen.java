/**
 * N皇后问题，递归求解
 */
public class RecurrenceQueen {
    public void fillColumn(int row) {
        if (row >= queenNumber) {
            solutionNumber++;
//            printResult(columns);
        } else {
            for (int col = 0; col < queenNumber; col++) {
                columns[row] = col;
                if (isValid(row, columns)) {
                    fillColumn(row + 1);
                }
            }
        }
    }

    // 判断第row行的列是否合格
    protected boolean isValid(int row, int[] columns) {
        for (int rowIndex = 0; rowIndex < row; rowIndex++) {
            times += 1;
            // 之前没有出现过相同的列，且没有在一条斜线上的列
            if (columns[row] == columns[rowIndex] || Math.abs(row - rowIndex) == Math.abs(columns[row] - columns[rowIndex])) {
                return false;
            }
        }
        return true;
    }


    int queenNumber;
    int solutionNumber = 0;
    int[] columns;
    long times;

    private void getSolutions(int queenNumber) {
        long start = System.currentTimeMillis();
        this.queenNumber = queenNumber;
        this.columns = new int[queenNumber];
        fillColumn(0);
        long end = System.currentTimeMillis();
        System.out.println("解的个数为：" + solutionNumber);
        System.out.println("循环次数：" + times + "， 耗时(ms)：" + (end - start));
        System.out.println(8 * 7 * 6 * 5 * 4 * 3 * 2);
    }

    public static void printResult(int[] columns) {
        for (int i : columns) {
            for (int j = 0; j < columns.length; j++) {
                System.out.print(j == i ? "x" : "\t");
            }
            System.out.println();
        }
        System.out.println("----------------------------------");
    }

    public static void main(String[] args) {
        RecurrenceQueen n = new RecurrenceQueen();
        n.getSolutions(9);
    }
}
