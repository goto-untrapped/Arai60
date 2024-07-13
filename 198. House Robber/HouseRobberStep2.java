public class HouseRobberStep2 {
    
    public int rob2_1(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int[] maxSubSums = new int[nums.length];
        maxSubSums[0] = nums[0];
        maxSubSums[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            maxSubSums[i] = Math.max(maxSubSums[i - 2] + nums[i], maxSubSums[i - 1]);
        }
        return maxSubSums[maxSubSums.length - 1];
    }
    
    public int rob2_2(int[] nums) {
        int[] maxSubSums = new int[nums.length];
        maxSubSums[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int subSum = 0;
            for (int j = 0; j < i - 1; j++) {
                subSum = Math.max(subSum, maxSubSums[j]);
            }
            maxSubSums[i] = subSum + nums[i];
        }

        int maxSum = 0;
        for (int subSum : maxSubSums) {
            if (maxSum < subSum) {
                maxSum = subSum;
            }
        }
        return maxSum;
    }
    
    public int rob2_3(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }
        int twoBeforeMaxSum = nums[0];
        int oneBeforeMaxSum = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            int temp = oneBeforeMaxSum;
            oneBeforeMaxSum = Math.max(twoBeforeMaxSum + nums[i], oneBeforeMaxSum);
            twoBeforeMaxSum = temp;
        }
        return oneBeforeMaxSum;
    }
}
/*
 * https://github.com/hayashi-ay/leetcode/pull/48
 *   https://discord.com/channels/1084280443945353267/1200089668901937312/1217116484203970701
 * https://github.com/shining-ai/leetcode/pull/35
 * https://github.com/sakupan102/arai60-practice/pull/36
 * https://github.com/YukiMichishita/LeetCode/pull/16
 * https://github.com/fhiyo/leetcode/pull/36
 */
