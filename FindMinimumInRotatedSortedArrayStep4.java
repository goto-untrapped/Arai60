public class FindMinimumInRotatedSortedArrayStep4 {
    // 5min（匿名クラスの書き方や書き直しを入れて15分くらい）
    /*
     * 匿名クラスを変数に格納せずに、中身を書いたらそのまま実行して返せる
     */
    public int findMin4_1(int[] nums) {
        return new FindMinimum() {
            @Override
            public int find(int[] nums) {
                int left = 0;
                int right = nums.length - 1;
                while (left < right) {
                    int middle = left + (right - left) / 2;
                    if (nums[middle] < nums[nums.length - 1]) {
                        right = middle;
                    } else {
                        left = middle + 1;
                    }
                }
                return nums[left];
            }
        }.find(nums);
    }
    
    /*
     * インターフェースを定義する
     * インターフェースを定義せずに findMinimum を宣言すると、
     * not find Symbol エラーが出る。
     * おそらく、public でクラスを宣言しようとして、既にあるpublic のfindMin() と競合するからだと思った
     */
    public int findMin4_2(int[] nums) {
        FindMinimum findMinimum = new FindMinimum() {
            @Override
            public int find(int[] nums) {
                int left = 0;
                int right = nums.length - 1;
                while (left < right) {
                    int middle = left + (right - left) / 2;
                    if (nums[middle] < nums[nums.length - 1]) {
                        right = middle;
                    } else {
                        left = middle + 1;
                    }
                }
                return nums[left];
            }
        };
        return findMinimum.find(nums);
    }
}

@FunctionalInterface
interface FindMinimum {
    int find(int[] nums);
}
