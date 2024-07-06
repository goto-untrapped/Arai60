public class CoinChangeStep2 {
    // BottomUpDp 見ずに書くまで1h
    class Solution2_1 {
        static int WAIT_FOR_ASSIGN = Integer.MAX_VALUE;
        public int coinChange(int[] coins, int amount) {
            int[] fewestCombinationNums = new int[amount + 1];
            Arrays.fill(fewestCombinationNums, WAIT_FOR_ASSIGN);
            fewestCombinationNums[0] = 0;
            for (int subAmount = 1; subAmount <= amount; subAmount++) {
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
    
    /*
     * BFS 1h
     * https://github.com/shining-ai/leetcode/pull/40
     */
    public int coinChange2_2(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        Queue<int[]> subAmountAndFewestCombNums = new LinkedList<>();
        HashSet<Integer> foundFewest = new HashSet<>();
        subAmountAndFewestCombNums.offer(new int[] {0,0});
        while (!subAmountAndFewestCombNums.isEmpty()) {
            int[] subAmountAndFewestCombNum = subAmountAndFewestCombNums.poll();
            int subAmount = subAmountAndFewestCombNum[0];
            int fewestCombNum = subAmountAndFewestCombNum[1];
            for (int coin : coins) {
                if (amount < coin) {
                    continue;
                }
                int newSubAmount = subAmount + coin;
                if (subAmount > amount) {
                    continue;
                }
                if (newSubAmount == amount) {
                    return fewestCombNum + 1;
                }
                if (foundFewest.contains(newSubAmount)) {
                    continue;
                }
                subAmountAndFewestCombNums.offer(new int[] {newSubAmount, fewestCombNum + 1});
                foundFewest.add(newSubAmount);
            }
        }
        return -1;
    }
    /*     
     * 汚いので書き直し：
     * ・変数名が分かりにくい
     * ・足していく方式のため、制限する条件が多くて取りこぼしがありそう（オーバーフロー）
     * ・amount == 0 のケースを別で分けてるのも、できたらまとめたい
     * 
     * 15min 2_2を書き直し
     */
    public int coinChange2_3(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        LinkedList<int[]> subAmountFewestCombNums = new LinkedList<>();
        HashSet<Integer> foundFewestCombNums = new HashSet<>();
        subAmountFewestCombNums.offer(new int[] {amount, 0});
        while (!subAmountFewestCombNums.isEmpty()) {
            int[] info = subAmountFewestCombNums.poll();
            int subAmount = info[0];
            int combNum = info[1];
            for (int coin : coins) {
                int remain = subAmount - coin;
                if (remain < 0) {
                    continue;
                }
                if (remain == 0) {
                    return combNum + 1;
                }
                if (foundFewestCombNums.contains(remain)) {
                    continue;
                }
                subAmountFewestCombNums.offer(new int[] {remain, combNum + 1});
                foundFewestCombNums.add(remain);
            }
        }
        return -1;
    }
    /*     
     * まだ微妙：
     * ・if (remain == 0) は特別
     * ・coinのループでたくさんのことをやりすぎている気がする。queueに入れておいて、
     * 取り出す時に合計が0になっていれば返せるように書いた方が、役割分担できそう
     * ・LinkedList を別の実装クラスにしたいわけではなかったが、Queueということが察しにくく感じ、やっぱり戻したい
     * 
     * 2_3を書き直し
     */
    public int coinChange2_4(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        Queue<int[]> subAmountFewestCombNums = new LinkedList<>();
        HashSet<Integer> foundFewestCombNums = new HashSet<>();
        subAmountFewestCombNums.offer(new int[] {amount, 0});
        while (!subAmountFewestCombNums.isEmpty()) {
            int[] info = subAmountFewestCombNums.poll();
            int subAmount = info[0];
            int fewestCombNum = info[1];
            if (subAmount == 0) {
                return fewestCombNum;
            }
            for (int coin : coins) {
                int remain = subAmount - coin;
                if (remain < 0) {
                    continue;
                }
                if (foundFewestCombNums.contains(remain)) {
                    continue;
                }
                subAmountFewestCombNums.offer(new int[] {remain, fewestCombNum + 1});
                foundFewestCombNums.add(remain);
            }
        }
        return -1;
    }
    /*
     * 思ったこと：
     * ・合計が0の場合を分けて書いたことで、変数名がすっきりした。
     * ・info という名前自体は微妙だけど、中身を取り出したいだけということが伝わってほしかった。
     * ・これ以上変数名が長くなると分かりにくいし、書き間違えそう。
     */
    
    // 40min DFS
    class Solution2_5 {
        static final int WAIT_FOR_ASSIGN = Integer.MAX_VALUE;
        public int coinChange(int[] coins, int amount) {
            int[] fewestNums = new int[amount + 1];
            return coinChangeHelper(coins, amount, fewestNums);
        }

        private int coinChangeHelper(int[] coins, int remain, int[] fewestNums) {
            if (remain == 0) {
                return 0;
            }
            if (remain < 0) {
                return -1;
            }
            if (fewestNums[remain] != 0) {
                return fewestNums[remain];
            }

            int finalFewestNum = WAIT_FOR_ASSIGN;
            for (int coin : coins) {
                int fewestNum = coinChangeHelper(coins, remain - coin, fewestNums);
                if (fewestNum == -1) {
                    continue;
                }
                finalFewestNum = Math.min(finalFewestNum, fewestNum + 1);
            }

            if (finalFewestNum == WAIT_FOR_ASSIGN) {
                fewestNums[remain] = -1;
            } else {
                fewestNums[remain] = finalFewestNum;
            }
            return fewestNums[remain];
        }
    }
    
    /*
     * その他：
     * ・Integerを使って組み合わせ数が見つからない場合はnullで表現すると分かりやすそうだと思ったが、
     * 使わなくてもできるし少しはパフォーマンスに影響しそうなのでやめた 
     * https://stackoverflow.com/questions/14557989/autoboxing-performance
     * https://stackoverflow.com/questions/6037389/integer-auto-unboxing-and-auto-boxing-gives-performance-issues
     */
}
/*
 * https://github.com/hayashi-ay/leetcode/pull/68
 *   https://discord.com/channels/1084280443945353267/1196472827457589338/1196541234169266387 46
 * https://github.com/shining-ai/leetcode/pull/40
 *   https://discord.com/channels/1084280443945353267/1201211204547383386/1225481127007883345
 * https://github.com/thonda28/leetcode/pull/1
 *   https://discord.com/channels/1084280443945353267/1230079550923341835/1230201149144043530
 * https://github.com/ryoooooory/LeetCode/pull/4
 *   https://discord.com/channels/1084280443945353267/1218823830743547914/1230507344991879219
 * https://github.com/rossy0213/leetcode/pull/16
 * https://github.com/sakupan102/arai60-practice/pull/41
 */
