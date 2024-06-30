public class UniquePathsStep1 {
    // 10min
    // 時間計算量：O(m * n)
    // 空間計算量：O(m * n)
    public int uniquePaths(int m, int n) {
        int[][] uniquePathsTotal = new int[m][n];
        for (int i = 0; i < uniquePathsTotal.length; i++) {
            for (int j = 0; j < uniquePathsTotal[0].length; j++) {
                if (i == 0 || j == 0) {
                    uniquePathsTotal[i][j] = 1;
                    continue;
                }
                uniquePathsTotal[i][j] = uniquePathsTotal[i - 1][j] + uniquePathsTotal[i][j - 1];
            }
        }
        return uniquePathsTotal[m - 1][n - 1];
    }
}
