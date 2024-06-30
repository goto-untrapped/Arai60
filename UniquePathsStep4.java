/*
 * 修正箇所
 * ・処理を変数化
 */
import java.math.BigInteger;

public int uniquePaths4_1(int m, int n) {
    int numCandidate = m + n - 2;
    int duplicated = n - 1;
    
    BigInteger allCombinations = factorial(numCandidate);
    BigInteger firstCombinations = factorial(numCandidate - duplicated);
    BigInteger secondCombinations = factorial(duplicated);
    
    return allCombinations.divide(firstCombinations).divide(secondCombinations).intValue();
}

private BigInteger factorial(int n) {
    BigInteger total = new BigInteger("1");
    for (int i = 2; i <= n; i++) {
        total = total.multiply(BigInteger.valueOf(i));
    }
    return total;
}
