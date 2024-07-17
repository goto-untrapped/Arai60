public class SubsetsStep1 {
    /*
     * 25min
     * 時間計算量：O(n * (n - 1) * ... * 1) = O(n!) (誤り)
     * ⇒ 1つの数字ごとに、選ぶか選ばないかの2通りがあるという考え方。なので O(2^n)
     * 
     * 空間計算量：O(nC1 + nC2 + ... + nCn) = O(2^n)
     * 一番深い時の最大再帰回数： 1 <= nums.length <= 10 より、10回 ⇒ オーバーヘッド10000バイトくらいまでならいけそう
     * => nums=[1,2,3,4,5,6,7,8,9,10]でローカル実行できた
     */
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> allSubSets = new ArrayList<List<Integer>>();
        allSubSets.add(new ArrayList<>());
        return subsetsHelper(nums, allSubSets, 0);
    }

    private List<List<Integer>> subsetsHelper(int[] nums, List<List<Integer>> subsets, int start) {
        List<Integer> subset = subsets.get(subsets.size() - 1);
        if (subset.size() == nums.length) {
            return subsets;
        }
        for (int i = start; i < nums.length; i++) {
            List<Integer> nextSubSet = new ArrayList<>(subset);
            nextSubSet.add(nums[i]);
            subsets.add(nextSubSet);
            subsetsHelper(nums, subsets, i + 1);
        }
        return subsets;
    }
    
    /*
     * 思ったこと
     * ・List<List<Integer>>型で返すことうまくができず、ハマった。
     * 　・allSubSets の実装の型をList<ArrayList<Integer>>(); にしていた。
     *    ⇒ 基本型はサブタイプを取ることができるが、引数の型はジェネリック型のルールが適用されて、同じ型で揃える必要があるため。
     *       もしサブタイプを取りたい場合は、基本型を extends する。
     *       https://www.quora.com/Why-cant-I-initialise-List-List-Integer-as-List-List-Integer-list-new-ArrayList-ArrayList-Integer
     * 　・subsetsHelper の引数 subsets の型を ArrayList<List<Integer>> にしていた。
     *    ⇒ 宣言した型に合わせる。関数としてやり取りする型は宣言した型で統一されるため。
     * 　・subset の引数の型を ArrayList<Integer> にしていた。
     *    ⇒ 実装した型に合わせる。ジェネリックに任せると決めた範囲のため。
     * ・ループで書きたかったが、頭の中がまとまらなかった。backtracking も試そうとしたが、入れる⇒削除してもう一度出現した組み合わせの追加を
     * 回避する方法が浮かばなかった。残された選択肢が再帰しかなかったので、
     * 再帰の回数が足りるか考えずに（LeetCodeなら足りるだろうけど）再帰で書いた。
     * ・subsetで一語だった。
     */
}
