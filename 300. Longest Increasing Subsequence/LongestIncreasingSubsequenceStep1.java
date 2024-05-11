public class LongestIncreasingSubsequenceStep1 {
    
    /*
     * Step1を実施
     * 連続増加カウントは、比べたものよりちょうど1つだけ大きくなるので、その時までの合計に1を足せば、正しくカウントできる
     * 時間計算量：O(n^2) 1+2+3+...+n = n*(n+1)/2 36ms
     * 空間計算量：O(n) 44.0MB
     */
    public int lengthOfLIS(int[] nums) {
        int[] increasingCounts = new int[nums.length];
        Arrays.fill(increasingCounts, 1);

        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    increasingCounts[i] = Math.max(increasingCounts[i], increasingCounts[j] + 1);
                }
            }
        }

        int maxIncreasingCount = 1;
        for (int count : increasingCounts) {
            if (count > maxIncreasingCount) {
                maxIncreasingCount = count;
            }
        }
        return maxIncreasingCount;
    }
    
    /*
     * 答えを見て分かったと思って書いて、5回くらいデバッグしたもの
     *   2つめのforあたり、その値が取る最大の連続増加カウントを、どうやってmaxを更新しながら、どこで増加カウント配列に代入するといいか、動きをトレースできなかった。
     * 答えに比べて冗長
     */
    public int lengthOfLIS_NG(int[] nums) {
        int[] increasingCounts = new int[nums.length];
        Arrays.fill(increasingCounts, 1);

        for (int i = 1; i < nums.length; i++) {
            int currentMaxIncreasingCount = increasingCounts[i];
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    currentMaxIncreasingCount = Math.max(currentMaxIncreasingCount, increasingCounts[i] + increasingCounts[j]);
                }
            }
            increasingCounts[i] = currentMaxIncreasingCount;
        }

        int maxIncreasingCount = 1;
        for (int count : increasingCounts) {
            if (count > maxIncreasingCount) {
                maxIncreasingCount = count;
            }
        }
        return maxIncreasingCount;
    }
    
    /*
     * WA
     * 同じ数が出てきて後の方が続く時、どうやってそっちの方を採用すればいいのかが分からなかった
     * 1つのみ保持しているより大きい値を前から比較して代入し直すので、
     * n個分見ても正しくない：[0,1,0,3,2,3] で正解は4を、3と出力する
     * 0 1 2 3は見れず、0 1 3で見てしまう
     * 
     * 答えを見た後：
     * それぞれの要素が取れる最大の連続カウントを更新していけば、自然と全部を見た時の最大がわかる（DP）
     * より大きい値が来たら持ち、より小さい値が来たらスワップすれば、個数は最大の長さと一致する（連続要素の取り方の最適化）
     */
    public int lengthOfLIS_WA(int[] nums) {

        int maxIncreaseLength = 0;
        for (int i = 0; i < nums.length; i++) {
            int compare = nums[i];
            int increaseLength = 1;

            int index = i + 1;
            while (index < nums.length) {
                if (compare < nums[index]) {
                    increaseLength++;
                    compare = nums[index];
                }
                index++;
            }
            maxIncreaseLength = Math.max(maxIncreaseLength, increaseLength);
        }
        return maxIncreaseLength;
    }
}
