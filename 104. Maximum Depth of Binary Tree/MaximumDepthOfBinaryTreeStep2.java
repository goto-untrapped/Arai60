public class MaximumDepthOfBinaryTreeStep2 {
    // スタック。とりあえず詰める。
    class Solution2_1 {
        public int maxDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            Stack<NodeAndDepth> nodeAndDepth = new Stack<>();
            nodeAndDepth.push(new NodeAndDepth(root, 1));
            int maxDepth = 0;
            while (!nodeAndDepth.isEmpty()) {
                NodeAndDepth depth = nodeAndDepth.pop();
                if (depth.node() == null) {
                    continue;
                }
                maxDepth = Math.max(maxDepth, depth.depth());
                nodeAndDepth.push(new NodeAndDepth(depth.node().left, depth.depth() + 1));
                nodeAndDepth.push(new NodeAndDepth(depth.node().right, depth.depth() + 1));
            }
            return maxDepth;
        }
        record NodeAndDepth(TreeNode node, int depth) {}
    }
    /*
     * 思ったこと
     * ・スタックを使うと再帰を使った時に比べて処理が複雑になる
     */


    // スタック。nullでないnodeしか入れない。
    class Solution2_2 {
        public int maxDepth2_2(TreeNode root) {
            if (root == null) {
                return 0;
            }
            ArrayDeque<NodeAndDepth> depths = new ArrayDeque<>();
            depths.addLast(new NodeAndDepth(root, 1));
            int maxDepth = 0;
            while (!depths.isEmpty()) {
                NodeAndDepth depth = depths.pollFirst();
                maxDepth = Math.max(maxDepth, depth.depth());
                if (depth.node().left != null) {
                    depths.addLast(new NodeAndDepth(depth.node().left, depth.depth() + 1));
                }
                if (depth.node().right != null) {
                    depths.addLast(new NodeAndDepth(depth.node().right, depth.depth() + 1));
                }
            }
            return maxDepth;

        }
        record NodeAndDepth(TreeNode node, int depth) {}
    }
    /*
     * 思ったこと
     * ・ArrayDequeはnullを処理せず、例外を投げる。LinkedListはnullも格納できる。
     * ⇒ LinkedListはListインターフェースも実装しているため。
     * ・LeetCodeを解く場合、注意することを減らしたいので、LinkedListを使いたいと思った。
     * 　・ArrayDequeの方が要素の追加、取り出すのパフォーマンスがいい。
     * 　・どちらもスレッドセーフでない。
     */


    // BFS 入れ物を2つ使うと深さをint型で書ける
    public int maxDepth2_3(TreeNode root) {
        if (root == null) {
            return 0;
        }
        LinkedList<TreeNode> nodes = new LinkedList<>();
        nodes.offer(root);
        int maxDepth = 0;
        while (!nodes.isEmpty()) {
            maxDepth++;
            LinkedList<TreeNode> nextDepthNodes = new LinkedList<>();
            for (TreeNode node : nodes) {
                if (node.left != null) {
                    nextDepthNodes.offer(node.left);
                }
                if (node.right != null) {
                    nextDepthNodes.offer(node.right);
                }
            }
            nodes = nextDepthNodes;
        }
        return maxDepth;
    }
    /*
     * 思ったこと
     * ・addだと例外を投げ、offerはbooleanを返す
     * https://stackoverflow.com/questions/2703984/what-is-the-difference-between-the-add-and-offer-methods-in-a-queue-in-java
     * ⇒ 対応できるたほうがいいと思い、offerを使った。
     */

    
    // スタック 帰りがけに計算する
    // https://github.com/sakupan102/arai60-practice/pull/21/files
    // https://github.com/fhiyo/leetcode/pull/23
    class Solution2_4 {
        public int maxDepth(TreeNode root) {
            int[] maxDepth = new int[1];
            Stack<NodeDepthInfo> depthInfos = new Stack<>();
            depthInfos.add(new NodeDepthInfo(root, "go", maxDepth, new int[1], new int[1]));
            while (!depthInfos.isEmpty()) {
                NodeDepthInfo depthInfo = depthInfos.pop();
                if (depthInfo.direction.equals("go")) {
                    if (depthInfo.node == null) {
                        depthInfo.depth[0] = 0;
                        continue;
                    }
                    depthInfos.add(new NodeDepthInfo(depthInfo.node, "back", depthInfo.depth, depthInfo.leftDepth,
                            depthInfo.rightDepth));
                    depthInfos.add(
                            new NodeDepthInfo(depthInfo.node.left, "go", depthInfo.leftDepth, new int[1], new int[1]));
                    depthInfos.add(new NodeDepthInfo(depthInfo.node.right, "go", depthInfo.rightDepth, new int[1],
                            new int[1]));
                    continue;
                }
                depthInfo.depth[0] = Math.max(depthInfo.leftDepth[0], depthInfo.rightDepth[0]) + 1;
            }
            return maxDepth[0];
        }

        class NodeDepthInfo {
            TreeNode node;
            String direction;
            int[] depth;
            int[] leftDepth;
            int[] rightDepth;

            NodeDepthInfo(TreeNode node, String direction, int[] depth, int[] leftDepth, int[] rightDepth) {
                this.node = node;
                this.direction = direction;
                this.depth = depth;
                this.leftDepth = leftDepth;
                this.rightDepth = rightDepth;
            }
        }
    }
    /*
     * 思ったこと
     * ・理解するのに時間がかかった。
     * 　・子の深さと親の左・右の深さは同じ参照なので子の深さを更新したら親にも反映される
     * 　・nodeの格納順番、取り出す順番。
     */


    /*
     * 再帰をするのなら、nodeがnullになった時の深さを0でスタートした方が
     * Step1より役割分担が自然で分かりやすい。
     */
    class Solution2_5 {
        public int maxDepth(TreeNode root) {
            return maxDepthHelper(root);
        }

        private int maxDepthHelper(TreeNode node) {
            if (node == null) {
                return 0;
            }
            return Math.max(maxDepthHelper(node.left), maxDepthHelper(node.right)) + 1;
        }
    }

}
/*
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1206164603688456222
 *   https://github.com/hayashi-ay/leetcode/pull/22/files
 * ・ループで解く時、nodes を2つ使えば、depth を決め打ちで増やしていける
 * ・Stackで解く時、pre-order する時、right から left の順番で格納すればいい
 * https://discord.com/channels/1084280443945353267/1227073733844406343/1236232864098684928
 *   https://github.com/sakupan102/arai60-practice/pull/21
 * https://discord.com/channels/1084280443945353267/1235829049511903273/1252664495105507389
 *   https://github.com/fhiyo/leetcode/pull/23
 */
