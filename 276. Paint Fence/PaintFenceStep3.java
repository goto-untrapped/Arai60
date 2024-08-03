public class PaintFenceStep3 {
    // 1min30s / 1min
    public int numWays(int n, int k) {
        if (n == 1) {
            return k;
        }
        int[] numOfWays = new int[n + 1];
        numOfWays[1] = k;
        numOfWays[2] = k * k;
        for (int i = 3; i < numOfWays.length; i++) {
            numOfWays[i] = numOfWays[i - 1] * (k - 1) + numOfWays[i - 2] * (k - 1) * 1;
        }
        return numOfWays[numOfWays.length - 1];
    }
    /*
     * 思ったこと
     * ・インデックスがそのままフェンスの本数を表しているほうが、その本数での組み合わせを表せて分かりやすいと思った。
     * ・式をまとめない方が、何を計算しているのか分かりやすいと思った。
     */
}
