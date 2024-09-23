public class BestTimeToBuyAndSellStockIIStep2 {
    
    // 範囲を大きくとっても一番小さい範囲の和以下にしかならないため、
    // ふつうに利益を全部足していくことと同じ意味になる。
    class Solution2_1 {
        public int maxProfit(int[] prices) {
            int maxProfit = 0;
            for (int i = 0; i < prices.length - 1; i++) {
                if (prices[i] < prices[i + 1]) {
                    maxProfit += prices[i + 1] - prices[i];
                }
            }
            return maxProfit;
        }
    }
    /*
     * ・maxProfit、変数名と関数名が同じままだった。変数名はprofitでいいかな。
     */
    
    
    // 意味合いをもっと入れる
    class Solution2_2 {
        public int maxProfit(int[] prices) {
            int profit = 0;
            for (int i = 1; i < prices.length; i++) {
                int difference = prices[i] - prices[i - 1];
                if (difference > 0) {
                    profit += difference;
                }
            }
            return profit;
        }
    }
    
    
    // bottom up dp 株を持っている状態と持っていない状態で一番いい利益を引き継いで最大値を出す
    class Solution2_3 {
        public int maxProfit(int[] prices) {
            // 1日目の最大収支
            int profitWithStock = -prices[0];
            int profitWithoutStock = 0;
            for (int i = 1; i < prices.length; i++) {
                profitWithStock = Math.max(profitWithStock, profitWithoutStock - prices[i]);
                profitWithoutStock = Math.max(profitWithoutStock, profitWithStock + prices[i]);
            }
            return profitWithoutStock;
        }
    }
    /*
     * ・それぞれの日を株を持っているか持っていないかで見た時、前の日の結果の影響を受けるため、
     * 前の結果までで一番いい利益を出していれば、今日も一番いい利益が出るように計算できる。
     * 最後の日に株を持っていない時が一番利益が出ている時。
     * 　・今日、株を持っている状態になるには、株を持っていた昨日から引き続き今日も持つか、株を持っていない昨日から今日は買い込むかのどちらか。
     * 　・今日、株を持っていない状態になるには、株を持っていない昨日から引き続き今日も持たないか、株を持っていた昨日からその株を売るかのどちらか。
     * ・すごい。けど難しい。
     */
    
    
    // 公式の解答を使う top down dp 
    class Solution2_4 {
        public int[] memo;

        public int maxProfit(int[] prices) {
            memo = new int[prices.length];
            return maxProfitHelper(prices, 0);
        }

        private int maxProfitHelper(int[] prices, int startIndex) {
            if (startIndex >= prices.length) {
                return 0;
            }
            if (memo[startIndex] > 0) {
                return memo[startIndex];
            }
            int maxProfit = 0;
            for (int i = startIndex; i < prices.length; i++) {
                int maxProfitAfterStartIndex = 0;
                for (int j = i + 1; j < prices.length; j++) {
                    int difference = prices[j] - prices[i];
                    if (difference <= 0) {
                        continue;
                    }
                    int profit = maxProfitHelper(prices, j + 1) + difference;
                    maxProfitAfterStartIndex = Math.max(maxProfitAfterStartIndex, profit);
                }
                maxProfit = Math.max(maxProfit, maxProfitAfterStartIndex);
                memo[startIndex] = Math.max(memo[startIndex], maxProfit);
            }
            return maxProfit;
        }
    }
    /*
     * ・memoを入れない時の時間計算量はO(2^n)だと思っている。
     * 単調増加の配列でデバッグをしたら、入るか入らないかの2通りで動いてると思った。
     */
    
}
/*
 * https://discord.com/channels/1084280443945353267/1210494002277908491/1210521728699076628
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1220699663301214208
 *   https://github.com/hayashi-ay/leetcode/pull/56
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1224003297145389096
 *   https://github.com/shining-ai/leetcode/pull/38
 * https://github.com/sakupan102/arai60-practice/pull/39
 * https://github.com/fhiyo/leetcode/pull/39
 * https://github.com/rossy0213/leetcode/pull/25
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/53
 * 
hayashiさん Step1
何をやっているのか理解したい(1通り)
　コメント
　https://github.com/fhiyo/leetcode/pull/39/files

hyodoさん Step3
上がっていれば、差額が利益になる、とするために
if と加算の両方で計算しているのなら、
1つの変数で置いた方が分かりやすいと理解(1通り)
 */
