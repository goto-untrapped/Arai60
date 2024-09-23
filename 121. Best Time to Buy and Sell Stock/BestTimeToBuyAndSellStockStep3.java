public class BestTimeToBuyAndSellStockStep3 {
    // 3min
    class Solution {
        public int maxProfit(int[] prices) {
            if (prices.length == 0) {
                return 0;
            }
            int minBuyPrice = prices[0];
            int maxProfit = 0;
            for (int i = 1; i < prices.length; i++) {
                minBuyPrice = Math.min(minBuyPrice, prices[i]);
                maxProfit = Math.max(maxProfit, prices[i] - minBuyPrice);
            }
            return maxProfit;
        }
    }
}
