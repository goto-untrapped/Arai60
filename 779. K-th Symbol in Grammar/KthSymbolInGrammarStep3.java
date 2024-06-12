public class KthSymbolInGrammarStep3 {
    // 2min
    public int kthGrammar(int n, int k) {
        if (n == 1) return 0;

        int nodeSum = (int) Math.pow(2, n - 1);
        if (k <= nodeSum / 2) {
            return kthGrammar(n - 1, k);
        }
        return 1 - kthGrammar(n, k - nodeSum / 2);
    }
    
    /*
     * ・2^10 = 1024 ≒ 1000 = 10^3 の変換を自然にできるようにしよう
     * https://github.com/Exzrgs/LeetCode/commit/9c4f4eac369f4da6ed136735931a4cfc1fbd4a3e
     * 
     * ・n == 1 より k == 1 の方が速いというコメントがそういえばあった
     * 確かにそうだ、kが最後のインデックス以外だったら、n == 1 になる前に k == 1 になれると思った
     * https://github.com/Mike0121/LeetCode/pull/18/files
     * 
     * ・nodeSumという名前微妙だったな、何でnode?ツリーか何か?どこが?とかなりそうな気がする
     * elementsSumとかになるのかなと思ったけど、numTotalElementsの方が分かりやすいな。nunElementsも。
     * https://github.com/shining-ai/leetcode/pull/46/files
     *      
     * ・return 1 ^ kthGrammar(n, k - nodeSum / 2); と書くこともできるのを知識として知っておく
     * | 一応、これ XOR で、XOR ということは () == () で一応書けます。() ないと、Python は chain します。
     * https://discord.com/channels/1084280443945353267/1196472827457589338/1243131995370815488
     */
}
