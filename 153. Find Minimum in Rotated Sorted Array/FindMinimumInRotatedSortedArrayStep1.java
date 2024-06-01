public class FindMinimumInRotatedSortedArrayStep1 {
    /*
     * 9m30s
     * 2回くらい解いてる
     * 前の解き方を大まかに覚えていた。3つの値で大きい⇒小さいとなる時に判定する、というロジックを使った
     * こんなに場合分けしていなかった記憶があるけど、
     * 3個ないとOutOfIndexErrorする、と思って書いた
     * 3個見てるから気持ち早めにループが打ち切れるように半開区間[)で書いた
     */ 
    public int findMin(int[] nums) {
        if (nums[0] < nums[nums.length - 1]) return nums[0];
        if (nums.length == 1) return nums[0];
        if (nums.length == 2 && nums[0] < nums[1]) return nums[0];
        if (nums.length == 2 && nums[1] < nums[0]) return nums[1];

        int left = 0;
        int right = nums.length;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (nums[middle - 1] > nums[middle]) {
                return nums[middle];
            }
            if (nums[middle] > nums[middle + 1]) {
                return nums[middle + 1];
            }

            if (nums[0] < nums[middle]) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return -1;
    }
}
