public class MaxAreaOfIslandStep3 {
    // 20min / 9min / 6min
    class Solution {
        static int LAND = 1;
        static int WATER = 0;
        boolean[][] seen;
        public int maxAreaOfIsland(int[][] grid) {
            seen = new boolean[grid.length][grid[0].length];
            int maxArea = 0;
            for (int h = 0; h < grid.length; h++) {
                for (int w = 0; w < grid[0].length; w++) {
                    if (grid[h][w] == LAND && !isSeen(seen, h, w)) {
                        maxArea = Math.max(maxArea, numContiguousAreas(grid, h, w));
                    }
                }
            }
            return maxArea;
        }

        private int numContiguousAreas(int[][] grid, int h, int w) {
            if (isOutOfRange(grid, h, w)) return 0;
            if (isWater(grid, h, w)) return 0;
            if (isSeen(seen, h, w)) return 0;

            int total = 1;
            seen[h][w] = true;
            int[][] directions = new int[][] {{1,0},{-1,0},{0,1},{0,-1}};
            for (int i = 0; i < directions.length; i++) {
                total += numContiguousAreas(grid, h + directions[i][0], w + directions[i][1]);
            }
            return total;
        }

        private boolean isOutOfRange(int[][] grid, int h, int w) {
            return h < 0 || grid.length <= h || w < 0 || grid[0].length <= w;
        }

        private boolean isWater(int[][] grid, int h, int w) {
            return grid[h][w] == WATER;
        }

        private boolean isSeen(boolean[][] seen, int h, int w) {
            return seen[h][w];
        }
    }
}
