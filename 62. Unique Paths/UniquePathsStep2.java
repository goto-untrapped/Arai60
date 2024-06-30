public class UniquePathsStep2 {
    /*
     * https://github.com/hayashi-ay/leetcode/pull/39/files
     *   https://discord.com/channels/1084280443945353267/1200089668901937312/1214202653035339816
     * https://github.com/shining-ai/leetcode/pull/32#pullrequestreview-1960409500
     * https://github.com/SuperHotDogCat/coding-interview/pull/16/files
     * https://github.com/sakupan102/arai60-practice/pull/34
     * https://github.com/rossy0213/leetcode/pull/19/files
     * https://github.com/YukiMichishita/LeetCode/pull/14
     */
    
    /*
     * 1DP
     * https://github.com/hayashi-ay/leetcode/pull/39/files
     */
    public int uniquePaths2_1(int m, int n) {
        if (n < m) return uniquePaths2_1(n, m);

        int[] uniquePathsTotal = new int[n];
        Arrays.fill(uniquePathsTotal, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                uniquePathsTotal[j] += uniquePathsTotal[j - 1];
            }
        }
        return uniquePathsTotal[n - 1];
    }
    
    /*
     * nCkをうまく計算したい
     * https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/src-html/org/apache/commons/math3/util/CombinatoricsUtils.html
     * (ArithmeticUtils.factorialはdeprecated)
     * nが0から20の場合は定数で計算し、それ以外はdouble型の範囲で返している:factorialLog()
     *   https://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/src-html/org/apache/commons/math3/util/FastMath.html
     *   logSumを計算して、e^logSum + 0.5 を切り捨てて累乗を出している
     */
    public int uniquePaths2_2(int m, int n) {
        int numCandidate = m + n - 2;
        int duplicated = n - 1;
        return factorial(numCandidate).divide(factorial(numCandidate - duplicated)).divide(factorial(duplicated)).intValue();
//        return factorial(numCandidate, BigInteger.valueOf(1)).divide(factorial(numCandidate - duplicated, BigInteger.valueOf(1))).divide(factorial(duplicated, BigInteger.valueOf(1))).intValue(); 
    }

    private BigInteger factorial(int n) {
        BigInteger total = new BigInteger("1");
        for (int i = 2; i <= n; i++) {
            total = total.multiply(BigInteger.valueOf(i));
        }
        return total;
    }
    
//    private BigInteger factorial(int n) {
//        if (n <= 1) return new BigInteger("1");
//        return BigInteger.valueOf(n).multiply(factorial(n - 1));
//    }

//    private BigInteger factorial(int n, BigInteger total) {
//        if (n <= 1) return total;
//        return factorial(n - 1, BigInteger.valueOf(n).multiply(total));
//    }
    
    /*
     * nCkをDPで解く
     * https://github.com/sakupan102/arai60-practice/pull/34/files
     */
    public int uniquePaths2_3(int m, int n) {
        int places = m + n - 2;
        int numSameDirection = m - 1;
        int[][] cache = new int[places + 1][numSameDirection + 1];
        return uniquePathsHelper(places, numSameDirection, cache);
    }
    private int uniquePathsHelper(int n, int k, int[][] cache) {
        if (n == 0 || k == 0 || n == k) {
            cache[n][k] = 1;
            return cache[n][k];
        }
        if (cache[n][k] != 0) return cache[n][k];
        cache[n][k] = uniquePathsHelper(n - 1, k, cache) + uniquePathsHelper(n - 1, k - 1, cache);
        return cache[n][k];
    }
}
