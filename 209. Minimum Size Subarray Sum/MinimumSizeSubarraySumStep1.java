public class MinimumSizeSubarraySumStep1 {
    /*
     * 15min 解答見た
     * if (right == nums.length) {} を入れるために1回デバッグをした。
     * 時間計算量：O(n)
     * 空間計算量：O(1)
     */
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int right = 0;
        int sum = nums[left];
        int minSize = Integer.MAX_VALUE;
        while (right < nums.length) {
            if (sum < target) {
                right++;
                if (right == nums.length) {
                    break;
                }
                sum += nums[right];
                continue;
            }
            minSize = Math.min(minSize, right - left + 1);
            sum -= nums[left];
            left++;
        }
        if (minSize == Integer.MAX_VALUE) {
            return 0;
        }
        return minSize;
    }
    
    /*
     * 25minほどでWAして手が止まる。
     * WA：target = 11, nums = [1,2,3,4,5]
     * grater than の場合すっかり忘れていた。
     * 解法を考えている内に、要件を忘れてしまった・・・。
     * もう少しゆっくり問題文を読む、理解する。
     */
    public int minSubArrayLenWA(int target, int[] nums) {
        HashMap<Integer, Integer> indexToPrefixSum = new HashMap<>();
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            indexToPrefixSum.put(sum, i);
        }

        int minSize = Integer.MAX_VALUE;
        for (var pair : indexToPrefixSum.entrySet()) {
            if (indexToPrefixSum.containsKey(target + pair.getKey())) {
                minSize = Math.min( minSize, 
                                    indexToPrefixSum.get(target + pair.getKey()) - indexToPrefixSum.get(pair.getKey()));
            }
        }
        if (minSize == Integer.MAX_VALUE) {
            return 0;
        }
        return minSize;
    }
}
