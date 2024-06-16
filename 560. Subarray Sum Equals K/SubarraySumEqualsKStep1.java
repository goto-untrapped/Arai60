public class SubarraySumEqualsKStep1 {
    // 15min
    // continueいらなかったな。プログラムが短く、後続処理ないことが見てすぐ分かるため。
    public int subarraySum(int[] nums, int k) {
        int equalKCount = 0;
        for (int start = 0; start < nums.length; start++) {
            int sum = 0;
            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                if (sum == k) {
                    equalKCount++;
                    continue;
                }
            }
        }
        return equalKCount;
    }
    
    /*
     * はじめ、下記のように書いたが、WA
     * 連続する数の和は変わらない
     * ⇒初めと終わりのインデックスをずらしながらkと一致する範囲を計算すれば、同じ和の計算を２回しなくて済むし、O(n+n)=O(n)でできそう
     * 1つの数字がkの場合をうまく書ききれず、断念した
     * ⇒そもそも、同じ初めのインデックスから2回kになりうる時点で、その後ろの範囲全部見る必要があり、上記のソースになる
     */
}
