public class BestTimeToBuyAndSellStockStep2 {
    
    // 総当たり
    class Solution2_1_TLE {
        public int maxProfit(int[] prices) {
            int maxProfit = 0;
            for (int i = 0; i < prices.length; i++) {
                for (int j = i + 1; j < prices.length; j++) {
                    maxProfit = Math.max(maxProfit, prices[j] - prices[i]);
                }
            }
            return maxProfit;
        }
    }
    
    // 最小はそのままでその日の利益を出して、一緒に最大利益も出す
    class Solution2_2 {
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
    /*
     * ・最後の最小値が来ても最小-最小でmaxが変わらないのか。
     * 　・最小値と最大値を出しておけるという発想が違うんだろうな。
     * 　結局その日までの最小値と、その日の最大値の組み合わせで見ないといけない。
     */
    
    
    // 最大値を更新してその時の値を引いていくこともできる
    // https://github.com/fhiyo/leetcode/pull/38/files
    class Solution2_3 {
        public int maxProfit(int[] prices) {
            int maxPrice = prices[prices.length - 1];
            int maxProfit = 0;
            for (int i = prices.length - 1; i >= 0; i--) {
                maxPrice = Math.max(maxPrice, prices[i]);
                maxProfit = Math.max(maxProfit, maxPrice - prices[i]);
            }
            return maxProfit;
        }
    }
    
    // 日ごとに最大、最小値を持ち回って、日ごとに計算する
    // https://github.com/colorbox/leetcode/pull/6/files
    class Solution2_4 {
        public int maxProfit(int[] prices) {
            if (prices.length == 0) {
                return 0;
            }
            int[] minPrices = new int[prices.length];
            int minPrice = prices[0];
            for (int i = 0; i < prices.length; i++) {
                minPrice = Math.min(minPrice, prices[i]);
                minPrices[i] = minPrice;
            }

            int[] maxPrices = new int[prices.length];
            int maxPrice = prices[prices.length - 1];
            for (int i = prices.length - 1; i >= 0; i--) {
                maxPrice = Math.max(maxPrice, prices[i]);
                maxPrices[i] = maxPrice;
            }

            int maxProfit = 0;
            for (int i = 0; i < prices.length; i++) {
                maxProfit = Math.max(maxProfit, maxPrices[i] - minPrices[i]);
            }
            return maxProfit;
        }
    }
}
/*
 * https://discord.com/channels/1084280443945353267/1210494002277908491/1210521728699076628
 * https://discord.com/channels/1084280443945353267/1206101582861697046/1218990153306079327
 *   https://github.com/colorbox/leetcode/pull/6
 * https://github.com/Kitaken0107/GrindEasy/pull/7
 *   https://discord.com/channels/1084280443945353267/1192728121644945439/1218818241636339722
 * https://github.com/hayashi-ay/leetcode/pull/52
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1224003297145389096
 *   https://github.com/shining-ai/leetcode/pull/37
 * https://discord.com/channels/1084280443945353267/1183683738635346001/1239989564135706726
 *   https://github.com/kzhra/Grind41/pull/4
 * https://github.com/erutako/leetcode/pull/3
 * https://github.com/sakupan102/arai60-practice/pull/38
 * https://github.com/fhiyo/leetcode/pull/38
 * https://github.com/rossy0213/leetcode/pull/22
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/52
 * https://github.com/rihib/leetcode/pull/24
 * 
colorboxさん Step1
前から最小を持ち回って、後ろから最大を持ち回った差はそれぞれの最大利益なので、このうちの最大が一番大きい利益になる(1通り)

kitakenさん Step1
総当たりもふつうに考えられる(1通り)

Odaさん コメント
https://discord.com/channels/1084280443945353267/1192728121644945439/1218818241636339722
その日までの最低価格を出したら、仮にその日に売った時の利益を出して、それから最大利益を考えることもできる(1通り)
その日に売ったらどれぐらいの利益化を考えてそれをそれぞれの日にやるというのは、ふつうに株価見るとしたら一番やりそうなやり方かも

hayashiさん Step1
その日の差額を出すのテンプレ

hyodoさん Step2
最大値を更新してその時の値を引いていくこともできる(1通り)
 */
