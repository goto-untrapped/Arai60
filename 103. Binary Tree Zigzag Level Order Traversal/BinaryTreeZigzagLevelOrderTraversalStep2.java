public class BinaryTreeZigzagLevelOrderTraversalStep2 {
    
    // BFS
    // 深さの偶奇によって値の並びを反転する
    class Solution2_1 {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            if (root == null) {
                return new ArrayList<>();
            }
            List<List<Integer>> allLevelVals = new ArrayList<>();
            List<TreeNode> sameLevelNodes = new ArrayList<>();
            sameLevelNodes.add(root);
            int level = 0;
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
                if (level % 2 == 1) {
                    Collections.reverse(sameLevelVals);
                }
                allLevelVals.add(sameLevelVals);
                sameLevelNodes = nextLevelNodes;
                level++;
            }
            return allLevelVals;
        }
    }
    
    
    // BFS
    // ジグザグに格納して、常に後ろから値を並べる
    class Solution2_2 {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> allLevelVals = new ArrayList<>();
            Deque<TreeNode> sameLevelNodes = new LinkedList<>();
            sameLevelNodes.offerFirst(root);
            boolean isLeftToRight = true;
            while (!sameLevelNodes.isEmpty()) {
                Deque<TreeNode> nextLevelNodes = new LinkedList<>();
                List<Integer> sameLevelVals = new ArrayList<>();
                while (!sameLevelNodes.isEmpty()) {
                    TreeNode node = sameLevelNodes.pollLast();
                    if (node == null) {
                        continue;
                    }
                    sameLevelVals.add(node.val);
                    if (isLeftToRight) {
                        nextLevelNodes.offerLast(node.left);
                        nextLevelNodes.offerLast(node.right);
                    } else {
                        nextLevelNodes.offerLast(node.right);
                        nextLevelNodes.offerLast(node.left);
                    }
                }
                if (sameLevelVals.size() > 0) {
                    allLevelVals.add(sameLevelVals);
                }
                sameLevelNodes = nextLevelNodes;
                isLeftToRight = !isLeftToRight;
            }
            return allLevelVals;
        }
    }
    
    
    // BFS 再帰
    // ジグザグに格納して、常に前から値を並べる
    class Solution2_3 {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            if (root == null) {
                return new ArrayList<>();
            }
            List<List<Integer>> allLevelVals = new ArrayList<>();
            Deque<TreeNode> sameLevelNodes = new LinkedList<>();
            sameLevelNodes.offerLast(root);
            return zigzagLevelOrderHelper(allLevelVals, sameLevelNodes, 0);
        }

        private List<List<Integer>> zigzagLevelOrderHelper(List<List<Integer>> allLevelVals, Deque<TreeNode> sameLevelNodes, int level) {
            if (sameLevelNodes.size() == 0) {
                return allLevelVals;
            }
            allLevelVals.add(new ArrayList<>());
            Deque<TreeNode> nextLevelNodes = new LinkedList<>();
            while (!sameLevelNodes.isEmpty()) {
                TreeNode node = sameLevelNodes.pollFirst();
                allLevelVals.get(allLevelVals.size() - 1).add(node.val);
                if (level % 2 == 0) {
                    if (node.left != null) {
                        nextLevelNodes.offerFirst(node.left);
                    }
                    if (node.right != null) {
                        nextLevelNodes.offerFirst(node.right);
                    }
                } else {
                    if (node.right != null) {
                        nextLevelNodes.offerFirst(node.right);
                    }
                    if (node.left != null) {
                        nextLevelNodes.offerFirst(node.left);
                    }
                }
            }
            return zigzagLevelOrderHelper(allLevelVals, nextLevelNodes, level + 1);
        }
    }
    
    
    // DFS 再帰
    // ノードを訪れる順番をpost-orderにして偶奇によって順番に格納する
    class Solution2_4 {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            if (root == null) {
                return new ArrayList<>();
            }
            return zigzagLevelOrderHelper(new ArrayList<>(), root, 0);
        }

        private List<List<Integer>> zigzagLevelOrderHelper(List<List<Integer>> allLevelVals, TreeNode node, int level) {
            if (node == null) {
                return allLevelVals;
            }

            zigzagLevelOrderHelper(allLevelVals, node.left, level + 1);
            zigzagLevelOrderHelper(allLevelVals, node.right, level + 1);

            while (allLevelVals.size() <= level) {
                allLevelVals.add(new ArrayList<>());
            }

            if (level % 2 == 0) {
                allLevelVals.get(level).add(node.val);
            } else {
                allLevelVals.get(level).add(0, node.val);
            }
            return allLevelVals;
        }
    }
    
    
    // DFS 再帰 
    // pre-orderでもいけた。
    class Solution2_5 {
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            if (root == null) {
                return new ArrayList<>();
            }
            return zigzagLevelOrderHelper(new ArrayList<>(), root, 0);
        }

        private List<List<Integer>> zigzagLevelOrderHelper(List<List<Integer>> allLevelVals, TreeNode node, int level) {
            // 1回だけ拡張される
            while (allLevelVals.size() <= level) {
                allLevelVals.add(new ArrayList<>());
            }
            if (level % 2 == 0) {
                allLevelVals.get(level).add(node.val);
            } else {
                allLevelVals.get(level).add(0, node.val);
            }
            if (node.left != null) {
                zigzagLevelOrderHelper(allLevelVals, node.left, level + 1);
            }
            if (node.right != null) {
                zigzagLevelOrderHelper(allLevelVals, node.right, level + 1);
            }
            return allLevelVals;
        }
    }
    /*
     * 思ったこと
     * ・pre-orderだとノードを処理した後に戻ってきてまた処理してしまうと思っていた。
     * 再帰後にノードの処理を書かなければ２回目は処理されない。早とちり。
     */
    
    
    // DFS スタック
    class Solution2_6 {
        record NodeAndLevel(TreeNode node, int level) {}
        public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            if (root == null) {
                return new ArrayList<>();
            }
            List<List<Integer>> allLevelVals = new ArrayList<>();
            Stack<NodeAndLevel> nodes = new Stack<>();
            nodes.push(new NodeAndLevel(root, 0));
            while (!nodes.isEmpty()) {
                NodeAndLevel nodeAndLevel = nodes.pop();
                TreeNode node = nodeAndLevel.node();
                int level = nodeAndLevel.level();
                while (allLevelVals.size() <= level) {
                    allLevelVals.add(new ArrayList<>());
                }
                if (level % 2 == 0) {
                    allLevelVals.get(level).add(0, node.val);
                } else {
                    allLevelVals.get(level).add(node.val);
                }
                if (node.left != null) {
                    nodes.push(new NodeAndLevel(node.left, level + 1));
                }
                if (node.right != null) {
                    nodes.push(new NodeAndLevel(node.right, level + 1));
                }
            }
            return allLevelVals;
        }
    }
    
}
/*
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1211791166190915687
 *   https://github.com/hayashi-ay/leetcode/pull/35
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1218448578767491152
 *   https://github.com/shining-ai/leetcode/pull/27
 * https://discord.com/channels/1084280443945353267/1196472827457589338/1236595428557062214
 *   https://github.com/Mike0121/LeetCode/pull/10
 * https://github.com/sakupan102/arai60-practice/pull/28
 * https://discord.com/channels/1084280443945353267/1192736784354918470/1239577010200641607
 *   https://github.com/YukiMichishita/LeetCode/pull/11
 * https://github.com/fhiyo/leetcode/pull/29
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/31
 * https://github.com/kazukiii/leetcode/pull/28
 * https://github.com/TORUS0818/leetcode/pull/29
 * https://github.com/Ryotaro25/leetcode_first60/pull/28
 * https://github.com/nittoco/leetcode/pull/34/files
 */
