public class BinaryTreeZigzagLevelOrderTraversalStep3 {
    // 7m40s
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
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
        for (int i = 1; i < allLevelVals.size(); i += 2) {
            Collections.reverse(allLevelVals.get(i));
        }
        return allLevelVals;
    }
    /*
     * 思ったこと
     * ・書いていて一番間違えにくいと思う流れで書いた。
     */
}
