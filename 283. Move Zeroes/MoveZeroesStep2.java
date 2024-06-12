public class MoveZeroesStep2 {
    /*
     * https://github.com/SuperHotDogCat/coding-interview/pull/25
     * https://github.com/shining-ai/leetcode/pull/54#discussion_r1572528718
     * https://github.com/shining-ai/leetcode/pull/54
     * https://github.com/hayashi-ay/leetcode/pull/58
     */
    // 0じゃない数字を前から詰めればいい O(n)/O(1)
    public void moveZeroes2_1(int[] nums) {
        int nonZeroIndex = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[nonZeroIndex] = num;
                nonZeroIndex++;
            }
        }

        int zeroIndex = nonZeroIndex;
        while (zeroIndex < nums.length) {
            nums[zeroIndex] = 0;
            zeroIndex++;
        }
    }
    
    // swapする O(n)/O(1)
    public void moveZeroes2_2(int[] nums) {
        int nonZeroLength = 0;
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] == 0) {
                continue;
            }
            int num = nums[index];
            nums[index] = nums[nonZeroLength];
            nums[nonZeroLength] = num;
            nonZeroLength++;
        }
    }
}
