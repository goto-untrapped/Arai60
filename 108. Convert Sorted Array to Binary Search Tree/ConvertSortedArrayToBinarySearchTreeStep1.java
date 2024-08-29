public class ConvertSortedArrayToBinarySearchTreeStep1 {
    /*
     * pre-order
     * 時間計算量:O(n)
     * 空間計算量:O(n)
     * 再帰をするうえで必要になる空間計算量はO(logN)、を解答で確認。
     * インデックスを毎回2で割っているため、最大でもlogNの深さで返り始めると理解した。
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBSTHelper(nums, 0, nums.length - 1);
    }
    private TreeNode sortedArrayToBSTHelper(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int rootIndex = (left + right) / 2;
        TreeNode root = new TreeNode(nums[rootIndex]);
        root.left = sortedArrayToBSTHelper(nums, left, rootIndex - 1);
        root.right = sortedArrayToBSTHelper(nums, rootIndex + 1, right);
        return root;
    }
    /*
     * 思ったこと
     * ・left = right の時、そのノードがそのまま子ノードになる。
     */
    
    
    /*
     * BFSで入れ物2つ使って、インデックスを指定しながらやればできると思ったけど、
     * 書けなかった。50min
     * というかまた再帰でやってみるの忘れてた・・・。
     * まずは解くこと。時間がかかりすぎていることにもっと早く気付いてやり方を変えてみよう・・・。
     * インデックスの範囲がかぶらないか心配してた。
     * 正しく左右でノードを配置できたか心配してた。
     * やるとしても関数化するとかして、一気にやるのやめたほうがいいと思った。
     */
    class Solution_WA {
        public TreeNode sortedArrayToBST(int[] nums) {
            int rootIndex = getCenterIndex(nums, 0, nums.length - 1);
            TreeNode root = new TreeNode(rootIndex);
            List<NodeAndIndex> nodeAndIndexes = new LinkedList<>();
            nodeAndIndexes.add(new NodeAndIndex(root, 0, rootIndex));
            nodeAndIndexes.add(new NodeAndIndex(root, rootIndex, nums.length - 1));
            while (!nodeAndIndexes.isEmpty()) {
                List<NodeAndIndex> nextNodeAndIndexes = new LinkedList<>();
                for (NodeAndIndex nodeAndIndex : nodeAndIndexes) {
                    TreeNode node = nodeAndIndex.node();
                    int left = nodeAndIndex.left();
                    int right = nodeAndIndex.right();
                    if (left < right) {
                        int nodeIndex = getCenterIndex(nums, left, right);
                        node.left = new TreeNode(nums[nodeIndex]);
                        if (left + 1 < nodeIndex) {
                            nextNodeAndIndexes.add(new NodeAndIndex(node, left, nodeIndex));
                            nextNodeAndIndexes.add(new NodeAndIndex(node, nodeIndex, right));
                        }
                    }
    
                    node = nodeAndIndex.node();
                    left = nodeAndIndex.left();
                    right = nodeAndIndex.right();
                    if (left < right) {
                        int nodeIndex = getCenterIndex(nums, left, right);
                        node.right = new TreeNode(nums[nodeIndex]);
                        if (left + 1 < nodeIndex) {
                            nextNodeAndIndexes.add(new NodeAndIndex(root, left, nodeIndex));
                            nextNodeAndIndexes.add(new NodeAndIndex(root, nodeIndex, right));
                        }
                    }
                }
                nodeAndIndexes = nextNodeAndIndexes;
            }
            return root;
        }
        record NodeAndIndex(TreeNode node, int left, int right) {}
    
        private int getCenterIndex(int[] nums, int left, int right) {
            return nums[(left + right) / 2];
        }
    }
}
