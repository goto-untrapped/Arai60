public class SearchInRotatedSortedArrayStep1 {
    
    // 解き直し
    // 15分くらいかかったうえ、Wrong Answer: 下記ケースにて
    // nums = [1, 3]
    // L22 true -> L23 true -> right = 0となり、見つからなくなる
    public int searchWrong(int[] nums, int target) {
        int targetIndex = -1;

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[mid] < target) {
                if (nums[left] <= target) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (nums[left] > target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return targetIndex;
    }
    
    /*
     * binarySearchが真ん中の値を使って探せるのは、昇順に並んでいるから
     * そのまま使うものではない
     * 
     * この問題では、まずはふつうの昇順になっている部分を見つける
     * ２つに分けると、少なくともどちらかは昇順になっているから
     * 昇順が見つかったら、その中にtargetが含まれているかを確認して、なければもう片方を見る
     * 
     * 時間計算量：O(logn)
     * 空間計算量：O(1)
     */
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
    
}