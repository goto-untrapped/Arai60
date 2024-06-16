public class SubarraySumEqualsKStep2 {
    /*
     * TLE 全区間見る
     * O(n^3)/O(1)
     * Max(n)=2*10^4
     * n^3=10^4^3=10^12回 回る 1兆回
     * CPUの読み出し⇒計算⇒格納 1回の和 10ステップないくらいのイメージ 
     * Branch mispredict 5ns だったら、10nsとしてみる
     * 10ns * 10^12回 = 10 * 10 ^ -9 * 10 ^ 12 = 1*10^6s = 10万秒=1万分以上=3hくらい？ そりゃ間に合わない
     * Step1で通ったものはn^2=10^4^2=10^8回 回る
     * 10ns * 10^8回 = 10^-9 * 10^8 = 10 ^ -1s = 1s Runtimeが1086msだったため、だいたい一緒 
     * */
    public int subarraySum2_1_TLE(int[] nums, int k) {
        int equalKCount = 0;
        for (int start = 0; start < nums.length; start++) {
            for (int end = start; end < nums.length; end++) {
                int sum = 0;
                for (int index = start; index <= end; index++) {
                    sum += nums[index];
                }
                if (sum == k) {
                    equalKCount++;
                }
            }
        }
        return equalKCount;
    }
    // 累積和を全て求めた後、指定のインデックス範囲の和を引くことでkに一致する種類の数を探す
    // O(n^2)/O(n)
    public int subarraySum2_2(int[] nums, int k) {
        int equalKCount = 0;
        int[] untilSum = new int[nums.length + 1];
        untilSum[0] = 0; 
        for (int index = 0; index < nums.length; index++) {
            untilSum[index + 1] = untilSum[index] + nums[index];
        }
        for (int start = 0; start < untilSum.length; start++) {
            for (int end = start + 1; end < untilSum.length; end++) {
                if (untilSum[end] - untilSum[start] == k) {
                    equalKCount++;
                }
            }
        }
        return equalKCount;
    }
    
    // マップを使って、範囲の和の差からk一致を求める処理を高速化
    // O(n)/O(n)
    public int subarraySum2_3(int[] nums, int k) {
        int sum = 0;
        HashMap<Integer, Integer> sumToCount = new HashMap<>();
        sumToCount.put(0, 1);
        int equalKCount = 0;
        for (int num : nums) {
            sum += num;
            if (sumToCount.containsKey(sum - k)) {
                equalKCount += sumToCount.get(sum - k);
            }
            sumToCount.put(sum, sumToCount.getOrDefault(sum, 0) + 1);
        }
        return equalKCount;
    }
}

/*
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1210881816605499472
 *   コメント
 * https://github.com/ryoooooory/LeetCode/pull/3
 *   prefixSumToCount 
 * https://github.com/tshimosake/arai60/pull/7/files
 *   Build-in な名前はできれば使いません。
 * https://github.com/Exzrgs/LeetCode/pull/25/files
 *   コメント
 * https://github.com/fhiyo/leetcode/pull/19/files
 *   コメント
 */
