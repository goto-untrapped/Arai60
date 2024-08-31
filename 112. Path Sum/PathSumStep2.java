public class PathSumStep2 {
    
    // Step1のWAを修正
    class Solution2_1 {
        public boolean hasPathSum(TreeNode root, int targetSum) {
            return hasPathSumHelper(root, targetSum);
        }

        private boolean hasPathSumHelper(TreeNode node, int rest) {
            if (node == null) {
                return false;
            }
            rest -= node.val;
            if (node.left == null && node.right == null) {
                return rest == 0;
            }
            return hasPathSumHelper(node.left, rest) || hasPathSumHelper(node.right, rest);
        }
    }
    /*
     * 思ったこと
     * ・葉ノードであること、値は負にもなることを盛り込めば書ける。
     */
    
    
    // DFS
    class Solution2_2 {
        public boolean hasPathSum(TreeNode root, int targetSum) {
            if (root == null) {
                return false;
            }
            targetSum -= root.val;
            if (root.left == null && root.right == null) {
                return targetSum == 0;
            }
            if (hasPathSum(root.left, targetSum)) {
                return true;
            }
            return hasPathSum(root.right, targetSum);
        }
    }
    /*
     * 思ったこと
     * ・1本の道を見つけたらtrueでいいか。
     */
    
    
    // DFS
    class Solution2_3 {
        record NodeFrame(TreeNode node, int rest) {}
        public boolean hasPathSum(TreeNode root, int targetSum) {
            Stack<NodeFrame> nodeFrames = new Stack<>();
            nodeFrames.push(new NodeFrame(root, targetSum));
            while(!nodeFrames.isEmpty()) {
                NodeFrame nodeFrame = nodeFrames.pop();
                TreeNode node = nodeFrame.node();
                int rest = nodeFrame.rest();
                if (node == null) {
                    continue;
                }
                rest -= node.val;
                if (node.left == null && node.right == null) {
                    if (rest == 0) {
                        return true;
                    }
                    continue;
                }
                nodeFrames.push(new NodeFrame(node.left, rest));
                nodeFrames.push(new NodeFrame(node.right, rest));
            }
            return false;
        }
    }
    /*
     * 思ったこと
     * ・はじめ、sumを足していく方式にしようとしたが、rootがnullの時にwhileでまとめて処理できなかった。
     * と思ったけど、0を設定しておけばよかった。
     */
    // DFS 2_3の足していく方式
    class Solution2_4 {
        record NodeFrame(TreeNode node, int sum) {}
        public boolean hasPathSum(TreeNode root, int targetSum) {
            Stack<NodeFrame> nodeFrames = new Stack<>();
            nodeFrames.push(new NodeFrame(root, 0));
            while (!nodeFrames.isEmpty()) {
                NodeFrame nodeFrame = nodeFrames.pop();
                TreeNode node = nodeFrame.node();
                int sum = nodeFrame.sum();
                if (node == null) {
                    continue;
                }
                sum += node.val;
                if (node.left == null && node.right == null) {
                    if (sum == targetSum) {
                        return true;
                    }
                    continue;
                }
                nodeFrames.push(new NodeFrame(node.left, sum));
                nodeFrames.push(new NodeFrame(node.right, sum));
            }
            return false;
        }
    }
    /*
     * 思ったこと
     * ・nullのノードが入らないようにもできる。
     * whileの前でrootを判定、スタックにpushする前にleftとrightを判定する。
     */
    
    
    // BFS
    class Solution2_5 {
        record NodeFrame(TreeNode node, int sum) {}
        public boolean hasPathSum(TreeNode root, int targetSum) {
            Queue<NodeFrame> nodeFrames = new LinkedList<>();
            nodeFrames.add(new NodeFrame(root, 0));
            while (!nodeFrames.isEmpty()) {
                NodeFrame nodeFrame = nodeFrames.poll();
                TreeNode node = nodeFrame.node();
                int sum = nodeFrame.sum();
                if (node == null) {
                    continue;
                }
                sum += node.val;
                if (node.left == null && node.right == null) {
                    if (sum == targetSum) {
                        return true;
                    }
                    continue;
                }
                nodeFrames.add(new NodeFrame(node.left, sum));
                nodeFrames.add(new NodeFrame(node.right, sum));
            }
            return false;
        }
    }
    /*
     * 思ったこと
     * ・その時の値を持っているので取り出す順番を変えるくらい。 
     */
    
}
/*
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1210852552749355008
 *   https://github.com/hayashi-ay/leetcode/pull/30
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1218139084376113162
 *   https://github.com/shining-ai/leetcode/pull/25
 * https://github.com/rossy0213/leetcode/pull/14
 * https://discord.com/channels/1084280443945353267/1196472827457589338/1235138056881705042
 *   https://github.com/Mike0121/LeetCode/pull/5
 * https://github.com/sakupan102/arai60-practice/pull/26
 * https://github.com/fhiyo/leetcode/pull/27
 * https://github.com/SuperHotDogCat/coding-interview/pull/37
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/29
 * https://github.com/kazukiii/leetcode/pull/26
 * https://github.com/TORUS0818/leetcode/pull/27
 * https://github.com/nittoco/leetcode/pull/31
 * https://github.com/Ryotaro25/leetcode_first60/pull/27
 */
