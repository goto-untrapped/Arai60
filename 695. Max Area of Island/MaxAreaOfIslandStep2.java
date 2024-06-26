public class MaxAreaOfIslandStep2 {
    /*
     * https://github.com/hayashi-ay/leetcode/pull/34#pullrequestreview-1900756394
     *   https://discord.com/channels/1084280443945353267/1200089668901937312/1211696971992141905
     * https://github.com/shining-ai/leetcode/pull/18#pullrequestreview-1915939521
     *   https://discord.com/channels/1084280443945353267/1201211204547383386/1214554228777811989
     * https://github.com/YukiMichishita/LeetCode/pull/6
     * https://github.com/rossy0213/leetcode/pull/9
     * https://discord.com/channels/1084280443945353267/1227073733844406343/1235246908109226004
     */
    
    /*
     * BFSをきれいにする。入力を変えない。
     * seenをHashSetで書こうとしたが、中身equalsができず、@Overrideする必要がありそう。
     */
    class Solution2_1 {
        public static int LAND = 1;
        public static int WATER = 0;
        public int maxAreaOfIsland(int[][] grid) {
            ArrayList<Integer> maxAreas = new ArrayList<>();
            Queue<int[]> contiguousPositions = new LinkedList<>();
            boolean[][] seen = new boolean[grid.length][grid[0].length];

            for (int height = 0; height < grid.length; height++) {
                for (int width = 0; width < grid[0].length; width++) {
                    if (grid[height][width] == LAND) {
                        int numCells = 0;
                        contiguousPositions.offer(new int[] {height, width});
                        while (!contiguousPositions.isEmpty()) {
                            int[] cellPosition = contiguousPositions.poll();
                            int cellHeight = cellPosition[0];
                            int cellWidth = cellPosition[1];

                            if (isCellOutOfRange(grid, cellHeight, cellWidth)) continue;
                            if (isCellWater(grid, cellHeight, cellWidth)) continue;
                            if (isCounted(seen, cellHeight, cellWidth)) continue;

                            numCells++;
                            grid[cellHeight][cellWidth] = WATER;
                            seen[cellHeight][cellWidth] = true;

                            contiguousPositions.offer(new int[] {cellHeight - 1, cellWidth});
                            contiguousPositions.offer(new int[] {cellHeight + 1, cellWidth});
                            contiguousPositions.offer(new int[] {cellHeight, cellWidth - 1});
                            contiguousPositions.offer(new int[] {cellHeight, cellWidth + 1});
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

        private boolean isCellOutOfRange(int[][] grid, int height, int width) {
            return height < 0 || height >= grid.length || width < 0 || width >= grid[0].length;
        }

        private boolean isCellWater(int[][] grid, int height, int width) {
            return grid[height][width] == WATER;
        }

        private boolean isCounted(boolean[][] seen, int height, int width) {
            return seen[height][width];
        }
    }

    /*
     * DFSをきれいにする。
     */
    class Solution2_2 {
        public static int LAND = 1;
        public static int WATER = 0;
        public int maxAreaOfIsland(int[][] grid) {
            int maxArea = 0;
            for (int height = 0; height < grid.length; height++) {
                for (int width = 0; width < grid[0].length; width++) {
                    if (grid[height][width] == LAND) {
                        int numCells = numContiguousCells(grid, height, width);
                        maxArea = Math.max(maxArea, numCells);
                    }
                }
            }
            return maxArea;
        }

        private int numContiguousCells(int[][] grid, int height, int width) {
            if (isCellOutOfRange(grid, height, width)) return 0;
            if (grid[height][width] == WATER) return 0;

            int total = 1;
            grid[height][width] = WATER;

            int[][] dHeightWidth = new int[][] {
                {1, 0}, {-1, 0}, {0, -1}, {0, 1}
            };
            for (int delta = 0; delta < dHeightWidth.length; delta++) {
                total += numContiguousCells(grid, height + dHeightWidth[delta][0], width + dHeightWidth[delta][1]);
            }
            
            return total;
        }

        private boolean isCellOutOfRange(int[][] grid, int height, int width) {
            return height < 0 || height >= grid.length || width < 0 || width >= grid[0].length;
        } 
    }
    
    /*
     * UnionFindを書いてみる。
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
                            unionFind.findAndUnion(height, width, nextHeight, nextWidth);
                        }
                    }
                }
            }
            for (int h = 0; h < grid.length; h++) {
                for (int w = 0; w < grid[0].length; w++) {
                    if (grid[h][w] == 1) {
                        int groupIndex = unionFind.find(unionFind.groupIndex(h, w));
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

        public void findAndUnion(int height, int width, int nextHeight, int nextWidth) {
            int index = groupIndex(height, width);
            int nextIndex = groupIndex(nextHeight, nextWidth);
            int groupIndex = find(index);
            int nextGroupIndex = find(nextIndex);
            group[groupIndex] = nextGroupIndex;
        }

        public int groupIndex(int height, int width) {
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
