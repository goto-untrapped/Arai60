public class UniquePathsIIStep2 {
    /*
     * https://github.com/hayashi-ay/leetcode/pull/44
     * https://github.com/shining-ai/leetcode/pull/34
     * https://github.com/SuperHotDogCat/coding-interview/pull/17
     */
    
    /*
     * Step1をきれいにする
     */
    static int OBSTACLE = 1;
    public int uniquePathsWithObstacles2_1(int[][] obstacleGrid) {
        if (obstacleGrid[0][0] == 1) return 0;
        int height = obstacleGrid.length + 1;
        int width = obstacleGrid[0].length + 1;
        int[][] uniquePathsTotal = new int[height][width];
        uniquePathsTotal[1][1] = 1;
        for (int h = 1; h < height; h++) {
            for (int w = 1; w < width; w++) {
                if (h == 1 && w == 1) continue;
                if (obstacleGrid[h - 1][w - 1] == OBSTACLE) {
                    uniquePathsTotal[h][w] = 0;
                } else {
                    uniquePathsTotal[h][w] = uniquePathsTotal[h - 1][w] + uniquePathsTotal[h][w - 1];
                }
            }
        }
        return uniquePathsTotal[height - 1][width - 1];
    }
    
    /*
     * 与えられたgridの大きさで解く
     */
    class Solution2_2 {
        static final int OBSTACLE = 1;
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            int[][] uniquePathsTotal = new int[obstacleGrid.length][obstacleGrid[0].length];
            for (int h = 0; h < uniquePathsTotal.length; h++) {
                if (obstacleGrid[h][0] == OBSTACLE) {
                    break;
                }
                uniquePathsTotal[h][0] = 1;
            }
            for (int w = 0; w < uniquePathsTotal[0].length; w++) {
                if (obstacleGrid[0][w] == OBSTACLE) {
                    break;
                }
                uniquePathsTotal[0][w] = 1;
            }
            for (int h = 1; h < uniquePathsTotal.length; h++) {
                for (int w = 1; w < uniquePathsTotal[0].length; w++) {
                    if (obstacleGrid[h][w] == OBSTACLE) {
                        obstacleGrid[h][w] = 0;
                        continue;
                    }
                    uniquePathsTotal[h][w] = uniquePathsTotal[h - 1][w] + uniquePathsTotal[h][w - 1];
                }
            }
            return uniquePathsTotal[uniquePathsTotal.length - 1][uniquePathsTotal[0].length - 1];
        }
    }
    
    /*
     * 1DP
     */
    class Solution2_3 {
        static final int OBSTACLE = 1;
        public int uniquePathsWithObstacles(int[][] obstacleGrid) {
            int h = obstacleGrid.length;
            int w = obstacleGrid[0].length;
            int[] uniquePathsTotal = new int[w];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if (obstacleGrid[i][j] == OBSTACLE) {
                        uniquePathsTotal[j] = 0;
                        continue;
                    }
                    if (i == 0 && j == 0) {
                        uniquePathsTotal[j] = 1;
                        continue;
                    }
                    if (j > 0) {
                        uniquePathsTotal[j] += uniquePathsTotal[j - 1];
                    }
                }
            }
            return uniquePathsTotal[w - 1];
        }
    }
}
