public class BinaryTreeZigzagLevelOrderTraversalStep1 {
    /*
     * 19min
     * 時間計算量:O(n)
     * 空間計算量:O(n)
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> allLevelVals = new ArrayList<>();
        List<TreeNode> sameLevelNodes = new ArrayList<>();
        sameLevelNodes.add(root);
        boolean isFromLeftToRight = true;
        while (!sameLevelNodes.isEmpty()) {
            List<TreeNode> nextLevelNodes = new ArrayList<>();
            List<Integer> sameLevelVals = new ArrayList<>();
            if (isFromLeftToRight) {
                for (TreeNode node : sameLevelNodes) {
                    sameLevelVals.add(node.val);
                }
            } else {
                for (int i = sameLevelNodes.size() - 1; i >= 0; i--) {
                    TreeNode sameLevelNode = sameLevelNodes.get(i);
                    sameLevelVals.add(sameLevelNode.val);
                }
            }
            for (TreeNode node : sameLevelNodes) {
                if (node.left != null) {
                    nextLevelNodes.add(node.left);
                }
                if (node.right != null) {
                    nextLevelNodes.add(node.right);
                }
            }
            allLevelVals.add(sameLevelVals);
            sameLevelNodes = nextLevelNodes;
            isFromLeftToRight = !isFromLeftToRight;
        }
        return allLevelVals;
    }
    /*
     * 解く過程
     * ・深さが奇数か偶数かで値の見る向きを変えればできそう
     * ・いや、フラグの方がもっとシンプルにできそう
     * ・前の問題と似ているし、BFSでできそう。
     * ・書き始めたら、次のノードの入れる向きも反対にしようとして、
     * でもそれだと後ろのノードの右、左、前のノードの右、左に入って、2回目の左から右で後ろから見ることになって
     * 同じ処理で書けない？どうしよう
     * ・Dequeにして前から追加するかか後ろから追加するかすればいいのかな。
     * ・よくわかんなくなってきた。もっと単純にListでできないのかな。
     * ・あ、もう前から順にノードを追加して、値の読み込みだけ順番を反対にすればできそう。
     * ・デバッグしてできそうなことを確認し、Submit
     * 思ったこと
     * ・ノードの追加する順番を考えずに書き始めてしまった
     * 　・つまりあらかじめデバッグせずに書き始めてしまった
     * 　　・それで混乱するの当たり前だった
     * 　　　・ちゃんとあらかじめデバッグする
     * ・allLevelVals.add(sameLevelVals);は、sameLevelValsの処理が終わった直後に書いた方が、それ以降使わないと分かって読む負担減りそう。
     */
}
