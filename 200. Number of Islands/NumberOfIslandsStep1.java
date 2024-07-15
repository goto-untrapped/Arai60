public class NumberOfIslandsStep1 {
    // 30min
    static char LAND = '1';
    static char WATER = '0';
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
                traverseAdjacentLands(grid, isSeens, row, col);
            }
        }
        return numOfIslands;
    }

    private void traverseAdjacentLands(char[][] grid, boolean[][] isSeens, int row, int col) {
        Queue<int[]> adjacentLands = new LinkedList<>();
        adjacentLands.offer(new int[] {row, col});
        while (!adjacentLands.isEmpty()) {
            int[] position = adjacentLands.poll();
            int rowSearch = position[0];
            int colSearch = position[1];

            if (isOutOfGrid(grid, rowSearch, colSearch)) {
                continue;
            }
            if (isSeens[rowSearch][colSearch]) {
                continue;
            }
            if (grid[rowSearch][colSearch] == WATER) {
                continue;
            }
            isSeens[rowSearch][colSearch] = true;
            adjacentLands.offer(new int[] {rowSearch + 1, colSearch});
            adjacentLands.offer(new int[] {rowSearch - 1, colSearch});
            adjacentLands.offer(new int[] {rowSearch, colSearch + 1});
            adjacentLands.offer(new int[] {rowSearch, colSearch - 1});
        }
    }

    private boolean isOutOfGrid(char[][] grid, int row, int col) {
        return row < 0 || grid.length <= row || col < 0 || grid[0].length <= col;
    }
    
    /*
     * 思ったこと
     * ・書き始めてからもStackでやっぱりできないかな。。。が頭をよぎって、
     * でも考えは進まなくてただ悩むだけ、が何回か起きる。集中しなきゃ。
     * ・定数なのにfinalするの忘れた。
     */
}
