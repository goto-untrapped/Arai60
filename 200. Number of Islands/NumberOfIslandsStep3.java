public class NumberOfIslandsStep3 {
    // 14m30s / 8m10s / 7m30s
    static final char WATER = '0';
    // static final char LAND = '1';
    public int numIslands(char[][] grid) {
        int numOfIslands = 0;
        int numRows = grid.length;
        int numCols = grid[0].length;
        boolean[][] isSeens = new boolean[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (grid[row][col] == WATER) {
                    continue;
                }
                if (isSeens[row][col]) {
                    continue;
                }
                numOfIslands++;
                traverseConnectedLands(grid, isSeens, row, col);
            }
        }
        return numOfIslands;
    }

    private void traverseConnectedLands(char[][] grid, boolean[][] isSeens, int row, int col) {
        Stack<int[]> connected = new Stack<>();
        connected.push(new int[] {row, col});
        while (!connected.isEmpty()) {
            int[] position = connected.pop();
            int rowSearch = position[0];
            int colSearch = position[1];
            if (isOutOfGrid(grid, rowSearch, colSearch)) {
                continue;
            }
            if (grid[rowSearch][colSearch] == WATER) {
                continue;
            }
            if (isSeens[rowSearch][colSearch]) {
                continue;
            }
            isSeens[rowSearch][colSearch] = true;
            traverseConnectedLands(grid, isSeens, rowSearch + 1, colSearch);
            traverseConnectedLands(grid, isSeens, rowSearch - 1, colSearch);
            traverseConnectedLands(grid, isSeens, rowSearch, colSearch + 1);
            traverseConnectedLands(grid, isSeens, rowSearch, colSearch - 1);
        }
    }

    private boolean isOutOfGrid(char[][] grid, int row, int col) {
        return row < 0 || grid.length <= row || col < 0 || grid[0].length <= col;
    }
    
    /*
     * 思ったこと
     * ・numOfIslands という名前について、関数名が numIslands だったので苦し紛れだけど、
     * numRows などと合わせて省略形で統一したかったため、この名前にした。
     * ・使わない定数はコメントアウトという形にすれば、コメントアウト行が数行であれば、
     * 全体像が見えるし、使わないことも分かっていいと思ったので、使った。
     * ・再帰で深さ探索を書くと関数が勝手に深掘りしてくれて書くのが楽だったけど、Stackを使うと
     * 流れを定義しないから考えることが増える感覚がある。慣れの問題かな。探索順番もちょっと違う。
     * 再帰のリミット考えなくてもいいから採用した。でも業務で再帰で書く方がいい時もあるのだろうか。
     */
}
