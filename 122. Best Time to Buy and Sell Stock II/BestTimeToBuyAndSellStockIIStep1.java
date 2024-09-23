public class BestTimeToBuyAndSellStockIIStep1 {
    /*
     * 15min考えたけど実装方針が立たなかった。
     * 時間計算量:O(n)
     * 空間計算量:O(1)
     */
    class Solution {
        public int maxProfit(int[] prices) {
            int index = 0;
            int maxProfit = 0;
            while (index < prices.length - 1) {
                while (index + 1 < prices.length && prices[index] >= prices[index + 1]) {
                    index++;
                }
                int minBuyPrice = prices[index];
                while (index + 1 < prices.length && prices[index] <= prices[index + 1]) {
                    index++;
                }
                int maxSellPrice = prices[index];
                maxProfit += maxSellPrice - minBuyPrice;
            }
            return maxProfit;
        }
    }
    /*
     * ・考えたこと
     * 　・インデックスごとに見れば、そのインデックスまでに最大利益になる区間は分かりそう。
     * 　　・でも[7 1 8 6 12]のような場合に、1-12ではなく1-8,6-12で利益を出すにはどうしたらいいのか。
     * 　・総当たりは10^8くらいか。早期リターンできる条件があれば、間に合うかもしれない。
     * 
     * ・解答を見た後
     * 　　・実はこの2パターンを区別できる考え方ができて、それを使えばプログラムに落とし込める。
     * 　　　・なんだろう、観察眼が足りないのかな。つまりはどういうことか、というか。
     * 
     * ・maxProfit、関数名と変数名が同じでよくなかったな。考える時にややこしくなりそうなので。
     */
}
