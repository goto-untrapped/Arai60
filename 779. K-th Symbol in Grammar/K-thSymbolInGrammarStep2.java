class KthSymbolInGrammarStep2 {

    // 全体で3hくらいかかった
    /*
     * 0が01、1が10の時、真ん中で分けてそれぞれの前から見ると、値が入れ替わっている。
     * 0|1
     * 01|10
     * 0110|1001
     * と増えていっても入れ替わっている。
     * なので、前の行が持つ数字の個数より小さくする場合、1-(今の数字)で対応する値を計算できる。
     * 1行前に行く時、そのまま同じ値になる。
     */
    public int kthGrammar2_1(int n, int k) {
        return kthGrammarVal(n, k);
    }

    private int kthGrammarVal(int n, int k) {
        if (n == 1) {
            return 0;
        }

        int nodeSum = (int) Math.pow(2, n - 1);
        int nodeHalfSum = nodeSum / 2;
        if (k > nodeHalfSum) {
            return 1 - kthGrammarVal(n, k - nodeHalfSum);
        }

        return kthGrammarVal(n - 1, k);
    }

    /*
     * 2_1のやり方ができる時、下記の見方ができる。
     * 前の行より大きい列の時だけ値が0<->1と入れ替わる。
     * よって、ある行、ある列の値を仮定しておいて、根本まで計算したとき、
     * 0だったら仮定は正しく、1だったら仮定が誤りとして値を計算できる。
     */
    public int kthGrammar2_2(int n, int k) {
        int assumption = 1;

        while (n > 1) {
            int totalElements = (int) Math.pow(2, n - 1);
            int halfElements = totalElements / 2;
            if (k > halfElements) {
                assumption = 1 - assumption;
                k = k - halfElements;
            } else {
                n--;
            }
        }

        if (assumption == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    /*
     * その列から列が1になるまで列を減らす時、値が入れ替わるのは減らした先の列の値が1の時
     * 列を減らす時、2^a減る。2^x減り続けるとすると、
     * k - 2^a - 2^b ... = 1となり、
     * k - 1 = 2^a + 2^b ... となる。
     * 項の数だけ値が入れ替わり、その項が0でないのは列の値が1の時。
     */
    public int kthGrammar2_3(int n, int k) {
        int bitCount = Integer.bitCount(k - 1);
        return bitCount % 2 == 0 ? 0 : 1;
    }
}
