public class NumberOfIslandsStep2 {
    /*
     * DFS(Stack)で書いてみる
     * 18min
     */
    class Solution2_1 {
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
            Stack<int[]> connected = new Stack<>();
            connected.push(new int[] {row, col});
            while (!connected.isEmpty()) {
                int[] position = connected.pop();
                int[][] deltas = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
                for (int[] delta : deltas) {
                    int searchRow = position[0] + delta[0];
                    int searchCol = position[1] + delta[1];
                    if (isOutOfGrid(grid, searchRow, searchCol)) {
                        continue;
                    }
                    if (grid[searchRow][searchCol] != LAND) {
                        continue;
                    }
                    grid[searchRow][searchCol] = VISITED;
                    connected.push(new int[] {searchRow, searchCol});
                }
            }
        }

        private boolean isOutOfGrid(char[][] grid, int row, int col) {
            return !(0 <= row && row < grid.length && 0 <= col && col < grid[0].length);
        }
    }
    /*
     * 取り入れたこと
     * ・VISITEDを使って、破壊的にだけど可読性も上げつつ書いてみる
     * ・numOfIslandsという名前は中途半端では？
     * ・stackに入れる前に条件判定
     * ・4方向は配列にしてみる
     * ・grid内かの判定はnot()で
     * 思ったこと
     * ・deltasという名前は許容範囲内ではあるかなと思っている
     */
    
    /*
     * DFS(再帰)で書いてみる
     * 10min
     */
    class Solution2_2 {
        public int numIslands(char[][] grid) {
            int numberOfIslands = 0;
            int numRows = grid.length;
            int numCols = grid[0].length;
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    if (grid[row][col] == '0') {
                        continue;
                    }
                    numberOfIslands++;
                    traverseLands(grid, row, col);
                }
            }
            return numberOfIslands;
        }

        private void traverseLands(char[][] grid, int row, int col) {
            if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
                return;
            }
            if (grid[row][col] == '0') {
                return;
            }
            grid[row][col] = '0';
            traverseLands(grid, row - 1, col);
            traverseLands(grid, row + 1, col);
            traverseLands(grid, row, col + 1);
            traverseLands(grid, row, col - 1);
        }
    }
    
    /*
     * 取り入れたこと
     * ・直接gridを書き換えてみる
     * 思ったこと
     * ・分かりにくくならない範囲で一番短く書こうとした
     * ・問題文にあるnumIslands()のような動詞から始まらない関数名ってそれなりに存在するのだろうか。
     */
}

/*
 * 参考箇所メモ
 * https://discord.com/channels/1084280443945353267/1183683738635346001/1194329732544737330
 *   ・Stack を使った書き方例
 * https://github.com/hayashi-ay/leetcode/pull/33
 * ・directions いいな
 * ・BFS、先に条件判定したら回る回数減るからいいね、でも入れてからまとめて判定すると頭が楽、Step1は頭が楽な方を取った
 * ・入力破壊楽だな、1パーツ考えることが減る感じ。
 * https://github.com/ryoooooory/LeetCode/pull/2
 * ・Queueの実装クラスをよく知らずにとりあえず見たことのあるLinkedListを使っていた。
 * LinkedListはノード間で相互情報を持てるようにしてあり、おそらくそのために適したインデックス構造になっているため、
 * ただ可変長配列として要素を持っているArrayDequeの方が、配列を独立に取得して見たい時は、使うと効率が上がるはずというふうに理解した。
 * 　・StackのDocsはArrayDequeを優先して使ってほしいと言っているけど、とりあえず一旦はStackでいいんじゃないかと思う（業務では合わせる）
 * ・やっぱり最外辺時のインデックスエラー回避判定と、必要な処理である格納値の判定を、同時に書くのは不等号を間違える自信がある。けど、何も思いつかない時はそれでもいいから書いたほうがいいな。
 * ・deltaRow, deltaColを別々の配列で持つと、順番や値が対応しているか気になるため、Unmodifiableに宣言するか、int[][]やList<int[]>で持つように書きたいかも。
 * ・技術的にcurrent という名前へのイメージがなく、便利そうに思ってたけど、逆に何のcurrentなのかになりやすそうで、もっと分かりやすい名前があるか先に考えた方がよさそう 
 */
