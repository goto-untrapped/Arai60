public class ConstructBinaryTreeFromPreorderAndInorderTraversalStep2 {
    
    // DFS 配列を渡す
    // https://github.com/hayashi-ay/leetcode/pull/43/files#diff-e9b6f8cf3e6bf93b2177690b6fb60c6eaa153a33e3acaf7bad26c23ebd604dcaR12
    class Solution2_1 {
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            if (preorder.length == 0) {
                return null;
            }
            int rootVal = preorder[0];
            TreeNode root = new TreeNode(rootVal);
            List<Integer> inorderList = Arrays.stream(inorder).boxed().collect(Collectors.toList());
            int inorderRootIndex = inorderList.indexOf(rootVal);
            root.left = buildTree(Arrays.copyOfRange(preorder, 1, 1 + inorderRootIndex),
                                  Arrays.copyOfRange(inorder, 0, inorderRootIndex));
            root.right = buildTree(Arrays.copyOfRange(preorder, inorderRootIndex + 1, preorder.length),
                                   Arrays.copyOfRange(inorder, inorderRootIndex + 1, inorder.length));
            return root;
        }
    }
    
    // Solution2_1 をリストで書き直した
    class Solution2_2 {
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            List<Integer> preorderVals = Arrays.stream(preorder).boxed().collect(Collectors.toList());
            List<Integer> inorderVals = Arrays.stream(inorder).boxed().collect(Collectors.toList());

            return buildTree(preorderVals, inorderVals);
        }

        private TreeNode buildTree(List<Integer> preorderVals, List<Integer> inorderVals) {
            if (preorderVals.size() == 0) {
                return null;
            }
            int rootVal = preorderVals.get(0);
            TreeNode root = new TreeNode(rootVal);
            int inorderRootIndex = inorderVals.indexOf(rootVal);
            root.left = buildTree(preorderVals.subList(1, 1 + inorderRootIndex),
                                  inorderVals.subList(0, inorderRootIndex));
            root.right = buildTree(preorderVals.subList(inorderRootIndex + 1, preorderVals.size()),
                                   inorderVals.subList(inorderRootIndex + 1, inorderVals.size()));
            return root;
        }
    }
    /*
     * ・2_1, 2_2を比べると、ランタイムとメモリ使用量が改善された。
     * 80ms -> 25ms
     * 90MB -> 44.5MB
     * 関数呼び出しごとに配列からリストへ変換していることが原因だと思った。
     * ・inorderでインデックスを探すのは、Mapを作ってもできる。
     */
    
    
    // DFS rootIndexとインデックスのMapをメンバー変数にする
    class Solution2_3 {
        private int[] preorderVals;
        private int preorderIndex = 0;
        private Map<Integer, Integer> inorderValToIndex;

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            preorderVals = preorder;
            inorderValToIndex = new HashMap<>();
            for (int i = 0; i < inorder.length; i++) {
                inorderValToIndex.put(inorder[i], i);
            }
            return buildTree(0, inorder.length);
        }

        private TreeNode buildTree(int first, int last) {
            if (first == last) {
                return null;
            }
            int rootVal = preorderVals[preorderIndex];
            TreeNode root = new TreeNode(rootVal);
            preorderIndex++;
            int inorderRootIndex = inorderValToIndex.get(rootVal);
            root.left = buildTree(first, inorderRootIndex);
            root.right = buildTree(inorderRootIndex + 1, last);
            return root;
        }
    }
    /*
     * ・Mapを使うと処理がシンプルになる。
     */
    
    
    // DFS スタック
    // https://github.com/fhiyo/leetcode/pull/31/files#diff-588e20b8d5d13270d9f58e900c601cc9e72e7aa9c9d083442b5e0334dcbee61aR109
    class Solution2_4 {
        record RootAndSubtreePlace(TreeNode root, int preorderFirstIndex, int inorderFirstIndex, int size) {}

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            Map<Integer, Integer> inorderValToIndex = new HashMap<>();
            for (int i = 0; i < inorder.length; i++) {
                inorderValToIndex.put(inorder[i], i);
            }

            Stack<RootAndSubtreePlace> rootAndSubtreePlaces = new Stack<>();
            TreeNode root = new TreeNode(preorder[0]);
            rootAndSubtreePlaces.push(new RootAndSubtreePlace(root, 0, 0, preorder.length));
            while (!rootAndSubtreePlaces.isEmpty()) {
                RootAndSubtreePlace rootAndSubtreePlace = rootAndSubtreePlaces.pop();
                TreeNode node = rootAndSubtreePlace.root();
                int preorderFirstIndex = rootAndSubtreePlace.preorderFirstIndex();
                int inorderFirstIndex = rootAndSubtreePlace.inorderFirstIndex();
                int size = rootAndSubtreePlace.size();

                int inorderRootIndex = inorderValToIndex.get(preorder[preorderFirstIndex]);
                int leftTreeSize = inorderRootIndex - inorderFirstIndex;
                int rightTreeSize = size - leftTreeSize - 1;

                if (leftTreeSize > 0) {
                    int leftTreeRootIndex = preorderFirstIndex + 1;
                    node.left = new TreeNode(preorder[leftTreeRootIndex]);
                    rootAndSubtreePlaces.push(new RootAndSubtreePlace(node.left, leftTreeRootIndex, inorderFirstIndex, leftTreeSize));
                }
                if (rightTreeSize > 0) {
                    int rihgtTreeRootIndex = preorderFirstIndex + leftTreeSize + 1;
                    node.right = new TreeNode(preorder[rihgtTreeRootIndex]);
                    rootAndSubtreePlaces.push(new RootAndSubtreePlace(node.right, rihgtTreeRootIndex, inorderRootIndex + 1, rightTreeSize));
                }
            }

            return root;
        }
    }
    /*
     * ・難しい。
     */

}
/*
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1215307045008310313
 *   https://github.com/hayashi-ay/leetcode/pull/43
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1219907804991590412
 *   https://github.com/shining-ai/leetcode/pull/29
 * https://discord.com/channels/1084280443945353267/1196472827457589338/1237821775589412946
 *   https://github.com/Mike0121/LeetCode/pull/12
 * https://github.com/sakupan102/arai60-practice/pull/30
 * https://discord.com/channels/1084280443945353267/1192736784354918470/1247343552262963240
 *   https://github.com/YukiMichishita/LeetCode/pull/12
 * https://github.com/fhiyo/leetcode/pull/31
 * https://github.com/kazukiii/leetcode/pull/30
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/33
 * https://github.com/TORUS0818/leetcode/pull/31
 * https://github.com/Ryotaro25/leetcode_first60/pull/31
 */
