public class CoinChangeStep1 {
    /*
     * 1時間弱で回答を見て書いた。最終的に感覚的だが5minで書いた。
     * 時間計算量：O(金額 * 通貨の種類数)
     * ⇒ 金額の円ごとに通貨の種類数から最小の組み合わせを探すが、1回計算すれば保存されるから、金額ごとに通貨の種類数見れば、最小が分かる
     *    再帰の最大呼び出し回数は、 (通貨 - 最小の通貨)回なので、(10^4 - 1)で約1万回。
     *    呼び出しのオーバーヘッドは、
     *    関数の引数： coins, remain, counts： 12 * 4 + 4 + 10^4 * 4 ≒ 40000byte = 0.04MB
     *    ローカル変数：count, i: 4 + 4 = 8byte
     *    リターンアドレス、ベースポインタ：計16byte
     *    全部で0.04MBくらい
     *    Stackのメモリ容量が1MBとすると、1 / 0.04 = 25回 しか呼び出せない？
     *    ⇒ ローカルで coins=[1], amount=[10000] で実行時エラー
     *      LeetCodeだと通るけど、メモリがたくさん設定されているのだろうか？（0.04MB * 10000 = 400MBくらい？）
     * 空間計算量：O(amount)
     * 
     * 回答を確認。DPの問題だったことに気付けず。
     * ふつうに合計を計算したら同じ計算が何回も出てくるから、という発想なのだろうか。
     * 間に合わないかも、という感覚がないといけない気がする。そしてそれがなかった。
     * ⇒普通に計算すると、時間計算量が 全組み合わせの (amount / c1) * (amount / c2) * ... = amount^nになるから、よくないね、という感覚。(n^2でも遅いのに)
     */
    static int callCount = 0;
    public int coinChange(int[] coins, int amount) {
        int[] counts = new int[amount + 1];
        return coinChangeHelper(coins, amount, counts);
    }

    private int coinChangeHelper(int[] coins, int remain, int[] counts) {
        if (remain == 0) {
            return 0;
        }
        if (remain < 0) {
            return -1;
        }
        if (counts[remain] != 0) {
            return counts[remain];
        }

        int count = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            int thisCount = coinChangeHelper(coins, remain - coins[i], counts);
            if (thisCount == -1) {
                continue;
            }
            count = Math.min(count, thisCount + 1);
        }

        if (count != Integer.MAX_VALUE) {
            counts[remain] = count;
        } else {
            counts[remain] = -1;
        }
        return counts[remain];
    }
    
    // --------------------↓WA
    // 不正解だけど、思考ログとして
    
    /*
     * 2回目WA 20min
     * StackOverflowError: coins = [1,2147483647], amount = 2
     */
    public int coinChangeStackOverflowError(int[] coins, int amount) {
        coins = Arrays.stream(coins)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
        return coinChangeHelper(coins, amount, 0, 0);
    }
    private int coinChangeHelper(int[] coins, int amount, int total, int count) {
        if (total == amount) {
            return count;
        }
        if (total > amount) {
            return -1;
        }

        int fewestCount = 0;
        for (int coin : coins) {
            fewestCount = coinChangeHelper(coins, amount, total + coin, count + 1);
            if (fewestCount != -1) {
                return fewestCount;
            }
        }
        return -1;
    }
    
    /*
     * 1回目WA 20min
     * TLE: coins=[1,2,5], amount=100
     * 1 <= coins[i] <= 2^31 - 1, 0 <= amount <= 10^4 のため、
     * 再帰でやると最大1万回呼び出しとなりエラーになるかもと思い、BFSで書いた。
     * 
     * 思ったこと
     * ・coinsをreverseにする方法を調べて書いたけど、適当に仮定していい範囲だと思っている
     */
    public int coinChangeTLE(int[] coins, int amount) {
        if (amount == 0) return 0;
        coins = Arrays.stream(coins)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(Integer::intValue)
                .toArray();
        Queue<int[]> totalAndCount = new LinkedList<>();

        for (int coin : coins) {
            if (coin == amount) {
                return 1;
            }
            if (coin < amount) {
                totalAndCount.offer(new int[] {coin, 1});
            }
        }

        while (!totalAndCount.isEmpty()) {
            int[] currentTotalAndCount = totalAndCount.poll();
            int total = currentTotalAndCount[0];
            int count = currentTotalAndCount[1] + 1;
            for (int coin : coins) {
                if (total + coin == amount) {
                    return count;
                }
                if (total + coin > amount) {
                    continue;
                }
                totalAndCount.offer(new int[] {total + coin, count});
            }
        }
        return -1;
    }
    // --------------------↑WA
}
