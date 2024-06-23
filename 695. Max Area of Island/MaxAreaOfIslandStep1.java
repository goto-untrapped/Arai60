public class MaxAreaOfIslandStep1 {
    /*
     * 35min
     * ・下記を宣言してコンパイラが通らずに調べた
     * Queue<int[]> contiguousPositions = new Queue<int[2]>();
     * ・cellHeight - 1 > 0 と書いてWA
     * 時間計算量：O(H * W)
     * 空間計算量：O(H * W):全部1
     * */
    public int maxAreaOfIsland1_1(int[][] grid) {
        ArrayList<Integer> maxAreas = new ArrayList<>();
        Queue<int[]> contiguousPositions = new LinkedList<>();

        for (int height = 0; height < grid.length; height++) {
            for (int width = 0; width < grid[0].length; width++) {
                if (grid[height][width] == 1) {
                    grid[height][width] = 0;
                    int numCells = 1;
                    contiguousPositions.offer(new int[] {height, width});
                    while (!contiguousPositions.isEmpty()) {
                        int[] cellPosition = contiguousPositions.poll();
                        int cellHeight = cellPosition[0];
                        int cellWidth = cellPosition[1];
                        if (cellHeight - 1 >= 0 && grid[cellHeight - 1][cellWidth] == 1) {
                            numCells++;
                            grid[cellHeight - 1][cellWidth] = 0;
                            contiguousPositions.offer(new int[] {cellHeight - 1, cellWidth});
                        }
                        if (cellHeight + 1 < grid.length && grid[cellHeight + 1][cellWidth] == 1) {
                            numCells++;
                            grid[cellHeight + 1][cellWidth] = 0;
                            contiguousPositions.offer(new int[] {cellHeight + 1, cellWidth});
                        }
                        if (cellWidth - 1 >= 0 && grid[cellHeight][cellWidth - 1] == 1) {
                            numCells++;
                            grid[cellHeight][cellWidth - 1] = 0;
                            contiguousPositions.offer(new int[] {cellHeight, cellWidth - 1});
                        }
                        if (cellWidth + 1 < grid[0].length && grid[cellHeight][cellWidth + 1] == 1) {
                            numCells++;
                            grid[cellHeight][cellWidth + 1] = 0;
                            contiguousPositions.offer(new int[] {cellHeight, cellWidth + 1});
                        }
                    }
                    maxAreas.add(numCells);
                }
            }
        }

        int maxArea = 0;
        for (int maybeMaxArea : maxAreas) {
            maxArea = Math.max(maxArea, maybeMaxArea);
        }
        return maxArea;
    }
    
    /*
     * ・インデックスだけ微妙に違う同じコードをたくさん書いてしまった
     * でもグローバルで変数定義する方が嫌だった
     * いや、引数として渡せばいいのか。そこまで頭が回らなかった。
     */
    
    /*
     * 19min
     * BFSを書いて頭を整理できた
     * 時間計算量：O(H * W)
     * 空間計算量：O(H * W) 全部が1の時。深さは50 * 50 = 2500 だけど、Javaは1万くらいまでは大丈夫だと思っている。
     * ⇒スタックメモリのサイズが1MBの場合、1万回程度の関数呼び出しに耐えられるため という記述を見た。
     *   512k の場合も、5000回は耐えられる。
     */
    public int maxAreaOfIsland1_2(int[][] grid) {
        int maxArea = 0;
        for (int height = 0; height < grid.length; height++) {
            for (int width = 0; width < grid[0].length; width++) {
                if (grid[height][width] == 1) {
                    int numCells = numContiguousCells(grid, height, width, 0);
                    maxArea = Math.max(maxArea, numCells);
                }
            }
        }
        return maxArea;
    }
    private int numContiguousCells(int[][] grid, int height, int width, int num) {
        if (height < 0 || height >= grid.length || width < 0 || width >= grid[0].length) {
            return 0;
        }
        int total = 0;
        if (grid[height][width] == 1) {
            grid[height][width] = 0;
            total += 1;
        } else {
            return 0;
        }

        if (height - 1 >= 0) {
            total += numContiguousCells(grid, height - 1, width, total);
        }
        if (height + 1 < grid.length) {
            total += numContiguousCells(grid, height + 1, width, total);
        } 
        if (width - 1 >= 0) {
            total += numContiguousCells(grid, height, width - 1, total);
        }
        if (width + 1 < grid[0].length) {
            total += numContiguousCells(grid, height, width + 1, total);
        }
        return total;
    }
}
