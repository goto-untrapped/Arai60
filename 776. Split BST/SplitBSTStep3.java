public class SplitBSTStep3 {
    // 5min
    class Solution {
        public TreeNode[] splitBST(TreeNode root, int target) {
            if (root == null) {
                return new TreeNode[2];
            }

            if (target < root.val) {
                TreeNode[] left = splitBST(root.left, target);
                root.left = left[1];
                return new TreeNode[] { left[0], root };
            } else {
                TreeNode[] right = splitBST(root.right, target);
                root.right = right[0];
                return new TreeNode[] { root, right[1] };
            }
        }
    }
}
