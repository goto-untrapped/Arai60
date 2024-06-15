public class FindMinimumInRotatedSortedArrayStep3 {
    /*
     * 5min
     * 理解したというよりは覚えていたという感じがする
     * 整理
     * 全てが昇順⇒rightが左に絞られていきleftは動かないためOK
     * 回転している場合⇒ 
     * nums[middle] > nums[-1]であれば、右側を見に行く でも最大は[-1]だからrightがひたすら左に絞られてOK
     * nums[middle] < nums[-1]であれば、左側を見に行く 結局[-1]より[middle]が大きいならleftはちょうど最小になるまで動いてrightで絞れる、
     * [-1]が[middle]よりまた大きかったらやっぱりrightが左に行って絞れる
     */
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] > nums[nums.length - 1]) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return nums[left];
    }
}
