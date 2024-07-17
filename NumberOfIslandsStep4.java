public class NumberOfIslandsStep4 {
   /*
    * Queueに複数の変数を組み合わせごと格納したい時、
    * 配列以外に、Pairの代替として使えそうなクラスを使ってみる
    */ 
    // Record を使う場合
    class Solution4_1 {
        static final char WATER = '0';
        static final char LAND = '1';
        static final char VISITED = '2';
        public int numIslands(char[][] grid) {
            int numberOfIslands = 0;
            int numRows = grid.length;
            int numCols = grid[0].length;
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    if (grid[row][col] != LAND) {
                        continue;
                    }
                    numberOfIslands++;
                    visit(grid, row, col);
                }
            }
            return numberOfIslands;
        }

        private void visit(char[][] grid, int row, int col) {
            grid[row][col] = VISITED;
            Stack<Position> connected = new Stack<>();
            connected.push(new Position(row, col));
            while (!connected.isEmpty()) {
                Position position = connected.pop();
                int[][] deltas = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
                for (int[] delta : deltas) {
                    int rowSearch = position.row() + delta[0];
                    int colSearch = position.col() + delta[1];
                    if (isOutOfGrid(grid, rowSearch, colSearch)) {
                        continue;
                    }
                    if (grid[rowSearch][colSearch] != LAND) {
                        continue;
                    }
                    grid[rowSearch][colSearch] = VISITED;
                    connected.push(new Position(rowSearch, colSearch));
                }
            }
        }

        private boolean isOutOfGrid(char[][] grid, int row, int col) {
            return !(0 <= row && row < grid.length && 0 <= col && col < grid[0].length);
        }
        
        record Position(int row, int col) {}
    }
    /*
     * 思ったこと
     * ・自作クラスよりとても楽に定義できる。
     * ・パラメータも増やせるので、柔軟に使える。
     * ・Record について全然知らない（値は不変らしい、とか）ので、使うとしたらドキュメント見てみる。
     */
    
    // Mat.Entry<> を使う場合
    class Solution4_2 {
        static final char WATER = '0';
        static final char LAND = '1';
        static final char VISITED = '2';
        public int numIslands(char[][] grid) {
            int numberOfIslands = 0;
            int numRows = grid.length;
            int numCols = grid[0].length;
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    if (grid[row][col] != LAND) {
                        continue;
                    }
                    numberOfIslands++;
                    visit(grid, row, col);
                }
            }
            return numberOfIslands;
        }

        private void visit(char[][] grid, int row, int col) {
            grid[row][col] = VISITED;
            Stack<Map.Entry<Integer, Integer>> connected = new Stack<>();
            connected.push(Map.entry(row, col));
            while (!connected.isEmpty()) {
                Map.Entry<Integer, Integer> position = connected.pop();
                int[][] deltas = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
                for (int[] delta : deltas) {
                    int rowSearch = position.getKey() + delta[0];
                    int colSearch = position.getValue() + delta[1];
                    if (isOutOfGrid(grid, rowSearch, colSearch)) {
                        continue;
                    }
                    if (grid[rowSearch][colSearch] != LAND) {
                        continue;
                    }
                    grid[rowSearch][colSearch] = VISITED;
                    connected.push(Map.entry(rowSearch, colSearch));
                }
            }
        }

        private boolean isOutOfGrid(char[][] grid, int row, int col) {
            return !(0 <= row && row < grid.length && 0 <= col && col < grid[0].length);
        }
    }
    /*
     * 思ったこと
     * ・シンプルにペアとして定義できる。
     */
}
