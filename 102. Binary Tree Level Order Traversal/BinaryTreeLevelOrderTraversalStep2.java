public class BinaryTreeLevelOrderTraversalStep2 {
    
    // BFS
    class Solution2_1 {
        public List<List<Integer>> levelOrder(TreeNode root) {
            if (root == null) {
                return new ArrayList<>();
            }
            List<List<Integer>> allLevelVals = new ArrayList<>();
            List<TreeNode> sameLevelNodes = new ArrayList<>();
            sameLevelNodes.add(root);
            while (!sameLevelNodes.isEmpty()) {
                List<TreeNode> nextLevelNodes = new ArrayList<>();
                List<Integer> sameLevelVals = new ArrayList<>();
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
    /*
     * 思ったこと
     * ・Step1で元々書きたかった書き方。
     * ・Listを使う時、popLeft()に相当する動きはO(n)かかる。インデックスをずらしているため。
     * DequeやQueueならO(1)になる。今回たまたまremove()を使わなかったけど、そこの時間量が違うことに注意する。
     * https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/ArrayList.java#L720
     * https://github.com/Mike0121/LeetCode/pull/7/files#r1587421003
     */
    
    
    // BFS
    class Solution2_2 {
        public List<List<Integer>> levelOrder(TreeNode root) {
            if (root == null) {
                return new ArrayList<>();
            }
            List<List<Integer>> allLevelVals = new ArrayList<>();
            Queue<TreeNode> ascLevelNodes = new LinkedList<>();
            ascLevelNodes.offer(root);
            while (!ascLevelNodes.isEmpty()) {
                allLevelVals.add(new ArrayList<>());
                int numSameLevelNodes = ascLevelNodes.size();
                for (; numSameLevelNodes > 0; numSameLevelNodes--) {
                    TreeNode node = ascLevelNodes.poll();
                    allLevelVals.get(allLevelVals.size() - 1).add(node.val);
                    if (node.left != null) {
                        ascLevelNodes.offer(node.left);
                    }
                    if (node.right != null) {
                        ascLevelNodes.offer(node.right);
                    }
                }
            }
            return allLevelVals;
        }
    }
    /*
     * 思ったこと
     * ・今まで気づかなかったけど、問題文ではlevel orderと書いているのか。
     */
    
    
    // DFS
    class Solution2_3 {
        public List<List<Integer>> levelOrder(TreeNode root) {
            if (root == null) {
                return new ArrayList<>();
            }
            List<List<Integer>> allLevelVals = new ArrayList<>();
            List<TreeNode> sameLevelNodes = new ArrayList<>();
            sameLevelNodes.add(root);
            return addEachLevelVals(allLevelVals, sameLevelNodes);
        }
        private List<List<Integer>> addEachLevelVals(List<List<Integer>> allLevelVals, List<TreeNode> sameLevelNodes) {
            if (sameLevelNodes.size() == 0) {
                return allLevelVals;
            }
            allLevelVals.add(new ArrayList<>());
            List<TreeNode> nextLevelNodes = new ArrayList<>();
            for (TreeNode node : sameLevelNodes) {
                if (node == null) {
                    continue;
                }
                allLevelVals.get(allLevelVals.size() - 1).add(node.val);
                if (node.left != null) {
                    nextLevelNodes.add(node.left);
                }
                if (node.right != null) {
                    nextLevelNodes.add(node.right);
                }
            }
            return addEachLevelVals(allLevelVals, nextLevelNodes);
        }
    }
    /*
     * 思ったこと
     * ・nullが入っても処理が終わることが分かる条件が書けると思っていた。
     */
    
    
    // DFS
    class Solution2_4 {
        record NodeAndLevel(TreeNode node, int level) {}
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> allLevelVals = new ArrayList<>();
            Stack<NodeAndLevel> nodeAndLevels = new Stack<>();
            nodeAndLevels.push(new NodeAndLevel(root, 1));
            while (!nodeAndLevels.isEmpty()) {
                NodeAndLevel nodeAndLevel = nodeAndLevels.pop();
                TreeNode node = nodeAndLevel.node();
                if (node == null) {
                    continue;
                }
                int level = nodeAndLevel.level();
                while (allLevelVals.size() < level) {
                    allLevelVals.add(new ArrayList<>());
                }
                int levelIndex = level - 1;
                allLevelVals.get(levelIndex).add(node.val);
                nodeAndLevels.push(new NodeAndLevel(node.right, level + 1));
                nodeAndLevels.push(new NodeAndLevel(node.left, level + 1));
            }
            return allLevelVals;
        }
    }
    /*
     * 思ったこと
     * ・問題文をちゃんと読めていなかった。(level order traversalはfrom left to right)
     * ・また、サイズが同じ時もlevelリストを増やすようにすれば、インデックス周りがもっとシンプルに書ける
     * https://github.com/fhiyo/leetcode/pull/28/files
     */
    // Solution2_4のインデックス周りを変更
    class Solution2_5 {
        record NodeAndLevel(TreeNode node, int level) {}
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> allLevelVals = new ArrayList<>();
            Stack<NodeAndLevel> nodeAndLevels = new Stack<>();
            nodeAndLevels.push(new NodeAndLevel(root, 0));
            while (!nodeAndLevels.isEmpty()) {
                NodeAndLevel nodeAndLevel = nodeAndLevels.pop();
                TreeNode node = nodeAndLevel.node();
                if (node == null) {
                    continue;
                }
                int level = nodeAndLevel.level();
                while (allLevelVals.size() <= level) {
                    allLevelVals.add(new ArrayList<>());
                }
                int levelIndex = level;
                allLevelVals.get(levelIndex).add(node.val);
                nodeAndLevels.push(new NodeAndLevel(node.right, level + 1));
                nodeAndLevels.push(new NodeAndLevel(node.left, level + 1));
            }
            return allLevelVals;
        }
    }
    
}
/*
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1211206241867665432
 *   https://github.com/hayashi-ay/leetcode/pull/32
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1218244966946701363
 *   https://github.com/shining-ai/leetcode/pull/26
 * https://discord.com/channels/1084280443945353267/1196472827457589338/1235538738927370322
 *   https://github.com/Mike0121/LeetCode/pull/7
 * https://github.com/sakupan102/arai60-practice/pull/27
 * https://github.com/fhiyo/leetcode/pull/28
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/30
 * https://github.com/kazukiii/leetcode/pull/27
 * https://github.com/TORUS0818/leetcode/pull/28
 * https://github.com/nittoco/leetcode/pull/32
 * https://github.com/Ryotaro25/leetcode_first60/pull/28
 */
