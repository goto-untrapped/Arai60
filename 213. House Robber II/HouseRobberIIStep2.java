public class HouseRobberIIStep2 {
    // 関数を切り出して共通化
    class Solution2_1 {
        public int rob(int[] nums) {
            if (nums.length == 1) {
                return nums[0];
            }
            if (nums.length == 2) {
                return Math.max(nums[0], nums[1]);
            }
            int[] formFirstMaxSubSums = calcMaxSubSums(nums, 0, nums.length - 1);
            int[] formSecondMaxSubSums = calcMaxSubSums(nums, 1, nums.length);

            return Math.max(formFirstMaxSubSums[formFirstMaxSubSums.length - 1], formSecondMaxSubSums[formSecondMaxSubSums.length - 1]);
        }

        private int[] calcMaxSubSums(int[] nums, int start, int end) {
            if (nums.length < 2 || start >= end) {
                return new int[] {-1};
            }
            int[] maxSubSums = new int[end - start];
            maxSubSums[0] = nums[start];
            maxSubSums[1] = Math.max(nums[start], nums[start + 1]);
            for (int i = 2; i < maxSubSums.length; i++) {
                maxSubSums[i] = Math.max(maxSubSums[i - 2] + nums[start + i], maxSubSums[i - 1]);
            }
            return maxSubSums;
        }
    }
    /*
     * 思ったこと
     * ・共通関数の最初の入力チェックは、業務では書こうと思った。
     * 正しい入力が来るので通ることはないけど、共通関数なのに何も書かれていないのは気になるし、
     * 一応こう考えていますという意思表示になればいいと思った。
     */
    

    // 空間計算量O(1)
    class Solution2_3 {
        public int rob(int[] nums) {
            if (nums.length == 1) {
                return nums[0];
            }
            if (nums.length == 2) {
                return Math.max(nums[0], nums[1]);
            }
            int formFirstMaxSubSum = calcMaxSubSums(nums, 0, nums.length - 1);
            int formSecondMaxSubSum = calcMaxSubSums(nums, 1, nums.length + 1);

            return Math.max(formFirstMaxSubSum, formSecondMaxSubSum);
        }

        private int calcMaxSubSums(int[] nums, int start, int end) {
            if (nums.length < 2 || start >= end) {
                return -1;
            }
            int twoBeforeSubSum = nums[start];
            int oneBeforeSubSum = Math.max(nums[start], nums[start + 1]);
            for (int i = start + 2; i < end - start; i++) {
                int maxSubSum = Math.max(twoBeforeSubSum + nums[i], oneBeforeSubSum);
                twoBeforeSubSum = oneBeforeSubSum;
                oneBeforeSubSum = maxSubSum;
            }
            return oneBeforeSubSum;
        }
    }
    /*
     * 思ったこと
     * ・共通化するために最後の家まで見る方は、引数に+1をする必要があり、間違えそう
     */
}

/*
 * https://github.com/hayashi-ay/leetcode/pull/50
 *   https://discord.com/channels/1084280443945353267/1200089668901937312/1218564286624960582
 * https://github.com/shining-ai/leetcode/pull/36
 *   https://discord.com/channels/1084280443945353267/1201211204547383386/1223556134502137857
 * https://github.com/sakupan102/arai60-practice/pull/37
 * https://github.com/fhiyo/leetcode/pull/37
 */
