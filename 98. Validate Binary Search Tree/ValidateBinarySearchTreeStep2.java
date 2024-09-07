public class ValidateBinarySearchTreeStep2 {
    
    // DFS 再帰 pre-order
    // Step1をよりシンプルに書こうとした
    class Solution2_1 {
        private static long MIN_VALUE = Long.MIN_VALUE;
        private static long MAX_VALUE = Long.MAX_VALUE;

        public boolean isValidBST(TreeNode root) {
            return isValidBSTHelper(root, MIN_VALUE, MAX_VALUE);
        }

        private boolean isValidBSTHelper(TreeNode node, long minVal, long maxVal) {
            if (node == null) {
                return true;
            }

            if (!(minVal < node.val && node.val < maxVal)) {
                return false;
            }

            return isValidBSTHelper(node.left, minVal, node.val) && isValidBSTHelper(node.right, node.val, maxVal);
        }
    }
    /*
     * 思ったこと
     * ・-2^31 <= Node.val <= 2^31 - 1 を確認していなかった。
     * ・Integerよりごり押し感がある(望まれてなさそう)
     * ・Step1は答えの通りにIntegerを使ったけど、まだ最小最大が決まっていない時にnullにできるので
     * 取り得る値の上限下限の対応をしなくてもよくて複雑度が減ってよいと思った。また読んでて分かりやすそう。
     */
    
    
    // DFS 再帰 in-order
    class Solution2_2 {
        private Integer minVal = null;

        public boolean isValidBST(TreeNode node) {
            if (node == null) {
                return true;
            }
            if (!isValidBST(node.left)) {
                return false;
            }
            if (minVal != null && !(minVal < node.val)) {
                return false;
            }
            minVal = node.val;
            return isValidBST(node.right);
        }
    }
    /*
     * 思ったこと
     * ・minValをstaticにすると他のテストケースを実施した後に[0]でWAになる。
     * 確かにテストケースを連続で回した方が関数をメモリに載せ直すよりコスパ良さそう。(?)
     * ・Integer x = null;まで書かなくていいかもしれないけど、x;でnullで初期化してくれるか分からなかった。⇒試したらnullにしてくれた。
     * int型なら宣言だけした時に0で初期化してくれるから、参照型で相当する動きで、場所はあるけど中身は空っぽの意味合いでnullということになるのかな。
     * ⇒ nullってメモリにとって何だっけと思い、調べた。（メモリという主語が大きいかも。。実行するプログラムにOSが割り当てた計算・記憶用の領域、みたいな。）
     * 　・参照型の変数を宣言時に定義しない場合、参照先がないので参照パスはメモリに載らないけど、宣言した変数を
     * 取っておくための場所は用意するから、そういう状態をnullで表しているのかなと思った。
     * 　https://blogs.oracle.com/oracle4engineer/post/curly-braces-java-null-ja
     *   　・Javaはどうでしょうか?
     * 　https://qiita.com/e99h2121/items/46a624e0fe74e80c3b79
     */
    
    
    // DFS スタック in-order
    class Solution2_3 {
        public boolean isValidBST(TreeNode root) {
            Stack<TreeNode> inorderNodes = new Stack<>();
            pushLeftNodes(inorderNodes, root);
            Integer minVal = null;
            while (!inorderNodes.isEmpty()) {
                TreeNode inorderNode = inorderNodes.pop();
                if (minVal != null && !(minVal < inorderNode.val)) {
                    return false;
                }
                minVal = inorderNode.val;
                if (inorderNode.right != null) {
                    pushLeftNodes(inorderNodes, inorderNode.right);
                }
            }
            return true;
        }

        private void pushLeftNodes(Stack<TreeNode> inorderNodes, TreeNode node) {
            while (node != null) {
                inorderNodes.push(node);
                node = node.left;
            }
        }
    }
    /*
     * 思ったこと
     * ・ハイフンを使った場合、一つの英単語になるのか。
     * https://prince-eibei.jp/prince/prince-6797/
     */
    
    
    // BFS queue pre-order
    class Solution2_4 {
        record NodeAndValidRange(TreeNode node, Integer lower, Integer upper) {}

        public boolean isValidBST(TreeNode root) {
            Queue<NodeAndValidRange> nodeAndValidRanges = new LinkedList<>();
            nodeAndValidRanges.offer(new NodeAndValidRange(root, null, null));
            while (!nodeAndValidRanges.isEmpty()) {
                NodeAndValidRange nodeAndValidRange = nodeAndValidRanges.poll();
                TreeNode node = nodeAndValidRange.node();
                Integer lower = nodeAndValidRange.lower();
                Integer upper = nodeAndValidRange.upper();
                if (lower != null && !(lower < node.val)) {
                    return false;
                }
                if (upper != null && !(node.val < upper)) {
                    return false;
                }
                if (node.left != null) {
                    nodeAndValidRanges.offer(new NodeAndValidRange(node.left, lower, node.val));
                }
                if (node.right != null) {
                    nodeAndValidRanges.offer(new NodeAndValidRange(node.right, node.val, upper));
                }
            }
            return true;
        }
    }
    /*
     * 思ったこと
     * ・Step1とあまり変わらなくなってしまった。
     */
    
}
/*
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1201112801725386853
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1212784559280889957
 *   https://github.com/hayashi-ay/leetcode/pull/38
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1218927581605396572
 *   https://github.com/shining-ai/leetcode/pull/28
 * https://discord.com/channels/1084280443945353267/1192736784354918470/1230313494218412115
 *   https://github.com/YukiMichishita/LeetCode/pull/8
 * https://discord.com/channels/1084280443945353267/1196472827457589338/1236007436440305664
 *   https://github.com/Mike0121/LeetCode/pull/8/
 * https://github.com/sakupan102/arai60-practice/pull/29
 * https://github.com/fhiyo/leetcode/pull/30
 * https://github.com/kazukiii/leetcode/pull/29
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/32
 * https://github.com/TORUS0818/leetcode/pull/30
 * https://github.com/Ryotaro25/leetcode_first60/pull/30
 * https://github.com/nittoco/leetcode/pull/35
 * https://github.com/Ryotaro25/leetcode_first60/pull/30
 */
