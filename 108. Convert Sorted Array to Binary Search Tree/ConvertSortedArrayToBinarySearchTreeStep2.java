public class ConvertSortedArrayToBinarySearchTreeStep2 {
    // DFS 再帰 閉区間 rightが親
    class Solution2_1 {
        public TreeNode sortedArrayToBST(int[] nums) {
            return sortedArrayToBSTHelper(nums, 0, nums.length - 1);
        }

        private TreeNode sortedArrayToBSTHelper(int[] nums, int left, int right) {
            if (left > right) {
                return null;
            }
            int rootIndex = (left + right) / 2;
            if ((left + right) % 2 == 1) {
                rootIndex++;
            }
            TreeNode root = new TreeNode(nums[rootIndex]);
            root.left = sortedArrayToBSTHelper(nums, left, rootIndex - 1);
            root.right = sortedArrayToBSTHelper(nums, rootIndex + 1, right);
            return root;
        }
    }
    /*
     * 思ったこと
     * ・解答を見た。確かに常に親を左側にもできるし右側にもできる。
     */
    
    
    // DFS 再帰 半開区間 leftが親
    class Solution2_2 {
        public TreeNode sortedArrayToBST(int[] nums) {
            return sortedArrayToBSTHelper(nums, 0, nums.length);
        }

        private TreeNode sortedArrayToBSTHelper(int[] nums, int left, int right) {
            if (left >= right) {
                return null;
            }
            int rootIndex = (left + right) / 2;
            TreeNode root = new TreeNode(nums[rootIndex]);
            root.left = sortedArrayToBSTHelper(nums, left, rootIndex);
            root.right = sortedArrayToBSTHelper(nums, rootIndex + 1, right);
            return root;
        }
    }
    /*
     * 思ったこと
     * ・半開区間も練習する。
     * ・半開なのでleft->start, right->endでよかったな。
     */
    
    
    // DFS スタック 再帰がやってくれた処理は１仕事中に一緒にやる（部下に仕事を押し付ける）
    class Solution2_3 {
        public TreeNode sortedArrayToBST(int[] nums) {
            int rootIndex = nums.length / 2;
            TreeNode root = new TreeNode(nums[rootIndex]);
            Stack<NodeAndIndexRange> nodeAndIndexRanges = new Stack<>();
            nodeAndIndexRanges.push(new NodeAndIndexRange(root, 0, rootIndex - 1));
            nodeAndIndexRanges.push(new NodeAndIndexRange(root, rootIndex + 1, nums.length - 1));
            while (!nodeAndIndexRanges.isEmpty()) {
                NodeAndIndexRange nodeAndIndexRange = nodeAndIndexRanges.pop();
                TreeNode node = nodeAndIndexRange.node();
                int middleIndex = (nodeAndIndexRange.left() + nodeAndIndexRange.right()) / 2;
                if (nodeAndIndexRange.left() > nodeAndIndexRange.right()) {
                    continue;
                }
                if (nums[middleIndex] < node.val) {
                    TreeNode child = new TreeNode(nums[middleIndex]);
                    node.left = child;
                    nodeAndIndexRanges.push(new NodeAndIndexRange(child, nodeAndIndexRange.left(), middleIndex - 1));
                    nodeAndIndexRanges.push(new NodeAndIndexRange(child, middleIndex + 1, nodeAndIndexRange.right()));
                }
                if (node.val < nums[middleIndex]) {
                    TreeNode child = new TreeNode(nums[middleIndex]);
                    node.right = child;
                    nodeAndIndexRanges.push(new NodeAndIndexRange(child, nodeAndIndexRange.left(), middleIndex - 1));
                    nodeAndIndexRanges.push(new NodeAndIndexRange(child, middleIndex + 1, nodeAndIndexRange.right()));
                }
            }
            return root;
        }
        record NodeAndIndexRange(TreeNode node, int left, int right) {}
    }
    /*
     * 思ったこと
     * ・一番素朴だと思う方法で書いた。（木を作るために必要な処理と、スタックとしての動きでデータが流れる処理の最小限というイメージ）
     * ・record名が長く、コード全体が見にくくなってしまう。
     * ・問題を解くうえで必要な処理と、スタックとして回すために必要な処理が頭の中でごっちゃになりがち。
     * ・子が左になるか右になるかのためだけに同じ処理を長々と書くのつらい。
     * ・nodeAndIndexRange.XXX()と何回も書いて冗長だったと思うけど、変数として置いたらもっと同じ行が増えて冗長になる気がした。
     * と思ったが、rootとrootが真ん中の場合の初めから終わりまでのインデックスのグループを格納すれば、インデックスの比較で左右が分かるし、
     * そのグループで左右の2回pushすればいいだけになる。
     * https://github.com/fhiyo/leetcode/pull/26/files
     */
    // Solution2_3を改善
    class Solution2_4 {
        public TreeNode sortedArrayToBST(int[] nums) {
            TreeNode root = new TreeNode();
            Stack<NodeAndIndexRange> nodeAndIndexRanges = new Stack<>();
            nodeAndIndexRanges.push(new NodeAndIndexRange(root, 0, nums.length - 1));
            while (!nodeAndIndexRanges.isEmpty()) {
                NodeAndIndexRange nodeAndIndexRange = nodeAndIndexRanges.pop();
                TreeNode parent = nodeAndIndexRange.node();
                int left = nodeAndIndexRange.left();
                int right = nodeAndIndexRange.right();
                int middle = (left + right) / 2;
                parent.val = nums[middle];
                if (left <= middle - 1) {
                    parent.left = new TreeNode();
                    nodeAndIndexRanges.push(new NodeAndIndexRange(parent.left, left, middle - 1));
                }
                if (middle + 1 <= right) {
                    parent.right = new TreeNode();
                    nodeAndIndexRanges.push(new NodeAndIndexRange(parent.right, middle + 1, right));
                }
            }
            return root;
        }
        record NodeAndIndexRange(TreeNode node, int left, int right) {}
    }
    /*
     * 思ったこと
     * ・インデックス上下限は再帰の条件に合わせた。
     */
    
    
    // DFS スタック 再帰がやってくれた処理は１仕事中に一緒にやる（部下に仕事を押し付ける） 番兵
    class Solution2_5 {
        public TreeNode sortedArrayToBST(int[] nums) {
            TreeNode sentinel = new TreeNode();
            Stack<NodeAndIndexRangeAndIsLeft> nodeAndIndexRangeAndIsLefts = new Stack<>();
            nodeAndIndexRangeAndIsLefts.push(new NodeAndIndexRangeAndIsLeft(sentinel, 0, nums.length, true));
            while (!nodeAndIndexRangeAndIsLefts.isEmpty()) {
                NodeAndIndexRangeAndIsLeft nodeAndIndexRangeAndIsLeft = nodeAndIndexRangeAndIsLefts.pop();
                if (nodeAndIndexRangeAndIsLeft.left() >= nodeAndIndexRangeAndIsLeft.right()) {
                    continue;
                }
                TreeNode parent = nodeAndIndexRangeAndIsLeft.node();
                int middle = (nodeAndIndexRangeAndIsLeft.left() + nodeAndIndexRangeAndIsLeft.right()) / 2;
                TreeNode child = new TreeNode(nums[middle]);
                if (nodeAndIndexRangeAndIsLeft.isLeft()) {
                    parent.left = child;
                } else {
                    parent.right = child;
                }
                nodeAndIndexRangeAndIsLefts.push(
                    new NodeAndIndexRangeAndIsLeft(child, nodeAndIndexRangeAndIsLeft.left(), middle, true));
                nodeAndIndexRangeAndIsLefts.push(
                    new NodeAndIndexRangeAndIsLeft(child, middle + 1, nodeAndIndexRangeAndIsLeft.right(), false));
            }
            return sentinel.left;
        }
        record NodeAndIndexRangeAndIsLeft(TreeNode node, int left, int right, boolean isLeft) {}
    }
    /*
     * 思ったこと
     * ・インデックスの範囲というか、2分探索として狭めていく時の境界がいつも分からなくなる。
     * ・半開なのでleft->start, right->endでよかったな。
     */
    
    
    // DFS スタック 再帰がやってくれた処理は仕事として積む（部下に仕事を押し付けない） 番号を付ける
    class Solution2_6 {
        public TreeNode sortedArrayToBST(int[] nums) {
            Box<TreeNode> root = new Box<TreeNode>(null);
            Stack<DirectionAndNodeAndIndexRange> directionAndNodeAndIndexRanges = new Stack<>();
            directionAndNodeAndIndexRanges.push(
                new DirectionAndNodeAndIndexRange(Direction.go, root, new Box<TreeNode>(null), new Box<TreeNode>(null), 0, nums.length - 1));
            while (!directionAndNodeAndIndexRanges.isEmpty()) {
                DirectionAndNodeAndIndexRange directionAndNodeAndIndexRange = directionAndNodeAndIndexRanges.pop();
                if (directionAndNodeAndIndexRange.direction() == Direction.go) {
                    int left = directionAndNodeAndIndexRange.left();
                    int right = directionAndNodeAndIndexRange.right();
                    if (left > right) {
                        continue;
                    }
                    Box<TreeNode> parentNode = directionAndNodeAndIndexRange.parentNode();
                    Box<TreeNode> leftNode = directionAndNodeAndIndexRange.leftNode();
                    Box<TreeNode> rightNode = directionAndNodeAndIndexRange.rightNode();
                    int middle = (left + right) / 2;
                    parentNode.value = new TreeNode(nums[middle]);
                    directionAndNodeAndIndexRanges.push(
                        new DirectionAndNodeAndIndexRange(Direction.back, parentNode, leftNode, rightNode, left, right));
                    directionAndNodeAndIndexRanges.push(
                        new DirectionAndNodeAndIndexRange(Direction.go, leftNode, new Box<TreeNode>(null), new Box<TreeNode>(null), left, middle - 1));
                    directionAndNodeAndIndexRanges.push(
                        new DirectionAndNodeAndIndexRange(Direction.go, rightNode, new Box<TreeNode>(null), new Box<TreeNode>(null), middle + 1, right));
                    continue;
                }
                directionAndNodeAndIndexRange.parentNode().value.left = directionAndNodeAndIndexRange.leftNode().value;
                directionAndNodeAndIndexRange.parentNode().value.right = directionAndNodeAndIndexRange.rightNode().value;
            }
            return root.value;
        }

        record DirectionAndNodeAndIndexRange(
            Direction direction, Box<TreeNode> parentNode, Box<TreeNode> leftNode, Box<TreeNode> rightNode, int left, int right) {}
        enum Direction { go, back; }
        class Box<T> {
            T value;
            Box(T value) {
                this.value = value;
            }
        }
    }
    /*
     * 思ったこと
     * ・はじめ、TreeNodeをそのままスタックフレームに入れていたが、そうすると0のノードが子に付いてしまう。
     * 参照にしておくためのやり方が分からず、GPTに聞いて、実装した。
     * ・BoxをTreeNode限定にも実装できたが、Solution2_6の方が汎用性が高そう。
     */
    // Solution2_6の入れ物を変更
    class Solution2_7 {
        public TreeNode sortedArrayToBST(int[] nums) {
            TreeNodeBox root = new TreeNodeBox(null);
            Stack<DirectionAndNodeAndIndexRange> directionAndNodeAndIndexRanges = new Stack<>();
            directionAndNodeAndIndexRanges.push(
                new DirectionAndNodeAndIndexRange(Direction.go, root, new TreeNodeBox(null), new TreeNodeBox(null), 0, nums.length - 1));
            while (!directionAndNodeAndIndexRanges.isEmpty()) {
                DirectionAndNodeAndIndexRange directionAndNodeAndIndexRange = directionAndNodeAndIndexRanges.pop();
                if (directionAndNodeAndIndexRange.direction() == Direction.go) {
                    int left = directionAndNodeAndIndexRange.left();
                    int right = directionAndNodeAndIndexRange.right();
                    if (left > right) {
                        continue;
                    }
                    TreeNodeBox parentNode = directionAndNodeAndIndexRange.parentNode();
                    TreeNodeBox leftNode = directionAndNodeAndIndexRange.leftNode();
                    TreeNodeBox rightNode = directionAndNodeAndIndexRange.rightNode();
                    int middle = (left + right) / 2;
                    parentNode.node = new TreeNode(nums[middle]);
                    directionAndNodeAndIndexRanges.push(
                        new DirectionAndNodeAndIndexRange(Direction.back, parentNode, leftNode, rightNode, left, right));
                    directionAndNodeAndIndexRanges.push(
                        new DirectionAndNodeAndIndexRange(Direction.go, leftNode, new TreeNodeBox(null), new TreeNodeBox(null), left, middle - 1));
                    directionAndNodeAndIndexRanges.push(
                        new DirectionAndNodeAndIndexRange(Direction.go, rightNode, new TreeNodeBox(null), new TreeNodeBox(null), middle + 1, right));
                    continue;
                }
                directionAndNodeAndIndexRange.parentNode().node.left = directionAndNodeAndIndexRange.leftNode().node;
                directionAndNodeAndIndexRange.parentNode().node.right = directionAndNodeAndIndexRange.rightNode().node;
            }
            return root.node;
        }

        record DirectionAndNodeAndIndexRange(
            Direction direction, TreeNodeBox parentNode, TreeNodeBox leftNode, TreeNodeBox rightNode, int left, int right) {}
        enum Direction { go, back; }
        class TreeNodeBox {
            TreeNode node;
            TreeNodeBox(TreeNode node) {
                this.node = node;
            }
        }
    }
}
/*
 * https://discord.com/channels/1084280443945353267/1183683738635346001/1209195844511858688
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1210188842137821225
 *   https://github.com/hayashi-ay/leetcode/pull/29
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1217525976691507221
 *   https://github.com/shining-ai/leetcode/pull/24
 * https://github.com/rossy0213/leetcode/pull/13
 * https://github.com/sakupan102/arai60-practice/pull/25
 * https://discord.com/channels/1084280443945353267/1196472827457589338/1238907849422274622
 *   https://discord.com/channels/1084280443945353267/1201211204547383386/1217821129297629364
 *   https://github.com/Mike0121/LeetCode/pull/13
 * https://github.com/fhiyo/leetcode/pull/26
 * https://github.com/kazukiii/leetcode/pull/25
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/28
 * https://github.com/TORUS0818/leetcode/pull/26
 * https://github.com/Ryotaro25/leetcode_first60/pull/26
 */
