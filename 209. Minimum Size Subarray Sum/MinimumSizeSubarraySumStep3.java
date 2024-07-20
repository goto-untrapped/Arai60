public class MinimumSizeSubarraySumStep3 {
    // 2m10s / 2m50s / 1m40s
    public int minSubArrayLen(int target, int[] nums) {
        int minLen = nums.length + 1;
        int left = 0;
        int right = 0;
        int subArraySum = 0;
        while (right < nums.length) {
            subArraySum += nums[right];
            while (subArraySum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                subArraySum -= nums[left];
                left++;
            }
            right++;
        }

        if (minLen == nums.length + 1) {
            return 0;
        }
        return minLen;
    }
    
    /*
     * 思ったこと
     * ・変数名は問題文に合わせるといい（業務だったら既存コードに合わせるみたいな）
     * ・この問題を解く範囲だったら nums.length + 1 を2回書けばいいと思うが、
     * 業務だったら定数で置く、2回書かなくていいやり方でやる、小規模だったらそのままでいいかも、とかと思う。
     * ・今更だけど、for (int right = 0; ...) で書けたのにふつうに書いたら while になってた。
     * まだ for がふつうに出てこなかったな。
     */
}
