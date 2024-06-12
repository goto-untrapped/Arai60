public class MoveZeroesStep4 {
    // 1min
    public void moveZeroes(int[] nums) {
        int nextIndex = 0;
        for (int index = 0; index < nums.length; index++) {
            if (nums[index] == 0) {
                continue;
            }
            nums[nextIndex] = nums[index];
            nextIndex++;
        }

        for (int index = nextIndex; index < nums.length; index++) {
            nums[index] = 0;
        }
    }
}
