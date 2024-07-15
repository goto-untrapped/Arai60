public class SearchInRotatedSortedArrayStep4 {
    
    // 何となくこの形で書けただけで、細かい動きまで把握してではなかった
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int middle = left + (right - left) / 2;

            if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }

        return left;
    }
    
}
