public class TopKFrequentElementsStep3 {
    /*
     * 11m15s
     * 時間計算量:O(nlogk)
     * 空間計算量:O(n)
     */
    class Solution {
        record NumAndCount(int num, int count) {}

        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> numToCount = new HashMap<>();
            for (int num : nums) {
                int lastCount = numToCount.getOrDefault(num, 0);
                numToCount.put(num, lastCount + 1);
            }

            PriorityQueue<NumAndCount> numAndCounts = new PriorityQueue<>((a, b) -> a.count() - b.count());
            for (int num : numToCount.keySet()) {
                numAndCounts.offer(new NumAndCount(num, numToCount.get(num)));
                while (numAndCounts.size() > k) {
                    numAndCounts.poll(); 
                }
            }

            int[] topKFrequentNums = new int[numAndCounts.size()];
            for (int i = 0; i < topKFrequentNums.length && numAndCounts.size() > 0; i++) {
                topKFrequentNums[i] = numAndCounts.poll().num();
            }
            return topKFrequentNums;
        }
    }
}
