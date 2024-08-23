public class MinimumDepthOfBinaryTreeStep1 {
    /*
     * 12min
     * 時間計算量:O(n)
     * 空間計算量:O(n)
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int minDepth = Integer.MAX_VALUE;
        Stack<NodeAndDepth> depthInfos = new Stack<>();
        depthInfos.add(new NodeAndDepth(root, 1));
        while (!depthInfos.isEmpty()) {
            NodeAndDepth info = depthInfos.pop();
            if (info.node.left == null && info.node.right == null) {
                minDepth = Math.min(minDepth, info.depth);
                continue;
            }
            if (info.node.left != null) {
                depthInfos.add(new NodeAndDepth(info.node.left, info.depth + 1));
            }
            if (info.node.right != null) {
                depthInfos.add(new NodeAndDepth(info.node.right, info.depth + 1));
            }
        }
        return minDepth;
    }
    record NodeAndDepth(TreeNode node, int depth) {}
}
