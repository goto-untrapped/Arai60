public class SearchInsertPositionStep3 {
    
    // 一番漏れが少なく、安心して書けるものだったため、採用
    // 前からふつうに見ていって、最後まで見てだめなら最後に置けばいいね と思った
    public int searchInsert(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }

        return nums.length;
    }

}