public class MaxAreaOfIslandStep4 {
    /*
     * Step3を修正
     * ・定数はstaticを使おう
     * ・seenは関数に入れずにそのまま使うで十分分かりやすい
     * ・ネストは浅く
     */
    class Solution {
        static final int WATER = 0;
        static final int LAND = 1;
        static final int[][] directions = new int[][] {{1,0},{-1,0},{0,1},{0,-1}};
        boolean[][] seen;
        public int maxAreaOfIsland(int[][] grid) {
            seen = new boolean[grid.length][grid[0].length];
            int maxArea = 0;
            for (int h = 0; h < grid.length; h++) {
                for (int w = 0; w < grid[0].length; w++) {
                    if (grid[h][w] == WATER || seen[h][w]) continue;
                    maxArea = Math.max(maxArea, calculateAreaOfIsland(grid, h, w));
                }
            }
            return maxArea;
        }

        private int calculateAreaOfIsland(int[][] grid, int h, int w) {
            if (isOutOfRange(grid, h, w)) return 0;
            if (isWater(grid, h, w)) return 0;
            if (seen[h][w]) return 0;

            int total = 1;
            seen[h][w] = true;
            for (int i = 0; i < directions.length; i++) {
                total += calculateAreaOfIsland(grid, h + directions[i][0], w + directions[i][1]);
            }
            return total;
        }

        private boolean isOutOfRange(int[][] grid, int h, int w) {
            return h < 0 || grid.length <= h || w < 0 || grid[0].length <= w;
        }

        private boolean isWater(int[][] grid, int h, int w) {
            return grid[h][w] == WATER;
        }
    }
    
    // Step1,2を修正
    /*
     * 修正箇所
     * ・unionの時、indexを決める場所とfindしてunionする場所を分ける
     */
    class Solution2_3 {
        public int maxAreaOfIsland(int[][] grid) {
            UnionFind unionFind = new UnionFind(grid);
            HashMap<Integer,Integer> groupIndexToNumArea = new HashMap<>();
            for (int height = 0; height < grid.length; height++) {
                for (int width = 0; width < grid[0].length; width++) {
                    if (grid[height][width] == 1) {
                        int[][] directions = new int[][] {{1,0},{0,1}};
                        for (int i = 0; i < directions.length; i++) {
                            int nextHeight = height + directions[i][0];
                            int nextWidth = width + directions[i][1];

                            if (isOutOfRange(grid, nextHeight, nextWidth)) continue;
                            if (isWater(grid, nextHeight, nextWidth)) continue;
                            int groupIndex = unionFind.calculateIndex(height, width);
                            int groupNextIndex = unionFind.calculateIndex(nextHeight, nextWidth);
                            unionFind.union(groupIndex, groupNextIndex);
                        }
                    }
                }
            }
            for (int h = 0; h < grid.length; h++) {
                for (int w = 0; w < grid[0].length; w++) {
                    if (grid[h][w] == 1) {
                        int groupIndex = unionFind.find(unionFind.calculateIndex(h, w));
                        groupIndexToNumArea.put(groupIndex, groupIndexToNumArea.getOrDefault(groupIndex, 0) + 1);
                    }
                }
            }
            int maxArea = 0;
            for (int area : groupIndexToNumArea.values()) {
                maxArea = Math.max(maxArea, area);
            }
            return maxArea;
        }

        private boolean isOutOfRange(int[][] grid, int height, int width) {
            return height >= grid.length || width >= grid[0].length;
        }

        private boolean isWater(int[][] grid, int height, int width) {
            return grid[height][width] == 0;
        }
    }

    class UnionFind {
        int length;
        int[] group;

        public UnionFind(int[][] grid) {
            length = grid[0].length;
            group = new int[grid.length * grid[0].length];
            for (int i = 0; i < group.length; i++) {
                group[i] = i;
            }
        }

        public void union(int index, int nextIndex) {
            int groupIndex = find(index);
            int nextGroupIndex = find(nextIndex);
            group[groupIndex] = nextGroupIndex;
        }

        public int calculateIndex(int height, int width) {
            return height * this.length + width;
        }

        public int find(int index) {
            if (group[index] != index) {
                index = find(group[index]);
            }
            return index;
        }
    }
}
