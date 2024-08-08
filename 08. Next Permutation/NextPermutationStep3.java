public class NextPermutationStep3 {
    
    public void nextPermutation(int[] nums) {
        int swapIndex = nums.length - 2;
        while (swapIndex >= 0 && nums[swapIndex] >= nums[swapIndex + 1]) {
            swapIndex--;
        }

        if (swapIndex >= 0) {
            int biggerThanIndex = nums.length - 1;
            while (nums[biggerThanIndex] <= nums[swapIndex]) {
                biggerThanIndex--;
            }
            swap(nums, swapIndex, biggerThanIndex);
        }
        reverse(nums, swapIndex + 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int startIndex) {
        int i = startIndex;
        int j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }
    
}
