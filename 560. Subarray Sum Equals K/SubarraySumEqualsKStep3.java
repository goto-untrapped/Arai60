public class SubarraySumEqualsKStep3 {
    // 3min
    public int subarraySum(int[] nums, int k) {
        int equalKCount = 0;
        HashMap<Integer, Integer> prefixSumToCount = new HashMap<>();
        prefixSumToCount.put(0, 1);
        int prefixSum = 0;
        for (int index = 0; index < nums.length; index++) {
            prefixSum += nums[index];
            if (prefixSumToCount.containsKey(prefixSum - k)) {
                equalKCount += prefixSumToCount.get(prefixSum - k);
            }
            prefixSumToCount.put(prefixSum, prefixSumToCount.getOrDefault(prefixSum, 0) + 1);
        }
        return equalKCount;
    }
}
