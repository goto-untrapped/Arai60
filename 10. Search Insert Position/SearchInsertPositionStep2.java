public class SearchInsertPositionStep2 {
    
    /*
     * 参照
     * https://github.com/SuperHotDogCat/coding-interview/pull/9/files
     * https://github.com/rm3as/code_interview/pull/9/files
     * https://github.com/shining-ai/leetcode/pull/41/files
     * https://github.com/shining-ai/leetcode/pull/41/files 
     */
    
    public int searchInsert2_1(int[] nums, int target) {
        // ループをしなくても、含まれていないと分かる
        if (nums[nums.length - 1] < target) {
            return nums.length;
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
           
        // ここに辿り着くことはないはずだけど、書かなきゃいけないというのは
        // ロジック的に流れがまずい気がする
        return -1;
    }
    
    // 2_1の処理の流れを見直した。すっきりした気がする
    // O(n)だけど、提出時のRuntimeは二分探索と同じだった
    // 1万ぐらいの長さだったら、そんなに速さが変わらないということかなと思った
    // Python3でこの書き方と二分探索を試してみたけど、それぞれ同じRuntimeだった(51ms)
    public int searchInsert2_2(int[] nums, int target) {
        if (nums[nums.length - 1] < target) {
            return nums.length;
        }

        int targetIndex = 0;
        while (nums[targetIndex] < target) {
            targetIndex++;
        }

        return targetIndex;
    }
    
    // binary searchで書き直した
    // ロジック的な理由はなく、書き慣れているからという理由でこのように書いた
    public int searchInsert2_3(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            // オーバーフロー防止
            int middle = left + (right - left) / 2;

            if (nums[middle] == target) {
                return middle;
            }

            if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        return left;
    }
    
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int middle = left + (right - left) / 2;

            // if (nums[middle] <= target) だと動かない
            // <= にすると、今欲しい target が満たす必要のある target <= nums[i]を満たせなくなる
            if (nums[middle] < target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }

        return left;
    }
    
}