public class MaximumSubArrayStep1 {
    /*
     * 解き直し
     * 25分ぐだぐだ書いたけどテストケース通らず
     * その後答えを見て、20分くらいで書いた
     * 解法を覚えていたつもりだったが、理解が不十分だった
     * ⇒足しても最大値より小さかったら最大値からスタートする
     * 時間計算量：O(n)
     * 空間計算量：O(1)
     */
    public int maxSubArray(int[] nums) {
        int maxSum = nums[0];
        int prefixSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            if (prefixSum + num < num) {
                prefixSum = num;
            } else {
                prefixSum += num;
            }
            maxSum = Math.max(maxSum, prefixSum);
        }
        return maxSum;
    }
}
