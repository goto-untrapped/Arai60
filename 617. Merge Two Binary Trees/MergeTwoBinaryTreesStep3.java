public class MergeTwoBinaryTreesStep3 {
    // 2m10s / 1min
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            // return deepcopy(root2);
            return root2;
        }
        if (root2 == null) {
            // return deepcopy(root1);
            return root1;
        }
        TreeNode mergedNode = new TreeNode(root1.val + root2.val);
        mergedNode.left = mergeTrees(root1.left, root2.left);
        mergedNode.right = mergeTrees(root1.right, root2.right);
        return mergedNode;
    }
    /*
     * 思ったこと
     * ・非破壊の方がいいと思った。
     */
}
