public class KthSymbolInGrammarStep4 {
    // Step1を改善
    public int kthGrammar4_1(int n, int k) {
        return kthGrammarValue(n, k, 0);
    }
    private int kthGrammarValue(int n, int k, int rootValue) {
        if (n == 1) {
            return rootValue;
        }

        int numSymbols = (int)Math.pow(2, n - 1);
        if (k <= numSymbols / 2) {
            return kthGrammarValue(n - 1, k, rootValue);
        } else {
            return kthGrammarValue(n - 1, k - numSymbols / 2, 1 - rootValue);
        }
    }

    // Step2-2を改善
    public int kthGrammar4_2(int n, int k) {
        int assumption = 0;
        for (; n > 1; --n) {
            int numSymbols = (int)Math.pow(2, n - 1);
            if (k > numSymbols / 2) {
                k -= numSymbols / 2;
                assumption = 1 - assumption;
            }
        }
        return assumption;
    }

    // Step2-3を改善 
    // 解法は思いつけないが、計算結果とほしい返り値の関係を整理してシンプルに書くことはできるようにする
    public int kthGrammar4_3(int n, int k) {
        int bitCount = Integer.bitCount(k - 1);
        return bitCount % 2;
    }

}
