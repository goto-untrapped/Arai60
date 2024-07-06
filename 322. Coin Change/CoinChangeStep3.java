public class CoinChangeStep3 {
    // 9min / 3m30s / 3min
    class Solution {
        static final int WAIT_FOR_ASSIGN = Integer.MAX_VALUE;
        public int coinChange(int[] coins, int amount) {
            int[] fewestCombinationNums = new int[amount + 1];
            Arrays.fill(fewestCombinationNums, WAIT_FOR_ASSIGN);
            fewestCombinationNums[0] = 0;
            for (int subAmount = 0; subAmount <= amount; subAmount++) {
                for (int value : coins) {
                    int remain = subAmount - value;
                    if (remain < 0) {
                        continue;
                    }
                    if (fewestCombinationNums[remain] == WAIT_FOR_ASSIGN) {
                        continue;
                    }
                    fewestCombinationNums[subAmount] = Math.min(fewestCombinationNums[subAmount], fewestCombinationNums[remain] + 1);
                }
            }
            if (fewestCombinationNums[amount] == WAIT_FOR_ASSIGN) {
                return -1;
            }
            return fewestCombinationNums[amount];
        }
    }
}
