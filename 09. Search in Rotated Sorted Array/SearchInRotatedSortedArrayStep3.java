public class SearchInRotatedSortedArrayStep3 {
    
    // 一番安心して書けた閉区間
    // leftとrightが重なる所まで見れるから、漏れがなさそう。見る範囲が重複？しているかもしれないけど。
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int middle = (left + right) / 2;

            if (nums[middle] == target) {
                return middle;
            }

            if (nums[0] <= nums[middle]) {
                if (nums[0] <= target && target < nums[middle]) {
                    // nums[middle]がtargetであればreturnしているから、rightはmiddle-1から見ればいい、という理解でよいのかが分からない
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            } else {
                if (nums[middle] < target && target <= nums[right]) {
                    // nums[middle]がtargetであればreturnしているから、leftはmiddle+1から見ればいい、という理解でよいのかが分からない
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }
        }

        return -1;
    }
    
}
