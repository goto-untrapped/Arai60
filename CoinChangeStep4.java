// counts の役割が多いため、最小枚数を計算したかの判定を別変数で取り出す
public int coinChange(int[] coins, int amount) {
    int[] counts = new int[amount + 1];
    boolean[] calculated = new boolean[amount + 1];
    return coinChangeHelper(coins, amount, counts, calculated);
}

private int coinChangeHelper(int[] coins, int remain, int[] counts, boolean[] calculated) {
    if (remain == 0) {
        return 0;
    }
    if (remain < 0) {
        return -1;
    }
    if (calculated[remain]) {
        return counts[remain];
    }

    int maxCount = Integer.MAX_VALUE;
    for (int coin : coins) {
        int count = coinChangeHelper(coins, remain - coin, counts, calculated);
        if (count == -1) {
            continue;
        }
        maxCount = Math.min(maxCount, count + 1);
    }

    calculated[remain] = true;
    if (maxCount == Integer.MAX_VALUE) {
        counts[remain] = -1;
    }
    counts[remain] = maxCount;
    return counts[remain];
}
