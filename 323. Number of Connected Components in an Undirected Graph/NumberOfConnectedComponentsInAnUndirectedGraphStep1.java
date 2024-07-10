public class NumberOfConnectedComponentsInAnUndirectedGraphStep1 {
    /*
     * 20min
     * 問題の意味を誤解していた後、継ぎ接ぎ(※１)で書いたため、汚い。(existedの処理)
     * なんというか、小さな目的のために大層に書いている感じがする。
     * ・existedの処理は関係性に表れていないノードが欲しいだけだけどO(n)の時間は取られてしまう
     * ・なんか変数を有効活用できていない方法で書いている気がする。おそらくedgesを主軸で考えていたため。
     * DFSはオーバーフローエラーがちらついて、できればBFSで書こうといつも思っている。
     * 時間計算量：O(len(edges)^2) 一番遅いケースは下記のイメージ。
     * ・(0,1) ... (2,3)(1,2) => for i がO(len) while以降でlen^2 => len^2 + len
     * 空間計算量：O(len(edges))
     * チラッと回答を見たところ、隣接リストで解いていた。
     * 前に解いたこともあり、解法の検討中に行列を思いつきはしたが、プログラムにすることまでの距離が遠く、書けなかった。
     * 
     * ※１：はじめ、1hくらいかかって下記のケースが理解できなかったが、edgesはノードの関係性を表しているだけで、
     * 関係性がないnodeは現れないだけだと気付いた。
     * うーん。ほんとうによくない。
     */
    public int countComponents(int n, int[][] edges) {
        Queue<int[]> connected = new LinkedList<>();
        boolean[] seen = new boolean[edges.length];
        int numComponents = 0;
        for (int i = 0; i < edges.length; i++) {
            if (seen[i]) {
                continue;
            }
            connected.offer(new int[] {edges[i][0], edges[i][1]});
            seen[i] = true;
            while (!connected.isEmpty()) {
                int[] edge = connected.poll();
                int first = edge[0];
                int last = edge[1];
                for (int j = i + 1; j < edges.length; j++) {
                    if (seen[j]) {
                        continue;
                    }
                    if (first != edges[j][0] && first != edges[j][1] && last != edges[j][0] && last != edges[j][1]) {
                        continue;
                    }
                    connected.offer(new int[] {edges[j][0], edges[j][1]});
                    seen[j] = true;
                }
            }
            numComponents++;
        }

        HashSet<Integer> existed = new HashSet<>();
        for (int[] edge : edges) {
            existed.add(edge[0]);
            existed.add(edge[1]);
        }
        return numComponents + n - existed.size();
    }
}
