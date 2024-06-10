public class MoveZeroesStep1 {

    // 1h 解き直し
    // 時間計算量：O(n ^ 2)
    // [0,0,...,1,2,...]の場合
    // 空間計算量：O(1)
    public void moveZeroes(int[] nums) {
        int zeroIndex = 0;
        int nonZeroIndex = 0;
        int firstZeroAfterIndex = 0;

        while (firstZeroAfterIndex < nums.length && nonZeroIndex < nums.length) {
            while (firstZeroAfterIndex < nums.length && nums[firstZeroAfterIndex] != 0) {
                firstZeroAfterIndex++;
            }

            zeroIndex = firstZeroAfterIndex;
            nonZeroIndex = firstZeroAfterIndex;
            
            while (nonZeroIndex < nums.length && nums[nonZeroIndex] == 0) {
                nonZeroIndex++;
            }

            if (firstZeroAfterIndex == nums.length || nonZeroIndex == nums.length) {
                break;
            }

            int zero = nums[zeroIndex];
            nums[zeroIndex] = nums[nonZeroIndex];
            nums[nonZeroIndex] = zero;

            firstZeroAfterIndex++;
            nonZeroIndex++;
        }

    }
}