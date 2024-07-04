public class UniquePathsIIStep4 {
    static final int OBSTACLE = 1;
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid[0][0] == OBSTACLE) {
            return 0;
        }
        int h = obstacleGrid.length;
        int w = obstacleGrid[0].length;
        int[][] uniquePathsTotal = new int[h][w];
        uniquePathsTotal[0][0] = 1;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (obstacleGrid[i][j] == OBSTACLE) {
                    continue;
                }
                if (i != 0) {
                    uniquePathsTotal[i][j] += uniquePathsTotal[i - 1][j];
                }
                if (j != 0) {
                    uniquePathsTotal[i][j] += uniquePathsTotal[i][j - 1];
                }
            }
        }
        return uniquePathsTotal[h - 1][w - 1];
    }
}
