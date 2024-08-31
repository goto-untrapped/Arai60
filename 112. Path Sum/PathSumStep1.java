public class PathSumStep1 {
    
    // 解答を確認
    // 時間計算量:O(n)
    // 空間計算量:O(n)
    class Solution {
        public boolean hasPathSum(TreeNode root, int targetSum) {
            return hasPathSumHelper(root, targetSum);
        }

        private boolean hasPathSumHelper(TreeNode node, int sum) {
            if (node == null) {
                return false;
            }
            sum -= node.val;
            if (node.left == null && node.right == null) {
                return sum == 0;
            }
            return hasPathSumHelper(node.left, sum) || hasPathSumHelper(node.right, sum);
        }
    }
    
    // 20min WA
    // [1],1:true かつ [1,2]:false ができなかった
    // 時間計算量:O(n)
    // 空間計算量:O(n)
    class Solution_WA {
        public boolean hasPathSum(TreeNode root, int targetSum) {
            if (root == null) { 
                return false;
            }
            return hasPathSumHelper(root, targetSum, root.val);
        }
        private boolean hasPathSumHelper(TreeNode node, int targetSum, int sum) {
            if (sum == targetSum) {
                return true;
            }
            if (sum > targetSum) {
                return false;
            }
            boolean hasLeftPathSum = false;
            if (node.left != null) {
                hasLeftPathSum = hasPathSumHelper(node.left, targetSum, sum + node.left.val);
            }
            boolean hasRightPathSum = false;
            if (node.right != null) {
                hasRightPathSum = hasPathSumHelper(node.right, targetSum, sum + node.right.val);
            }
            return hasLeftPathSum || hasRightPathSum;
        }
    }
}
