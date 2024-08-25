public class MergeTwoBinaryTreesStep2 {
    // DFS スタックで
    class Solution2_1 {
        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            if (root1 == null) {
                return root2;
            }
            Stack<TreeNode[]> mergeNodesGroups = new Stack<>();
            mergeNodesGroups.push(new TreeNode[] {root1, root2});
            while (!mergeNodesGroups.isEmpty()) {
                TreeNode[] mergeNodes = mergeNodesGroups.pop();
                TreeNode node1 = mergeNodes[0];
                TreeNode node2 = mergeNodes[1];
                if (node1 == null || node2 == null) {
                    continue;
                }
                node1.val += node2.val;
                if (node1.left == null) {
                    node1.left = node2.left;
                } else {
                    mergeNodesGroups.push(new TreeNode[] {node1.left, node2.left});
                }
                if (node1.right == null) {
                    node1.right = node2.right;
                } else {
                    mergeNodesGroups.push(new TreeNode[] {node1.right, node2.right});   
                }
            }
            return root1;
        }
    }
    /*
     * 思ったこと
     * ・パターンによって異なる箇所のステートメントを組み合わせていて、またそのため同じ処理を行う時にパターンによって処理のタイミングが
     * 違っているように感じられて、理解するのに時間がかかった。
     * 　・root1, root2のあるなしで分岐をする必要があり、そのパターンはある/なし×2ノードの4パターンがある。
     * 　　・root1がある、root2がある ⇒ ループに入る
     * 　　・root1がある、root2がない ⇒ ループに入ってすぐcontinueで抜けて最後のreturnに移る
     * 　　・root1がない、root2がある/ない ⇒ 最初のifですぐにreturnする
     * 　・node1, node2の子ノードのあるなしで分岐をする必要があり、そのパターンはある/なし×2ノードの4パターンがある。
     * 　　・node1.leftがある、node2.leftがある ⇒ stack.push()してcontinueせずにそれぞれの値を足す
     * 　　・node1.leftがある、node2.leftがない ⇒ stack.push()してcontinueする
     * 　　・node1.leftがない、node2.leftがある/ないの２パターン ⇒ stack.push()せずにnode1.leftに代入する
     * うーん、まとめて理解しようとするから大変なのかな。条件ごとに分けてそれだけの流れを追えばいいのかな。
     */
    
    
    // 手計算でふつうに足していったらStep1になりそう
    // 帰りがけに計算してみる
    // https://github.com/sakupan102/arai60-practice/pull/24/files
    class Solution2_2 {
        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            if (root1 == null) {
                return root2;
            }
            if (root2 == null) {
                return root1;
            }
            int mergedValue = root1.val + root2.val;
            TreeNode leftNode = mergeTrees(root1.left, root2.left);
            TreeNode rightNode = mergeTrees(root1.right, root2.right);
            return new TreeNode(mergedValue, leftNode, rightNode);
        }
    }
    /*
     * 思ったこと
     * ・中途半端に非破壊的。deepcopyが標準ライブラリに含まれていないので、そういう関数があることにしておけばいい気がする。
     * やるとしたら、
     * 　https://stackoverflow.com/questions/64036/how-do-you-make-a-deep-copy-of-an-object
     * 　　・clone()は要注意？確認。また実装するにはTreeNodeにインターフェースを実装する必要がある
     * 　　・serialize/deserializeを使う：サードライブラリにはある。こちらも実装するには、
     * TreeNodeにインターフェースを実装する必要がある
     * https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/commons/lang3/SerializationUtils.java#L119
     * でも別で、自力でdeepcopyをするか一回書いてみたほうがいいか。
     */
    
    
    // 帰りがけでTreeを統合する 破壊的
    // https://github.com/hayashi-ay/leetcode/pull/12/files
    class Solution2_3 {
        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            if (root1 == null && root2 == null) {
                return null;
            }
            if (root2 == null) {
                TreeNode mergedNode = new TreeNode(root1.val);
                mergedNode.left = root1.left;
                mergedNode.right = root1.right;
                return mergedNode;
            }
            if (root1 == null) {
                TreeNode mergedNode = new TreeNode(root2.val);
                mergedNode.left = root2.left;
                mergedNode.right = root2.right;
                return mergedNode;
            }
            TreeNode mergedNode = new TreeNode(root1.val + root2.val);
            mergedNode.left = mergeTrees(root1.left, root2.left);
            mergedNode.right = mergeTrees(root1.right, root2.right);
            return mergedNode;
        }
    }
    /*
     * 思ったこと
     * ・mergedNodeを持ち回って最後に返すのもできると思ったけど、1つのifの処理が増えた時に
     * 見ることが大変そうなので、returnした方がいいかも。
     */
    
    
    // 非破壊的再帰スタック
    class Solution2_4 {
        public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
            if (root1 == null && root2 == null) {
                return null;
            }
            if (root2 == null) {
                // return deepcopy(root1);
                return root1;
            }
            if (root1 == null) {
                return root2;
            }

            Stack<TreeNode[]> mergeNodesGroups = new Stack<>();
            TreeNode mergedNode = new TreeNode(root1.val + root2.val);
            mergeNodesGroups.push(new TreeNode[] {mergedNode, root1, root2});
            while (!mergeNodesGroups.isEmpty()) {
                TreeNode[] mergeNode = mergeNodesGroups.pop();
                TreeNode root = mergeNode[0];
                TreeNode node1 = mergeNode[1];
                TreeNode node2 = mergeNode[2];
                if (node1.left == null) {
                    // root.left = deepcopy(node2.left);
                    root.left = node2.left;
                }
                if (node1.right == null) {
                    root.right = node2.right;
                }
                if (node2.left == null) {
                    root.left = node1.left;
                }
                if (node2.right == null) {
                    root.right = node1.right;
                }
                if (node1.left != null && node2.left != null) {
                    root.left = new TreeNode(node1.left.val + node2.left.val);
                    mergeNodesGroups.push(new TreeNode[] {root.left, node1.left, node2.left});
                }
                if (node1.right != null && node2.right != null) {
                    root.right = new TreeNode(node1.right.val + node2.right.val);
                    mergeNodesGroups.push(new TreeNode[] {root.right, node1.right, node2.right});
                }
            }
            return mergedNode;
        }
    }
    /*
     * 思ったこと
     * ・deepcopy()を書く必要がある箇所のパターンはコメントアウトしたところの想定。
     */
}
/*
 * https://discord.com/channels/1084280443945353267/1183683738635346001/1203778352050077706
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1204041603468103740
 *   https://github.com/hayashi-ay/leetcode/pull/12
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1217139317554679949
 *   https://github.com/shining-ai/leetcode/pull/23
 * https://discord.com/channels/1084280443945353267/1196472827457589338/1236595113552121917
 *   https://github.com/Mike0121/LeetCode/pull/9
 * https://github.com/sakupan102/arai60-practice/pull/24
 * https://github.com/fhiyo/leetcode/pull/25
 * https://github.com/SuperHotDogCat/coding-interview/pull/35
 * https://github.com/kazukiii/leetcode/pull/24
 * https://github.com/TORUS0818/leetcode/pull/25
 * https://github.com/nittoco/leetcode/pull/30/files
 * https://github.com/Ryotaro25/leetcode_first60/pull/25
 */
