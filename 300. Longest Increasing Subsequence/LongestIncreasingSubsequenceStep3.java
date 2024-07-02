public class LongestIncreasingSubsequenceStep3 {
    /*
     * 1 <= nums.length <= 2500
     * 最悪 2500^2 の時間計算量、空間計算量
        O: 2500 * 2500 = 6,250,000 40msだった
        O: 2500 * 10 = 25,000 5msだった
        40 / 5 = 8倍
        
        nums.length * 10 の場合
        25000 * 25000 = Ans * 100 4000ms
        25000 * 14 = Ans * 10 50ms
        4000 / 10 = 400倍くらい違う計算になる 1つのプログラムで4sはかかりすぎる
        
        nlognで解けるのなら、それを採用した方がいい
     */
    
    /*
     * https://discord.com/channels/1084280443945353267/1200089668901937312/1209563502407065602
     * > 頭から舐めていって k 番目まで見たときに、
        「nums[k] までを使って、すべての長さ1のシーケンスを考えたときに、そのシーケンスの一番お尻の最小値」
        「nums[k] までを使って、すべての長さ2のシーケンスを考えたときに、そのシーケンスの一番お尻の最小値」
        「nums[k] までを使って、すべての長さ3のシーケンスを考えたときに、そのシーケンスの一番お尻の最小値」
        ……
        というものです。これは昇順に並んでいるはずで、nums[k + 1] を使って、これを更新することは可能ですね。
        
        これは、sorted array なので二分探索で、どこを更新するかは絞れます。

        ⇒自分なりにかみ砕けたと思ったので、これで書く
        ・numsをループする時、増加数字配列の1つ目の数字は絶えずより小さい数字で更新されるので、numsのすべてのそれぞれの要素に対して、比較できているし、一番小さい数字が入るよね
        ・1つ目が決まった後、増加数字配列の2つ目の数字も絶えずより小さい数字で更新されるので、1つ目と2つ目の数字の組み合わせは、numsの中で、最初に一番小さいもの、次に小さいものを全通り比較した中での最小になるよね
        ・これを続けているから、増加数字配列は昇順になっていて、検索するのに二分探索が使えるよね

     */
    public int lengthOfLIS(int[] nums) {
        ArrayList<Integer> increasingNums = new ArrayList<>();

        for (int target : nums) {
            if (increasingNums.size() == 0) {
                increasingNums.add(target);
                continue;
            }

            if (increasingNums.get(increasingNums.size() - 1) < target) {
                increasingNums.add(target);
                continue;
            }

            int replaceIndex = search(increasingNums, target);
            increasingNums.set(replaceIndex, target);
        }

        return increasingNums.size();
    }

    private int search(ArrayList<Integer> increasingNums, int target) {
        int left = 0;
        int right = increasingNums.size();

        while (left < right) {
            int middle = left + (right - left) / 2;

            if (increasingNums.get(middle) < target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }
    
}
