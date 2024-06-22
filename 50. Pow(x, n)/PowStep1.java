public class PowStep1 {
    /*
     * 1hくらい理解するのに使った
     * 最終的には5minくらいで書いた
     * 時間計算量：O(logn) 1回の計算で 1/2 にできるから
     * 空間計算量：O(logn) 再帰の深さはlogn
     * n が int だと StackOverflowError するけど long だとしない
     * ⇒ n = -2^31 の時、-2^31 = 10...00
     *    -2^31 * -1 = 01...11 + 1 = 10...00 となり、-2^31 となり、L17から抜け出せない
     */
    public double myPow(double x, int n) {
        return calced(x, (long)n);
    }

    private double calced(double x, long n) {
        if (n == 0) return 1;
        if (n < 0) return 1 / calced(x, -1 * n);

        if (n % 2 == 0) {
            return calced(x * x, n / 2);
        } else {
            return x * calced(x * x, (n - 1) / 2);
        }
    }
}
