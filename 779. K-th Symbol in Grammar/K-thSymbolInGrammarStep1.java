class KthSymbolInGrammarStep1 {
    // 何も見ずに書くまでに1hくらいかかった気がする
    // 法則見つけられそうだと思ってnを増やして確認していたが、分からなかったため、答えを見た
    public int kthGrammar(int n, int k) {
        return kthGrammarVal(n, k, 0);
    }

    private int kthGrammarVal(int n, int k, int rootVal) {
        if (n == 1) {
            return rootVal;
        }

        int nodeSum = (int) Math.pow(2, n - 1);
        if (k <= (nodeSum / 2)) {
            int nextRootVal = rootVal == 0 ? 0 : 1;
            return kthGrammarVal(n - 1, k, nextRootVal);
        }

        int nextRootVal = rootVal == 0 ? 1 : 0;
        return kthGrammarVal(n - 1, k - (nodeSum / 2), nextRootVal);
    }
}
