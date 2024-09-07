public class ValidateBinarySearchTreeStep3 {
    // 5m40s
    class Solution {
        public boolean isValidBST(TreeNode root) {
            return isValidBSTHelper(root, null, null);
        }

        private boolean isValidBSTHelper(TreeNode node, Integer lower, Integer upper) {
            if (node == null) {
                return true;
            }
            if (lower != null && !(lower < node.val)) {
                return false;
            }
            if (upper != null && !(node.val < upper)) {
                return false;
            }
            boolean isLeftValidBST = isValidBSTHelper(node.left, lower, node.val);
            boolean isRightValidBST = isValidBSTHelper(node.right, node.val, upper);
            return isLeftValidBST && isRightValidBST;
        }
    }
    /*
     * ・return helper() && helper() すると1文が長いと思った。
     */
}
