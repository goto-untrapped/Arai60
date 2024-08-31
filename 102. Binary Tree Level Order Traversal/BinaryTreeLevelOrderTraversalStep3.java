public class BinaryTreeLevelOrderTraversalStep3 {
    // 5min / 4m30s
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> allLevelVals = new ArrayList<>();
        List<TreeNode> sameLevelNodes = new ArrayList<>();
        sameLevelNodes.add(root);
        while (!sameLevelNodes.isEmpty()) {
            List<Integer> sameLevelVals = new ArrayList<>();
            List<TreeNode> nextLevelNodes = new ArrayList<>();
            for (TreeNode node : sameLevelNodes) {
                sameLevelVals.add(node.val);
                if (node.left != null) {
                    nextLevelNodes.add(node.left);
                }
                if (node.right != null) {
                    nextLevelNodes.add(node.right);
                }
            }
            allLevelVals.add(sameLevelVals);
            sameLevelNodes = nextLevelNodes;
        }
        return allLevelVals;
    }
}
