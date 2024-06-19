public class PowStep3 {
    // 1m30s
    public double myPow(double x, int n) {
        return myPowHelper(x, (long)n);
    }

    private double myPowHelper(double x, long n) {
        if (n == 0) return 1;
        if (n < 0) return myPowHelper(1 / x, -n);

        if (n % 2 == 1) {
            return x * myPowHelper(x * x, n / 2);
        } else {
            return myPowHelper(x * x, n / 2);
        }
    }
}
