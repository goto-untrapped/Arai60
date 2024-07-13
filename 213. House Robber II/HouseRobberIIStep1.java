public class HouseRobberIIStep1 {
    /*
     * 解き直し 30min
     * 時間計算量：O(n)
     * 空間計算量：O(n)
     */
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]);
        }

        int[] fromFirstSubSums = new int[nums.length];
        fromFirstSubSums[0] = nums[0];
        fromFirstSubSums[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < fromFirstSubSums.length - 1; i++) {
            fromFirstSubSums[i] = Math.max(fromFirstSubSums[i - 2] + nums[i], fromFirstSubSums[i - 1]);
        }

        int[] fromSecondSubSums = new int[nums.length];
        fromSecondSubSums[1] = nums[1];
        fromSecondSubSums[2] = Math.max(nums[1], nums[2]);
        for (int i = 3; i < fromSecondSubSums.length; i++) {
            fromSecondSubSums[i] = Math.max(fromSecondSubSums[i - 2] + nums[i], fromSecondSubSums[i - 1]);
        }

        return Math.max(fromFirstSubSums[fromFirstSubSums.length - 2], fromSecondSubSums[fromSecondSubSums.length - 1]);
    }
}
