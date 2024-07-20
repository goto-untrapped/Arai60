public class MinimumSizeSubarraySumStep2 {
    // インデックスを使って
    // 和がtargetより大きい場合は最初に除けるとする
    // https://github.com/hayashi-ay/leetcode/pull/51
    public int minSubArrayLen2_1(int target, int[] nums) {
        if (Arrays.stream(nums).sum() < target) {
            return 0;
        }
        int minSize = nums.length;
        int left = 0;
        int right = 0;
        int prefixSum = 0;
        while (right < nums.length) {
            prefixSum += nums[right];
            while (target <= prefixSum) {
                minSize = Math.min(minSize, right - left + 1);
                prefixSum -= nums[left];
                left++;
            }
            right++;
        }
        return minSize;
    }
    /*
     * 思ったこと
     * ・Step1に比べて流れが分かりやすい
     * 　・rightは増やす時に使うだけ、leftは減らす時に使うだけ
     * 　・Step1はnums[left]が抜けて sum < target になってright++しても
     *    インデックスエラーにならないようにもう一回if文中で制御する必要がある
     * ・prefixSum じゃなくて subArraySumだった
     */
    
    
    // forでいける
    public int minSubArrayLen2_2(int target, int[] nums) {
        int minSize = nums.length + 1;
        int left = 0;
        int prefixSum = 0;
        for (int right = 0; right < nums.length; right++) {
            prefixSum += nums[right];
            while (target <= prefixSum) {
                minSize = Math.min(minSize, right - left + 1);
                prefixSum -= nums[left];
                left++;
            }
        }
        if (minSize == nums.length + 1) {
            return 0;
        }
        return minSize;
    }
    /*
     * 思ったこと
     * ・確かにwhileはforとやってること一緒だった。
     * ・prefixSum じゃなくて subArraySum
     */
    
    
    // 累積和を使って
    // 最大値を len(nums) + 1 とする
    // https://github.com/shining-ai/leetcode/pull/49
    public int minSubArrayLen2_3(int target, int[] nums) {
        int[] prefixSums = new int[nums.length + 1];
        for (int i = 1; i < prefixSums.length; i++) {
            prefixSums[i] = prefixSums[i - 1] + nums[i - 1];
        }

        int left = 0;
        int minSize = nums.length + 1;
        for (int right = 1; right < prefixSums.length; right++) {
            while (left < right) {
                if (prefixSums[right] - prefixSums[left] < target) {
                    break;
                }
                minSize = Math.min(minSize, right - left);
                left++;
            }
        }
        if (minSize == nums.length + 1) {
            return 0;
        }
        return minSize;
    }
    /*
     * 思ったこと
     * ・初めてleft引いてtarget以上のところからleftを動かせばいい。と考えられる
     */
    
    // 累積和を使って
    // https://github.com/SuperHotDogCat/coding-interview/pull/31
    public int minSubArrayLen2_4(int target, int[] nums) {
        int[] prefixSums = new int[nums.length + 1];
        for (int i = 1; i < prefixSums.length; i++) {
            prefixSums[i] = prefixSums[i - 1] + nums[i - 1];
        }

        int left = 0;
        int right = 1;
        int minSize = Integer.MAX_VALUE;
        boolean isUpdateMinSize = false;
        while (right < prefixSums.length) {
            if (left < right && prefixSums[right] - prefixSums[left] >= target) {
                minSize = Math.min(minSize, right - left);
                isUpdateMinSize = true;
                left++;
            } else {
                right++;
            }
        }

        if (!isUpdateMinSize) {
            return 0;
        }
        return minSize;
    }
    /*
     * 思ったこと
     * ・どっちみち right == left の場合、else {} に入るから、
     * わざわざ right = 1, if (left < right) にしなくていいかな。
     */
    
    
    /*
     * minSizeの初期化について思ったこと
     * ・nums.length + 1が自然かなと思ったけど、Intger.MAX_VALUEでもよさそう
     * ・余裕があったら最適化を考えて、sum(nums) < target で場合分けして minSize = nums.length
     */
    
}
/*
 * 参考
 * https://github.com/hayashi-ay/leetcode/pull/51
 * ・足していくのはrightのインデックス、引いていくのはleftのインデックス、で統一した方がいいな
 * ・累積和と二分探索を使えば同じことになるのか
 * ・Step1でWAしたやり方、どっちみちTLEにはなるけど、累積和を配列で持って比較すれば、greater than の場合に対応できたな。
 * ・どっちみちright増やすんだからforでいける
 * ・inf を使わなくできる。総和がtargetより大きいのであれば、targetを満たす和になる個数は0。
 * ・min_len = len(nums) と置けば、インデックスを全部使う時は一致するので、末尾の初期化値と同じだったら、の分岐がいらない。
 * https://github.com/shining-ai/leetcode/pull/49
 * ・計算量はあくまで計算時間の見積もりであり、大事なのは計算時間が実際にどれくらいになり、それがこの状況に対して問題ないのか、ということ
 * https://github.com/Mike0121/LeetCode/pull/22
 * ・Javaでお手軽にsumするためにStream()を使ってみる
 * ・フラグを使うのもあり
 * https://github.com/SuperHotDogCat/coding-interview/pull/31
 * ・こうやって累積和を使えば、間に合う
 */
