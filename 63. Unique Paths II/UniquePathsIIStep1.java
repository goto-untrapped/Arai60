public class UniquePathsIIStep1 {
    /*
     * 20min デバッグ複数回
     * obstacleがある場合が0と勘違い ⇒ 1の場合、に修正
     * スタート地点にobstacleケースの考慮漏れ ⇒ if文を追加
     * obstacleの先の壁も1でカウントしていた ⇒ 壁も前のマスを見る必要があり、
     *   列の壁と行の壁という条件だけ違くて中身は一緒のため、配列を拡張して対応するも、
     *   スタート地点の1が上書きされたり、仮行列を追加したことを考慮せずにobstacleGridとuniquePathsTotalのインデックスを
     *   指定したりして指定間違え、デバッグしながらちまちま直す
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid[0][0] == 1) return 0;
        int[][] uniquePathsTotal = new int[obstacleGrid.length + 1][obstacleGrid[0].length + 1];
        uniquePathsTotal[1][1] = 1;
        for (int i = 1; i < uniquePathsTotal.length; i++) {
            for (int j = 1; j < uniquePathsTotal[0].length; j++) {
                if (i == 1 && j == 1) continue;
                if (i == 1 || j == 1) {
                    if (obstacleGrid[i - 1][j - 1] == 1) {
                        uniquePathsTotal[i][j] = 0;
                    } else {
                        uniquePathsTotal[i][j] = uniquePathsTotal[i - 1][j] + uniquePathsTotal[i][j - 1];
                    }
                    continue;
                }
                if (obstacleGrid[i - 1][j - 1] == 1) {
                    uniquePathsTotal[i][j] = 0;
                } else {
                    uniquePathsTotal[i][j] = uniquePathsTotal[i - 1][j] + uniquePathsTotal[i][j - 1];
                }
            }
        }
        return uniquePathsTotal[uniquePathsTotal.length - 1][uniquePathsTotal[0].length - 1];
    }
    
    /*
     * よく見たらif-elseの中身一緒だからまとめられた。
     * 配列のサイズは長ったらしいし何回も使っているのでミスを減らすためにも変数で定義したい。
     * まだ速さを求める段階じゃない。まずはテストケースを色々想定してデバッグして、1発で通るコードにすることを心掛ける。
     */
}
