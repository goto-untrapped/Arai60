public class HouseRobberStep3 {
    // 2min / 3m30s / 2m30s
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int[] maxSubSums = new int[nums.length];
        maxSubSums[0] = nums[0];
        maxSubSums[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < maxSubSums.length; i++) {
            maxSubSums[i] = Math.max(maxSubSums[i - 2] + nums[i], maxSubSums[i - 1]);
        }
        return maxSubSums[maxSubSums.length - 1];
    }
}
