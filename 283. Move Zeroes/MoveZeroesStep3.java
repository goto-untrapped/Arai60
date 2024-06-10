public class MoveZeroesStep3 {
    // 1m20s
    public void moveZeroes(int[] nums) {
        int lastNonZeroIndex = -1;
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] == 0) {
                continue;
            }
            lastNonZeroIndex++;
            nums[lastNonZeroIndex] = nums[index];
        }

        for (int index = lastNonZeroIndex + 1; index < nums.length; index++) {
            nums[index] = 0;
        }
    }
}
