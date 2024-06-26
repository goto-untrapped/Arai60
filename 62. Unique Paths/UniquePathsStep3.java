public class UniquePathsStep3 {
    // 2min / 1m30s
    public int uniquePaths(int m, int n) {
        int[][] uniquePathsTotal = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
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
