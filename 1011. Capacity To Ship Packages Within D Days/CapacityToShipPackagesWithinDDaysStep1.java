public class CapacityToShipPackagesWithinDDaysStep1 {
    /*
     * 10分考えて分からず。（この後解答を見た）
     * 思ってたこと
     * ・要素順にしか載せられないことがポイントそう
     * ・最大搭載量を決めないと判定できないけど、最大搭載量も更新する必要があるし、どうやるんだ？
     * ・１回daysで要素数を割って均等に振り分けてから微調整するとか？
     *   ・でも増やしたり減らしたりをdays分やるって頭が回らないな。
     * ・累積和とか？使えそうな考え方が思いつかない。
     * 
     * 30minかけて解答の考え方を読んだ後
     * ・少なくとも、最大搭載量は積み荷のうち、一番大きい重量でないといけない
     * ・なので、一番大きい積み荷を最大搭載量として仮決めして、days日以内に全部積めるかを見ればいい
     * ・積めなかったら、最大搭載量+1で積めるか確認すればいい。を繰り返す。
     * ・ただし、それだと1日で(500, 500, ...)積めるか確認しようとすると、
     * これは最大搭載量が(n * 500)になるけど、そこに至るまでに 
     * (n * 500 - 500 + 1)ループ × 1ループあたり約n回見る、として、O(n^2 * 500)になってTLE。
     * 効率よく最大搭載量の仮決め値を決めるために、二分探索が使える。
     * ⇒ 自分の思考がなんというか、粗い。できなさそうの中でできることを探さずにすぐに判断をしている感じ。
     * というかできなさそうが先に来てる。それは色々考えてからやっと分かることなのに。 
     */
    /*
     * 35min
     * 時間計算量：O(nlogn)
     * 空間計算量：O(n)
     */
    class Solution {
        public int shipWithinDays(int[] weights, int days) {
            // 左端がmaxWeights, 右端がtotalWeights になるような配列をつくる
            int maxWeight = weights[0];
            int totalWeight = weights[0];
            for (int i = 1; i < weights.length; i++) {
                maxWeight = Math.max(maxWeight, weights[i]);
                totalWeight += weights[i];
            }
            int[] candidateWeights = new int[totalWeight - maxWeight + 1];
            int weight = maxWeight;
            for (int i = 0; i < candidateWeights.length; i++) {
                candidateWeights[i] = weight;
                weight++;
            }

            // 区間の値が1つになるまで繰り返す
            int left = 0;
            int right = candidateWeights.length;
            while (left < right) {
                // binarySearch で、最大搭載量を返す
                int mid = (left + right) / 2;
                if (canShipWithinDays(weights, days, candidateWeights[mid])) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return candidateWeights[left];       
        }

        // ある最大搭載量で、days日内に運べるか、確認して true/false を返す
        private boolean canShipWithinDays(int[] weights, int days, int maxWeight) {
            int pastDays = 1;
            int weight = 0;
            for (int index = 0; index < weights.length; index++) {
                if (pastDays > days) {
                    return false;
                }
                weight += weights[index];
                if (weight > maxWeight) {
                    pastDays++;
                    weight = weights[index];
                }
            }
            return pastDays <= days;
        }
    }
    
    /*
     * 解答のプログラムを読んだ後に思ったこと
     * ・candidateWeights いらなかった。調べたい搭載量を配列で持たず、mid をそのまま搭載量とすればいい。空間計算量もO(1)になる。
     * ・canShipWithinDays() はindexではなくiでよくて、もっと言うなら for (int weight : weights) でよかった。
     * はじめ、indexをループ内で直接書き換えるイメージだったため、役割があるということでこの名前にしたことの名残だけ残ってしまった。
     * ・canShipWithinDays() で積んだ荷重を load という変数名にするの分かりやすい。weight じゃ足りないと感じたが、もっといい名前が
     * 思いつかなかった。同じように、left, right, mid も maxLoad, totalLoad, candidateLoad にした方が分かりやすそう。
     * そうなると、maxWeight、totalWeight も xxxLoad にした方がいい。
     * ・pastDaysは、daysPastの方が英単語として（なのでプログラムとしても）自然なのだろうか。このあたりの英語の感覚がない。
     * ・最大搭載量というより最大積載量だった。
     */
}
