public class BestTimeToBuyAndSellStockIIStep3 {
    // 10min
    class Solution {
        public int maxProfit(int[] prices) {
            int profit = 0;
            int i = 0;
            while (i < prices.length - 1) {
                while (i + 1 < prices.length && prices[i] >= prices[i + 1]) {
                    i++;
                }
                int minBuyPrice = prices[i];
                while (i + 1 < prices.length && prices[i] <= prices[i + 1]) {
                    i++;
                }
                int maxSellPrice = prices[i];
                profit += maxSellPrice - minBuyPrice;
            }
            return profit;
        }
    }
    /*
     * ・minBuyPriceとmaxSellPriceという名前が気になった。下がったまま最後に到達した時、
     * 実は最後のインデックスを足し引きしていて、結果が0になるから出力に影響はないけど、最小買値でも最大売値でもない。
     * でも一般的なケースは表そうとしているので、そこまでこだわらなくてもいいのかなと思った。
     */
}
