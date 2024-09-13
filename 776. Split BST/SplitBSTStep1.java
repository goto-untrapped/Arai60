public class SplitBSTStep1 {
    /*
     * 答えを見た。2hくらい。
     * 時間計算量:O(logn)
     * 空間計算量:O(logn)
     */
    class Solution {
        public TreeNode[] splitBST(TreeNode root, int target) {
            if (root ==  null) {
                return new TreeNode[2];
            }

            if (target < root.val) {
                TreeNode[] left = splitBST(root.left, target);
                root.left = left[1];
                return new TreeNode[] {left[0], root};
            } else {
                TreeNode[] right = splitBST(root.right, target);
                root.right = right[0];
                return new TreeNode[] {root, right[1]};
            }
        }
    }
    /*
     * ・分かっていない。
     * ・日本語で考えたら確かにそうなりそうだけど、これで色々なパターンが網羅できていることがピンと来ていない。
     */
}
