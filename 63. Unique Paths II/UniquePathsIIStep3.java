public class UniquePathsIIStep3 {
    // 5min / 9min / 4min
    static final int OBSTACLE = 1;
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int h = obstacleGrid.length;
        int w = obstacleGrid[0].length;
        int[][] uniquePathsTotal = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (obstacleGrid[i][j] == OBSTACLE) {
                    uniquePathsTotal[i][j] = 0;
                    continue;
                }
                if (i == 0 && j == 0) {
                    uniquePathsTotal[i][j] = 1;
                    continue;
                }
                if (i == 0) {
                    uniquePathsTotal[i][j] += uniquePathsTotal[i][j - 1];
                    continue;
                }
                if (j == 0) {
                    uniquePathsTotal[i][j] += uniquePathsTotal[i - 1][j];
                    continue;
                }
                uniquePathsTotal[i][j] = uniquePathsTotal[i - 1][j] + uniquePathsTotal[i][j - 1];
            }
        }
        return uniquePathsTotal[h - 1][w - 1];
    }
    
    /*
     * 思ったこと
     * ・0番目の行と列を場合分けせずに、continueと書いても前の条件を覚えていないといけないけど、慣れだと思った。
     */
}
