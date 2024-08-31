public class PathSumStep3 {
    // 1m30s / 3m10s / 3min
    class Solution {
        public boolean hasPathSum(TreeNode root, int targetSum) {
            return hasPathSumHelper(root, targetSum, 0);
        }
        
        private boolean hasPathSumHelper(TreeNode node, int targetSum, int sum) {
            if (node == null) {
                return false;
            }
            sum += node.val;
            if (node.left == null && node.right == null) {
                return sum == targetSum;
            }
            if (hasPathSumHelper(node.left, targetSum, sum)) {
                return true;
            }
            return hasPathSumHelper(node.right, targetSum, sum);
        }
    }
    /*
     * 思ったこと
     * ・引いていけば1つの関数で足りたけど、自分が手計算する時に合わせた。
     * ・leftは結果を判定として受け取るけど、orよりは1本の道を見つけた時点で計算をやめることが
     * 読みやすいのかなと思った。
     */
}
