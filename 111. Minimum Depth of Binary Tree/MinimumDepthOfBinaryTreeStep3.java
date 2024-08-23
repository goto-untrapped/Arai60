public class MinimumDepthOfBinaryTreeStep3 {
    // 6m10s / 3m40s
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        LinkedList<NodeAndDepth> nodeAndDepths = new LinkedList<>();
        nodeAndDepths.offer(new NodeAndDepth(root, 1));
        while (!nodeAndDepths.isEmpty()) {
            NodeAndDepth nodeAndDepth = nodeAndDepths.poll();
            TreeNode node = nodeAndDepth.node();
            int depth = nodeAndDepth.depth();
            if (node.left == null && node.right == null) {
                return depth;
            }
            if (node.left != null) {
                nodeAndDepths.offer(new NodeAndDepth(node.left, depth + 1));
            }
            if (node.right != null) {
                nodeAndDepths.offer(new NodeAndDepth(node.right, depth + 1));
            }
        }
        return -1; // never reached
    }
    record NodeAndDepth(TreeNode node, int depth) {}
}
