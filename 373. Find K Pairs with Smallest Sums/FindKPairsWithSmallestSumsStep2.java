public class FindKPairsWithSmallestSumsStep2 {
    
    // 1方向追加とみる
    class Solution2_1 {
        record SumAndIndex(int sum, int index1, int index2) {}

        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<List<Integer>> kSmallestNumPairs = new ArrayList<>();
            PriorityQueue<SumAndIndex> smallerSumIndexes = new PriorityQueue<>(
                (a, b) -> (a.sum() - b.sum())
            );
            for (int i = 0; i < nums1.length; i++) {
                smallerSumIndexes.offer(new SumAndIndex(nums1[i] + nums2[0], i, 0));
            }
            int numPairs = 0;
            while (numPairs < k && !smallerSumIndexes.isEmpty()) {
                SumAndIndex sumAndIndex = smallerSumIndexes.poll();
                int index1 = sumAndIndex.index1();
                int index2 = sumAndIndex.index2();

                List<Integer> newNumPair = new ArrayList<>();
                Collections.addAll(newNumPair, nums1[index1], nums2[index2]);
                kSmallestNumPairs.add(newNumPair);
                numPairs++;

                if (index2 + 1 < nums2.length) {
                    smallerSumIndexes.offer(new SumAndIndex(nums1[index1] + nums2[index2 + 1], index1, index2 + 1));
                }
            }
            return kSmallestNumPairs;
        }
    }
    
    
    /*
     * 1方向のインデックスが最小の次の場合のみ追加する
     * Step1は次の行き先がまだ行ってない場所だったら追加する
     * これは行き先が行くべき場所だったら追加する
     * こっちの方が絞れそうはする
     */
    class Solution {
        record SumAndIndex(int sum, int index1, int index2) {}

        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<List<Integer>> kSmallestNumPairs = new ArrayList<>();
            PriorityQueue<SumAndIndex> smallerSumIndexes = new PriorityQueue<>(
                (a, b) -> (a.sum() - b.sum())
            );
            smallerSumIndexes.offer(new SumAndIndex(nums1[0] + nums2[0], 0, 0));
            int[] nums1NextIndex = new int[nums2.length];
            int[] nums2NextIndex = new int[nums1.length];
            int numPairs = 0;
            while (numPairs < k && !smallerSumIndexes.isEmpty()) {
                SumAndIndex sumAndIndex = smallerSumIndexes.poll();
                int index1 = sumAndIndex.index1();
                int index2 = sumAndIndex.index2();

                List<Integer> newNumPair = new ArrayList<>();
                Collections.addAll(newNumPair, nums1[index1], nums2[index2]);
                kSmallestNumPairs.add(newNumPair);
                numPairs++;

                nums1NextIndex[index2]++;
                nums2NextIndex[index1]++;
                if (index1 + 1 < nums1.length && index2 == nums2NextIndex[index1 + 1]) {
                    smallerSumIndexes.offer(new SumAndIndex(nums1[index1 + 1] + nums2[index2], index1 + 1, index2));
                }
                if (index2 + 1 < nums2.length && index1 == nums1NextIndex[index2 + 1]) {
                    smallerSumIndexes.offer(new SumAndIndex(nums1[index1] + nums2[index2 + 1], index1, index2 + 1));
                }
            }
            return kSmallestNumPairs;
        }
    }
    /*
     * ・難しい
     * https://github.com/TORUS0818/leetcode/pull/12
     */
    
}
/*
 * （拾い読みをさせていただいた）
https://discord.com/channels/1084280443945353267/1183683738635346001/1187118473462173776
  手でやるとしても毎回足したりしないよね。
https://discord.com/channels/1084280443945353267/1192736784354918470/1220012240086892604
　https://github.com/YukiMichishita/LeetCode/pull/4
https://discord.com/channels/1084280443945353267/1200089668901937312/1222557634092073051
　https://github.com/hayashi-ay/leetcode/pull/66
https://discord.com/channels/1084280443945353267/1227073733844406343/1231546662682365992
　https://github.com/sakupan102/arai60-practice/pull/11
https://discord.com/channels/1084280443945353267/1196472827457589338/1245390479172833342
　https://github.com/Mike0121/LeetCode/pull/20
https://github.com/fhiyo/leetcode/pull/13
　https://discord.com/channels/1084280443945353267/1235829049511903273/1246118347863621652
https://github.com/TORUS0818/leetcode/pull/12
https://github.com/Ryotaro25/leetcode_first60/pull/11
https://github.com/kazukiii/leetcode/pull/11
https://github.com/Yoshiki-Iwasa/Arai60/pull/9
　Odaさんのコメントが一番分かりやすい気がする
https://github.com/seal-azarashi/leetcode/pull/10
https://github.com/sendahuang14/leetcode/pull/9
https://github.com/nittoco/leetcode/pull/33
　https://discord.com/channels/1084280443945353267/1226508154833993788/1270734186713710614
https://github.com/ryoooooory/LeetCode/pull/17
https://discord.com/channels/1084280443945353267/1206101582861697046/1283059663981645846
　https://github.com/colorbox/leetcode/pull/25
 */
