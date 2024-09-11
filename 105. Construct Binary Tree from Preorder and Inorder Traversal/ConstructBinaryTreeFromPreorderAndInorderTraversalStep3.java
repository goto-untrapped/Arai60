public class ConstructBinaryTreeFromPreorderAndInorderTraversalStep3 {
    // 8min
    class Solution {
        private int preorderIndex = 0;

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            Map<Integer, Integer> inorderValToIndex = new HashMap<>();
            for (int i = 0; i < inorder.length; i++) {
                inorderValToIndex.put(inorder[i], i);
            }
            return buildTree(preorder, inorderValToIndex, 0, inorder.length);
        }

        private TreeNode buildTree(int[] preorder, Map<Integer, Integer> inorderValToIndex, int first, int last) {
            if (first == last) {
                return null;
            }
            int rootVal = preorder[preorderIndex];
            TreeNode root = new TreeNode(rootVal);
            int inorderRootIndex = inorderValToIndex.get(rootVal);
            preorderIndex++;
            root.left = buildTree(preorder, inorderValToIndex, first, inorderRootIndex);
            root.right = buildTree(preorder, inorderValToIndex, inorderRootIndex + 1, last);
            return root;
        }
    }
}
