public class PowStep2 {
    /*
     * Step1 書き直し
     * 1/(2^3) と見ると、pow を計算した後に最後にどうやって計算するのか覚えておくのがめんどうなので、
     * それよりは (1/2)^3 と見た方が分かりやすいと思った
     * 
     * long を付け忘れ。
     * キャストを考えた時、 n を使いまわして型変換できるのはシンプルに書けるし、
     * Helperという名前も型変換が目的であればちょうどいいと思った。
     */
    public double myPow2_1(double x, int n) {
        return myPowHelper2_1(x, (long)n);
    }
    private double myPowHelper2_1(double x, long n) {
        if (n == 0) return 1;
        if (n < 0) return myPowHelper2_1(1 / x, -n);

        if (n % 2 == 1) {
            return x * myPowHelper2_1(x * x, n / 2);
        } else {
            return myPowHelper2_1(x * x, n / 2);
        }
    }
    
    /*
     * iterative
     * if 中の n--; を書き忘れる 動きはする
     * => 2で割った余りを無視できるため
     * でも、実際は1回分掛け算をしているため、書いた方がいい 
     * */
    public double myPow2_2(double x, int n) {
        return myPowHelper2_2(x, (long)n);
    }
    /*
     * double powered = 1; と書いても 1.0 で計算してくれる
     * 1.0 と書くとそう宣言しないといけないのかなって若干違和感があるかもしれないので、
     * = 1; と書くようにする(Javaの仕様として覚えておくもの)
     * というより、コンパイラ言語なんだから、そりゃ解釈してくれるよ、か。
     */
    private double myPowHelper2_2(double x, long n) {
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        double powered = 1.0;
        while (n > 0) {
            if (n % 2 == 1) {
                powered *= x;
            }
            x *= x;
            n /= 2;
        }
        return powered;
    }
    
    /*
     * https://github.com/SuperHotDogCat/coding-interview/pull/15/files
     * こっちが 計算していないものを渡す もらってから計算して返す か、
     * 計算したものを渡す 既に計算しているため、もらったらそのまま返す
     * n は半分にして渡しているから、x も合わせて半分にして渡す方が分かりやすいと思った
     * それに、nが偶数であれば末尾再帰最適化になると思う 
     */
    public double myPow2_3(double x, int n) {
        return myPowHelper2_3(x, (long)n);
    }
    private double myPowHelper2_3(double x, long n) {
        if (n == 0) return 1;
        if (n < 0) return myPowHelper2_3(1 / x, -n);

        if (n % 2 == 1) {
            double nextPowered = myPowHelper2_3(x, n / 2);
            return x * nextPowered * nextPowered;
        } else {
            double nextPowered = myPowHelper2_3(x, n / 2);
            return nextPowered * nextPowered;
        }
    }
    
    /*
     * 1つの関数で書けるところをキャストのために関数化しているくらいであれば、
     * 匿名関数でまとめて書いておくのもいいと思った
     */
    class Solution {
        public double myPow2_4(double x, int n) {
            MyPowHelper myPowHelper = new MyPowHelper() {
                @Override
                public double helper(double x, long n) {
                    if (n == 0) return 1;
                    if (n < 0) return helper(1 / x, -n);

                    if (n % 2 == 1) {
                        return x * helper(x * x, n / 2);
                    } else {
                        return helper(x * x, n / 2);
                    }
                }
            };
            return myPowHelper.helper(x, (long)n);
        }
    }
    interface MyPowHelper {
        double helper(double x, long n);
    }
    
    // https://github.com/hayashi-ay/leetcode/pull/41/files
    // n を2のべき乗として見て、x^n を分解しながら計算する
    // 最下位ビットから
    public double myPow2_5(double x, int n) {
        return myPowHelper2_5(x, (long)n);
    }
    private double myPowHelper2_5(double x, long n) {
        if (n == 0) return 1;
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        double nextDigitTotal = x;
        double total = 1;
        while (n > 0) {
            // べき数を2のべき数で分解した時、その桁のべき数が存在する時は掛け合わせられる
            if ((n & 1) == 1) {
                total *= nextDigitTotal;
            }
            // べき数を2のべき数で分解した時、桁が上がるとべき数+1するため、元の数^2 がその桁の合計になる
            nextDigitTotal *= nextDigitTotal;
            n >>= 1;
        }
        return total;
    }
    
    // 最上位ビットから
    public double myPow2_6(double x, int n) {
        return myPowHelper2_6(x, (long)n);
    }
    private double myPowHelper2_6(double x, long n) {
        if (n == 0) return 1;
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        double total = 1;
        long nextBitToLatest = (long)1 << bitLength(n) - 1;
        while (nextBitToLatest > 0) {
            // べき数を2のべき数で表したとき、1桁遠ざかるごとに、今まで1だった桁数はそれぞれ+1され、
            // xの階乗まで計算すると、それぞれ *= x になる
            total *= total;
            if ((n & nextBitToLatest) > 0) {
                // 2のべき数が1の時、x^ で掛け合わせていく数として追加し、カウントし始める
                total *= x;
            } 
            nextBitToLatest >>= 1;
        }
        return total;
    }
    private int bitLength(long n) {
        BigInteger nBigInt = new BigInteger(Long.toString(n));
        return nBigInt.bitLength();
    }
    
}

/*
 * https://discord.com/channels/1084280443945353267/1210494002277908491/1210521583605776414
 * https://github.com/hayashi-ay/leetcode/pull/41/files
 * https://github.com/shining-ai/leetcode/pull/45/files
 * https://github.com/SuperHotDogCat/coding-interview/pull/15/files
 * 意識したい
 * https://discord.com/channels/1084280443945353267/1196472827457589338/1196473359588929536
 *   負の数の割り算と余り、言語によって振る舞いが色々なので自分の使い慣れたものについては覚えておきましょう。
 */
