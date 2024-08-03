public class PaintFenceStep1 {
    /*
     * 1hくらい
     * 20minくらい考えたけど分からず答えを見た。
     * 時間計算量：O(n)
     * 空間計算量：O(n)
     */
    public int numWays(int n, int k) {
        if (n == 1) {
            return k;
        }
        if (n == 2) {
            return k * k;
        }
        int[] totalWays = new int[n + 1];
        totalWays[1] = k;
        totalWays[2] = k * k;
        for (int i = 3; i < totalWays.length; i++) {
            totalWays[i] = (k - 1) * (totalWays[i - 1] + totalWays[i - 2]);
        }
        return totalWays[totalWays.length - 1];
    }
    /*
     * 思ったこと
     * ・最初バックトラッキングで考えていたけど、前の組み合わせが今の組み合わせに影響するから、DPか。
     * ・DPの分け方として、前と同じ色を塗った時と、別の色を塗った時で分けて、今回塗れる色の数を計算して、和を出すのか。
     */
}
