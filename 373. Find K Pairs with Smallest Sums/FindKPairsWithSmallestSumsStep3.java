public class FindKPairsWithSmallestSumsStep3 {
    // 10m30s
    class Solution {
        record SumAndIndex(int sum, int index1, int index2) {}

        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<List<Integer>> kSmallestNumPairs = new ArrayList<>();
            PriorityQueue<SumAndIndex> smallerSumIndexes = new PriorityQueue<>(
                (a, b) -> (a.sum() - b.sum())
            );
            smallerSumIndexes.offer(new SumAndIndex(nums1[0] + nums2[0], 0, 0));
            Set<SumAndIndex> visitedIndexes = new HashSet<>();
            visitedIndexes.add(new SumAndIndex(nums1[0] + nums2[0], 0, 0));
            int numPairs = 0;
            while (numPairs < k && !smallerSumIndexes.isEmpty()) {
                SumAndIndex sumAndIndex = smallerSumIndexes.poll();
                int index1 = sumAndIndex.index1();
                int index2 = sumAndIndex.index2();

                List<Integer> newNumPair = new ArrayList<>();
                Collections.addAll(newNumPair, nums1[index1], nums2[index2]);
                kSmallestNumPairs.add(newNumPair);
                numPairs++;

                if (index1 + 1 < nums1.length) {
                    SumAndIndex nextIndex1SumAndIndex = new SumAndIndex(nums1[index1 + 1] + nums2[index2], index1 + 1, index2);
                    if (!visitedIndexes.contains(nextIndex1SumAndIndex)) {
                        smallerSumIndexes.offer(nextIndex1SumAndIndex);
                        visitedIndexes.add(nextIndex1SumAndIndex);
                    }
                }
                if (index2 + 1 < nums2.length) {
                    SumAndIndex nextIndex2SumAndIndex = new SumAndIndex(nums1[index1] + nums2[index2 + 1], index1, index2 + 1);
                    if (!visitedIndexes.contains(nextIndex2SumAndIndex)) {
                        smallerSumIndexes.offer(nextIndex2SumAndIndex);
                        visitedIndexes.add(nextIndex2SumAndIndex);
                    }
                }
            }
            return kSmallestNumPairs;
        }
    }
    /*
     * ・これも書けるか怪しい。
     * ・同じrecordを使いまわしたけど思っていたほど読みやすくなかった。
     * 　・ネストが深い、一文が長くなってしまう。
     * ・最初にvisitedIndexesに追加した[0,0]は入れなくてもいいけど、合わせた方が違和感は少なそうと思った。
     */
}
