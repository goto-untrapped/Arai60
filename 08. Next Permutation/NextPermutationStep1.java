public class NextPermutationStep1 {

    /*
     * 解法が思いつかなかった。
     * 今見てる数より後ろの数のうち、今見てる数より大きい数の中で一番小さい数と入れ替わればできそう？と思った。
     * でも、どこから見ればいいのかが思いつかなかった。
     * また、今見てる、入れ替えが必要な数の後ろの数を、どうやって正しく並び替えられるのか、思いつかなった。
     * ⇒自分で配列を設定したら、想定したアウトプットが求められているものと異なっていた
     *   問題をちゃんと理解するのもやろう・・・。（ちゃんと日本語？で理解する。）
     */
    
    /*
     * 答えを見た。
     * 初め、whileループではなくforループで書いていた。（でも5分考えて整理できなかった）
     * 入れ替えるインデックスが見つかればよく、最後までループを回す必要がないため、
     * whileの方がやることが簡潔に表現されていてよいと思った。
     * 
     * forで書くと、インデックスが勝手に変わるので、ifは当てはまらない時の条件を書くのがふつうで、
     * whileで書いた場合、インデックスはループが継続する時の条件を書いて、
     * 当てはまらない時が探したいインデックスを見つけた時、のように書くのがふつうなのかなと思った。
     * 
     * 初め、入れ替えるインデックスより後の配列を、O(nlogn)のsort()をしようとしていた。
     * なぜreverseで左右対称に置換すれば足りるのか、分からなかった。
     * 今の問題だと、入れ替えたインデックスの後の並び順は降順になっているから、
     * 左右対称で置換すれば昇順になるのだろうと思った。
     */
    public void nextPermutation(int[] nums) {
        int swapIndex = nums.length - 2;
        while (swapIndex >= 0 && nums[swapIndex] >= nums[swapIndex + 1]) {
            swapIndex--;
        }
        if (swapIndex >= 0) {
            int nextNumIndex = nums.length - 1;
            while (nums[swapIndex] >= nums[nextNumIndex]) {
                nextNumIndex--;
            }
            swap(nums, swapIndex, nextNumIndex);
        }
        reverse(nums, swapIndex + 1);
    }

    private void swap(int[] nums, int swapIndex, int nextNumIndex) {
        int temp = nums[swapIndex];
        nums[swapIndex] = nums[nextNumIndex];
        nums[nextNumIndex] = temp;
    }

    private void reverse(int[] nums, int startIndex) {
        int i = startIndex;
        int j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }
    
}
