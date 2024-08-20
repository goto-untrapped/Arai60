public class MaximumDepthOfBinaryTreeStep3 {
    // 5min / 3min
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int maxDepth = 0;
        Stack<NodeAndDepth> depthInfos = new Stack<>();
        depthInfos.add(new NodeAndDepth(root, 1));
        while (!depthInfos.isEmpty()) {
            NodeAndDepth info = depthInfos.pop();
            maxDepth = Math.max(maxDepth, info.depth);
            if (info.node.left != null) {
                depthInfos.add(new NodeAndDepth(info.node.left, info.depth + 1));
            }
            if (info.node.right != null) {
                depthInfos.add(new NodeAndDepth(info.node.right, info.depth + 1));
            }
        }
        return maxDepth;
    }
    record NodeAndDepth(TreeNode node, int depth) {}
    /*
     * 思ったこと
     * ・レコードはクラスでもいい。
     * ・スタックやレコードのインスタンスの変数名がかぶるとタイプミスをしやすかったので、違う名前にした。
     */
}
