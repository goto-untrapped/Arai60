public class BestTimeToBuyAndSellStockStep1 {
    /*
     * 累計23min
     * 買った後のことまで考える必要があった
     * 時間計算量:O(n)
     * 空間計算量:O(1)
     */
    class Solution {
        public int maxProfit(int[] prices) {
            int minPrice = Integer.MAX_VALUE;
            int maxProfit = 0;
            for (int i = 0; i < prices.length; i++) {
                if (prices[i] < minPrice) {
                    minPrice = prices[i];
                    continue;
                }
                if (prices[i] - minPrice > maxProfit) {
                    maxProfit = prices[i] - minPrice;
                    continue;
                }
            }
            return maxProfit;
        }
    }
    
    /*
     * 10m20s
     * [2,4,1]でWA Output:0, Expected: 2
     */
    class Solution_WA {
        public int maxProfit(int[] prices) {
            int minBuyIndex = 0;
            int maxSellIndex = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] < prices[minBuyIndex]) {
                    minBuyIndex = i;
                    maxSellIndex = i;
                    continue;
                }
                if (prices[i] > prices[maxSellIndex]) {
                    maxSellIndex = i;
                    continue;
                }
            }
            return prices[maxSellIndex] - prices[minBuyIndex];
        }
    }
}
