public class MaximumDepthOfBinaryTreeStep1 {
    // 14min
    // 時間計算量:O(n)
    // 空間計算量:O(n)
    public int maxDepth(TreeNode root) {
        return maxDepthHelper(root, 0);
    }

    private int maxDepthHelper(TreeNode node, int numDepth) {
        if (node == null) {
            return numDepth;
        }
        
        int leftNumDepth = maxDepthHelper(node.left, numDepth + 1);
        int rightNumDepth = maxDepthHelper(node.right, numDepth + 1);
        return Math.max(leftNumDepth, rightNumDepth);
    }
}
