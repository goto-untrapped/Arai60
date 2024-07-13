public class HouseRobberStep1 {
    /*
     * 解き直しだが、WA20minから進まず+40minかけて解答を読み、AC
     * 時間計算量：O(n)
     * 空間計算量：O(n)
     */
    public int rob(int[] nums) {
        int[] maxAmounts = new int[nums.length + 1];
        maxAmounts[nums.length - 1] = nums[nums.length - 1];

        for (int i = nums.length - 2; i >= 0; i--) {
            maxAmounts[i] = Math.max(maxAmounts[i + 2] + nums[i], maxAmounts[i + 1]);
        }
        return maxAmounts[0];
    }
    /*
     * 試したやり方：
     * 前から、1個飛ばしか2個飛ばしの大きい方を今の値に足す
     * WAケース：nums = [2,4,8,9,9,3] 
     */
    
}
