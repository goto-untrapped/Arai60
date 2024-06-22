public class MaximumSubArrayStep3 {
    // 2min
    public int maxSubArray(int[] nums) {
        int[] maxSubArraySums = new int[nums.length];
        maxSubArraySums[0] = nums[0];
        for (int i = 1; i < maxSubArraySums.length; i++) {
            maxSubArraySums[i] = Math.max(maxSubArraySums[i - 1] + nums[i], nums[i]);
        }

        int maxSubArraySum = maxSubArraySums[0];
        for (int maybeMaxSubArraySum : maxSubArraySums) {
            maxSubArraySum = Math.max(maxSubArraySum, maybeMaxSubArraySum);
        }
        return maxSubArraySum;
    }
}
