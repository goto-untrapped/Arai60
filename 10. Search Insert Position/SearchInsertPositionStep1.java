public class SearchInsertPositionStep1 {
    
    // [1]でだめ
    public int searchInsertWA1(int[] nums, int target) {
        int index = 0;
        while (index < nums.length && target > nums[index]) {
            index++;
        }

        if (index >= nums.length - 1) {
            return nums.length;
        }
        return nums[index + 1] == target ? index + 1 : index;
    }
    
    // [1,3]でだめ
    public int searchInsertWA2(int[] nums, int target) {
        if (nums.length == 1) return target <= nums[0] ? 0 : 1;

        int index = 0;
        while (index < nums.length && target > nums[index]) {
            index++;
        }

        if (index >= nums.length - 1) {
            return nums.length;
        }
        return nums[index + 1] == target ? index + 1 : index;
    }
    
    // 根本的に直した方がいいと思って書き直したもの
    // 時間計算量：O(n)
    // 空間計算量：O(1)
    // はじめ、if (nums[i] == target) と if (nums[i] > target) で分けて書いていたが、中身が同じだった。
    // インデックスは始まりが0だから、要素を挿入する時はより大きい値のインデックスにそのまま入れればいい ということかなと思った
    public int searchInsert(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }

        return nums.length;
    }
    
}