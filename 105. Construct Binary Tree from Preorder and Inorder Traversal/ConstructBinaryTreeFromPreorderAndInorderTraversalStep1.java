public class ConstructBinaryTreeFromPreorderAndInorderTraversalStep1 {
    /*
     * 10min考えたが手が動かなかった
     * 数の並びから何とか法則は導き出そうとした
     * ⇒ つまりどういうことか？の深掘りができていない
     * 2hくらいかかった
     * 時間計算量:O(n^2)
     * ⇒ 初めO(n)と思ったけど、indexOf()がO(n)だった。
     * https://github.com/hayashi-ay/leetcode/pull/43/files
     * https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/ArrayList.java#L286
     * 空間計算量:O(n)
     */
    class Solution {
        private int rootIndex = 0;

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            return buildTreeHelper(preorder, inorder, 0, inorder.length - 1);
        }

        private TreeNode buildTreeHelper(int[] preorder, int[] inorder, int subtreeFirstIndex, int subtreeLastIndex) {
            if (subtreeFirstIndex > subtreeLastIndex) {
                // 左のサブツリーがない場合は戻す
                rootIndex--;
                return null;
            }

            int rootVal = preorder[rootIndex];
            TreeNode root = new TreeNode(rootVal);
            List<Integer> inorderVals = Arrays.stream(inorder).boxed().collect(Collectors.toList());
            int leftSubtreeLastIndex = inorderVals.indexOf(rootVal) - 1;
            int rightSubtreeFirstIndex = inorderVals.indexOf(rootVal) + 1;
            rootIndex++;
            root.left = buildTreeHelper(preorder, inorder, subtreeFirstIndex, leftSubtreeLastIndex);
            rootIndex++;
            root.right = buildTreeHelper(preorder, inorder, rightSubtreeFirstIndex, subtreeLastIndex);
            return root;
        }
    }
    /*
     * ・初め、rootIndexを引数で取っていたが、左のサブツリーがない場合、右のサブツリーはrootIndex+1が親ノードになるため、
     * rootIndexをメンバー変数とした。
     * ・次のサブツリーの範囲は今のrootIndexから求めるが、次の親ノードのindexはrootIndex自体を変える必要があるので、
     * leftSubtreeLastIndexとrightSubtreeFirstIndexの定義場所と使用場所が離れてしまった。
     * rootIndexの処理も一緒にごちゃっと書いていて、分かりにくいと思った。
     * ・変更しにくいコードなので、分かりにくいと思った。　
     * 　・複数のことを同時にやっていて読み取りにくい
     * 　・少しでも流れが違うとバグる ⇒ rootIndex++/--の位置、rootIndexと再帰呼び出しの順番が固定
     */
}
