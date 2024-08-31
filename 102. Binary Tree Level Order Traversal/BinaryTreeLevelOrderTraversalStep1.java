public class BinaryTreeLevelOrderTraversalStep1 {
    /*
     * 20min
     * 時間計算量:O(n)
     * 空間計算量:O(n + n/2 + n/2 * 2) = O(n)
     *   allLevelVals：n個のノードを収めるため、O(n)
     *   sameLevelNodes：一番深いlognの深さの時、ノードの数はn / 2ノード 個
     *   nextLevelVals：一番深いlognの深さの時、ノードの数はn / 2ノード * 次の深さの2 個
     * 実行して2回デバッグ
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> allLevelVals = new ArrayList<>();
        List<TreeNode> sameLevelNodes = new ArrayList<>();
        TreeNode sentinel = new TreeNode();
        sentinel.left = root;
        sameLevelNodes.add(sentinel);
        while (!sameLevelNodes.isEmpty()) {
            List<TreeNode> nextLevelNodes = new ArrayList<>();
            List<Integer> nextLevelVals = new ArrayList<>();
            for (TreeNode node : sameLevelNodes) {
                if (node.left != null) {
                    nextLevelNodes.add(node.left);
                    nextLevelVals.add(node.left.val);
                }
                if (node.right != null) {
                    nextLevelNodes.add(node.right);
                    nextLevelVals.add(node.right.val);
                }
            }
            if (nextLevelVals.size() > 0) {
                allLevelVals.add(nextLevelVals);
            }
            sameLevelNodes = nextLevelNodes;
        }
        return allLevelVals;
    }
    /*
     * 上記ソースのややこしい所
     * ・一見nullはListに入らないが、level+1のvalをlevelの段階で格納しているので、
     * 最後の深さのvalを格納し終えてもノードListが空にならず、次のlevelに行って空のval[]を格納してしまう。
     * （rootをループで処理したいがために番兵を使ってみたら処理の流れが思っていたのと違った）
     * 
     * 解く過程
     * ・同じレベルのものがほしいのか
     * ・手でやるんだったら図を書いて同じレベルのものを見つけるけど。
     * ・BFSだったら見に行く先が手計算と同じになるな。
     * ・次のレベルの入れ物を用意して、今のレベルに入れ替え続ければループでできるな。
     * 　先入れ後出しだからQueueか。いや、今のレベルはループして子を探すからListでいけるか。
     * ・全体の流れ行けそうなので書いてみよう。
     * ・あれ、2つ目の入れ物で子をループしたらrootが全レベルに入らないな。
     * ・テクニックだけど、番兵を用意すればrootもleftとして処理を統一できるか。
     * ・書き終わったのでエラーにならないか確認。あ、ほしいのは値じゃん。
     * ・値が欲しい所だけ変数を用意すればいいか。
     * ・これでいけそう。Submitしてみる。
     * ・全レベルにaddAllして怒られる。addAllは中身が入れ先と同じ型で追加するから、addでいいのか。再Submit。
     * ・全レベルの最後に[]が常に入り怒られる。
     * ・値が入っている時だけ全レベルに入るようにif文を入れよう。応急処置感がある。
     * 
     * 思ったこと
     * ・ほしいのは値。やる必要のあることを全体を頭に入れてからやり方を考える。
     * ・最後までデバッグしきってからいけるかどうかを判断する。
     * ・書く前に書いたコードがどう動くかがんばってもっと正確に想像する。
     * ・番兵使わなくてもできるやり方ありそう。
     * ・同じように書いてもif文を使わずに正しく格納できるやり方ありそう。
     */
}
