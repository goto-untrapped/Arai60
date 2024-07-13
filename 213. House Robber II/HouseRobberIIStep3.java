public class HouseRobberIIStep3 {
    // 5m30s / 3m10s / 3m40s
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int fromFirstMaxSubSum = calculateMaxSubSum(nums, 0, nums.length - 1);
        int fromSecondMaxSubSum = calculateMaxSubSum(nums, 1, nums.length);
        return Math.max(fromFirstMaxSubSum, fromSecondMaxSubSum);
    }

    private int calculateMaxSubSum(int[] nums, int start, int end) {
        int[] maxSubSums = Arrays.copyOfRange(nums, start, end);
        maxSubSums[0] = nums[start];
        maxSubSums[1] = Math.max(nums[start], nums[start + 1]);
        for (int i = 2; i < maxSubSums.length; i++) {
            maxSubSums[i] = Math.max(maxSubSums[i - 2] + nums[start + i] , maxSubSums[i - 1]);
        }
        return maxSubSums[maxSubSums.length - 1];
    }
}
