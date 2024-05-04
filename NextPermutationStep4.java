public class NextPermutationStep4 {
    
    /*
     * 修正点
     * ・全体的な処理の流れを、見る負荷が減るように修正
     * ・変数名を修正
     * ・関数名を修正
     * ・汎用的な関数として使われることを考え、エラーチェックを追加
     */
    public void nextPermutation(int[] nums) {
        int leftSwappedIndex = nums.length - 2;
        while (leftSwappedIndex >= 0 && nums[leftSwappedIndex] >= nums[leftSwappedIndex + 1]) {
            leftSwappedIndex--;
        }
        if (leftSwappedIndex == -1) {
            reverseTail(nums, 0);
            return;
        }

        int rightSwappedIndex = nums.length - 1;
        while (nums[rightSwappedIndex] <= nums[leftSwappedIndex]) {
            rightSwappedIndex--;
        }
        swap(nums, leftSwappedIndex, rightSwappedIndex);
        reverseTail(nums, leftSwappedIndex + 1);
    }

    public static void reverseTail(int[] nums, int startIndex) {
        if (nums.length == 0 || startIndex <= -1 || startIndex >= nums.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        int left = startIndex;
        int right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    public static void swap(int[] nums, int i, int j) {
        if (nums.length == 0 || i <= -1 || i >= nums.length
            || j <= -1 || j >= nums.length ) {
            throw new ArrayIndexOutOfBoundsException();
        }

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
}
