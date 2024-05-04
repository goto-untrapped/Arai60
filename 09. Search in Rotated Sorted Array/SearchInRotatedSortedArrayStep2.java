public class SearchInRotatedSortedArrayStep2 {
    
    /*
     * 参照いたしました。
     * https://github.com/hayashi-ay/leetcode/pull/49/files
     * https://github.com/SuperHotDogCat/coding-interview/pull/10/files
     * https://github.com/shining-ai/leetcode/pull/43/files
     */
    
    // 閉区間[a,b] と 半開区間（以上、より小さい）[a, b) で、最小値を使うやり方と、昇順配列を探しながら二分探索するやり方で書く練習

    /*
     * 最小値を指すpivotを探して、それぞれふつうに二分探索する
     * 閉区間[a,b]
     */
    public int search(int[] nums, int target) {
        int minIndex = searchMinIndexByClosed(nums);
        if (nums[minIndex] <= target && target <= nums[nums.length - 1]) {
            return binarySearchByClosed(nums, minIndex, nums.length - 1, target);
        }
        return binarySearchByClosed(nums, 0, minIndex - 1, target);
    }
    
    // 閉区間[a,b]
    private int searchMinIndexByClosed(int[] nums) {
        int left = 0;
        // bインデックスの値も含むため？
        int right = nums.length - 1;
        
        while (left <= right) {
            int middle = (left + right) / 2;
            
            // 4 5 0 1 2 だと、right=1 -> left=2となりいい感じにleftが最小値になる
            // 3 4 5 0 1 だと、left=3 -> right=2となりいい感じにleftが最小値になる
            // なぜいい感じにできるのかが分からない・・・。
            if (nums[middle] <= nums[nums.length - 1]) {
                // leftと重なってよいため？
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        
        return left;
    }
    
    // 半開区間（以上、より小さい）[a, b)
    private int searchMinIndexByHalfOpen(int[] nums) {
        int left = 0;
        int right = nums.length;
        
        while (left < right) {
            int middle = (left + right) / 2;
            
            if (nums[middle] <= nums[nums.length - 1]) {
                // leftと1以上離したいから？
                right = middle; 
            } else {
                left = middle + 1;
            }
        }
        
        return left;
    }
    
    // 閉区間[a,b]
    private int binarySearchByClosed(int[] nums, int start, int end, int target) {
        int left = start;
        int right = end;

        while (left <= right) {
            int middle = (left + right) / 2;

            if (nums[middle] == target) {
                return middle;
            }

            if (nums[middle] <= target) {
                // middleであれば既にreturnしているため？
                left = middle + 1;
            } else {
                // targetはmiddle以上でないから、1つ小さいインデックスを含んで見ればよい
                right = middle - 1;
            }
        }

        return -1;
    }
    
    // 半開区間（以上、より小さい）[a, b)
    private int binarySearchByHalfOpen(int[] nums, int start, int end, int target) {
        int left = start;
        int right = end + 1;
        
        while (left < right) {
            int middle = (left + right) / 2;
            
            if (nums[middle] == target) {
                return middle;
            }
            
            if (nums[middle] < target) {
                // middleであれば既にreturnしているため？
                left = middle + 1;
            } else {
                // targetはmiddle以上でないが、1つ小さいインデックスを含んで見る時、leftと1つ以上離したいから？
                right = middle;
            }
        }
        
        return -1;
    }
    
    /* 閉区間[a,b] WA */
    public int searchByClosed_WA(int[] nums, int target) {
        int minIndex = searchMinIndexByClosed(nums);
        
        /*
         * [3,1], target=3 で通らず
         * 3 <= 3 < 1 はありえない
         * 先に、最小値を含む方の範囲でif文を回さないと、1つの要素の場合に対応できないからだと思った
         */
        if (nums[0] <= target && target < nums[minIndex]) {
            return binarySearchByClosed(nums, 0, minIndex, target);
        }
        if (nums[minIndex] <= target && target <= nums[nums.length - 1]) {
            return binarySearchByClosed(nums, minIndex, nums.length - 1, target);
        }
        return -1;
    }
    
    
    // 判定しながら二分探索
    // 境界値はぼんやりしたイメージと知識で書いてて、境界値のパターンについて理解していない
    // 閉区間
    public int searchHalfClosed(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        
        // 閉区間なのでleftとrightは重なってよい（はず）
        while (left <= right) {
            int mid = (left + right) / 2;

            if (nums[mid] == target) {
                return mid;
            }
            
            // 左部分は昇順
            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // nums[right]を含んでいれば、midはleftを通じて辿り着けるから？
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
    
    // 半開区間
    public int searchHalfOpen(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int middle = (left + right) / 2;

            if (nums[middle] == target) {
                return middle;
            }
            
            // 左部分は昇順
            if (nums[left] <= nums[middle]) {
                if (nums[left] <= target && target < nums[middle]) {
                    right = middle;
                } else {
                    left = middle + 1;
                }
            } else {
                if (nums[middle] < target && target <= nums[right - 1]) {
                    left = middle + 1;
                } else {
                    // leftとrightは等しくなれないから、leftがmiddle-1の値まで含んで見れるようにするため？
                    right = middle;
                }
            }
        }
        return -1;
    }
    
}