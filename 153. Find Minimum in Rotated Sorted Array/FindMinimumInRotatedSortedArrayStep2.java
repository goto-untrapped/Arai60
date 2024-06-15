public class FindMinimumInRotatedSortedArrayStep2 {
    /*
     * https://github.com/Exzrgs/LeetCode/pull/11/files
     * https://github.com/YukiMichishita/LeetCode/pull/9/files
     * https://github.com/hayashi-ay/leetcode/pull/45/files
     */
    
    /*
     * Step1は下記のようにより簡潔に書けるけど、場合分けした方がいいのではないかと思った。
     * 確かに、rightはlengthなので、middle-1 インデックス目は範囲内にあるのでB>Aの時返せるし、
     * A<Bなら1行目で返せるけど、分かれた場所で同じ個数の数字の並びを確保しているのは読みにくいと思った。
     * でも、Step1ではrightがlengthになることに気付かず、middle-1とmiddle+1が要素数と一致するか考えていなかった。
     * また最後の返り値はStep1のように-1にすると要素の取り得る範囲とかぶるので、範囲外の数を使った方がよかった。
     * また、この最後のreturnは通らない想定のため、書かざる負えないようなプログラムの構造はよくない感覚があり、できれば直したい。
     */
    public int findMin2_1(int[] nums) {
        if (nums[0] < nums[nums.length - 1]) return nums[0];
        if (nums.length == 1) return nums[0];

        int left = 0;
        int right = nums.length;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (nums[middle - 1] > nums[middle]) {
                return nums[middle];
            }
            if (nums[middle] > nums[middle + 1]) {
                return nums[middle + 1];
            }


            if (nums[0] < nums[middle]) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return Integer.MAX_VALUE;
    }
    
    /* 2_2と2_3 合わせて1.5h整理していた */
    /*
     * https://github.com/Exzrgs/LeetCode/pull/11/files
     * そっか、最大から最小に切り替わる境界を探す考え方と、最小を探し当てる考え方があるのか。
     * Step1は、最小を探し当てるやり方だと思った。
     * 前後の値を確認しなくても、絞れるのか。
     * 探し当てに行くやり方をもっと簡潔に
     */
    /*
     * なぜ最右端と比べているか分からなかった
     * 最左端だと、配列全体が昇順か、一部が昇順なのか判別できない
     */
    public int findMin2_2(int[] nums) {
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
    
    /*
     * 境界を探し出す考え方もやってみる
     * leftとrightはきっと1の距離が空いたままループが終わる
     * [max,min]になっていて、境界線が分かる感じなのかな
     * 開区間 全部昇順でも、leftは-1のままなので絞れる
     */
    public int findMin2_3(int[] nums) {
        int left = -1;
        int right = nums.length;
        while (right - left > 1) {
            int middle = (left + right) / 2;
            if (nums[middle] > nums[nums.length - 1]) {
                left = middle;
            } else {
                right = middle;
            }
        }
        return nums[right];
    }
}
