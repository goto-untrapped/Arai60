public class MinimumDepthOfBinaryTreeStep2 {
    // 一番素直なのはBFSだと思った。最初の葉ノードが最短深さになるため。
    class Solution2_1 {
        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            LinkedList<NodeAndDepth> nodeAndDepths = new LinkedList<>();
            nodeAndDepths.offer(new NodeAndDepth(root, 1));
            while (!nodeAndDepths.isEmpty()) {
                NodeAndDepth nodeAndDepth = nodeAndDepths.poll();
                TreeNode node = nodeAndDepth.node();
                int depth = nodeAndDepth.depth();
                if (node.left == null && node.right == null) {
                    return depth;
                }
                if (node.left != null) {
                    nodeAndDepths.offer(new NodeAndDepth(node.left, depth + 1));
                }
                if (node.right != null) {
                    nodeAndDepths.offer(new NodeAndDepth(node.right, depth + 1));
                }
            }
            return -1; // never reached
        }
        record NodeAndDepth(TreeNode node, int depth) {}
    }
    /*
     * 思ったこと
     * ・到達しないreturn を回避する方法は、
     *   ・最短を見つけたらbreakして最後に返す
     *   ・while (true)
     * ができる。while(true)はもしパターン漏れでループから抜けられなかった時が怖い。
     * breakするのもいいが、到達しないことをコメントで書いておくほうが、返せるところを引き延ばして
     * 最後に持ってくるより引っかかるものが少なかった。ロジックのために構造をいじる、という優先度に引っかかった気がする。
     */
    
    
    // BFSで2つの入れ物を使う。
    class Solution2_2 {
        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            LinkedList<TreeNode> nodes = new LinkedList<>();
            nodes.offer(root);
            int minDepth = 1;
            while (!nodes.isEmpty()) {
                LinkedList<TreeNode> nextNodes = new LinkedList<>();
                for (TreeNode node : nodes) {
                    if (node == null) {
                        continue;
                    }
                    if (node.left == null && node.right == null) {
                        return minDepth;
                    }
                    nextNodes.offer(node.left);
                    nextNodes.offer(node.right);
                }
                nodes = nextNodes;
                minDepth++;
            }
            return -1; // never reached;
        }
    }
    /*
     * 思ったこと
     * ・深さを数えられるので、record を定義しなくてもいいのはタイプ量が少なく楽で助かる。
     * でも、この動きを正しく書けるかと言われると、自分はSolution2_1の単純だと思う方を取る。
     */
    
    
    // 再帰 minDepthを代入し直す
    class Solution2_3 {
        private int INITIALIZED = Integer.MAX_VALUE;
        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return minDepthHelper(root);
        }

        private int minDepthHelper(TreeNode node) {
            if (node.left == null && node.right == null) {
                return 1;
            }
            int minDepth = INITIALIZED;
            if (node.left != null) {
                minDepth = minDepthHelper(node.left);
            }
            if (node.right != null) {
                minDepth = Math.min(minDepth, minDepthHelper(node.right));
            }
            return minDepth + 1;
        }
    }
    /*
     * 思ったこと
     * ・ノードの最大個数が100000のため、再帰で実行するとStackOverflow例外になるのでは？
     * JVMのヒープサイズが1MBだとして、1スタックフレームが10バイトならぎりぎりか。
     * TreeNodeもノード分を置くから無理そう。
     * ・分岐の書き方の見た目は対称に見えるけど、処理は対称じゃないところが気になる。
     * 対称に見える分岐処理を書くことからまずいのかなという気がしてくる。
     */
    
    
    // 再帰 直接深さを返していく 左がなければ右をたどる ifは1まとまり
    class Solution2_4 {
        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return minDepthHelper(root);
        }

        private int minDepthHelper(TreeNode node) {
            if (node.left == null && node.right == null) {
                return 1;
            } else if (node.left == null && node.right != null) {
                return minDepthHelper(node.right) + 1;
            } else if (node.right == null && node.left != null) {
                return minDepthHelper(node.left) + 1;
            } else {
                return Math.min(minDepthHelper(node.left), minDepthHelper(node.right)) + 1;
            }
        }
    }
    // と思ったけど、下の方が分岐文がシンプルか。
    // また、同じまとまりだと思うifごとに分けた。
    class Solution2_5 {
        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return minDepthHelper(root);
        }

        private int minDepthHelper(TreeNode node) {
            if (node == null) {
                return 0;
            } 
            if (node.left == null && node.right == null) {
                return 1;
            } 
            if (node.left == null) {
                return minDepthHelper(node.right) + 1;
            } else if (node.right == null) {
                return minDepthHelper(node.left) + 1;
            } else {
                return Math.min(minDepthHelper(node.left), minDepthHelper(node.right)) + 1;
            }
        }
    }
    /*
     * 思ったこと
     * ・左があれば左をたどる という書き方は、直接returnする場合は使えなさそう。はじめ、できると思っていた。
     * 原因：左ノードがあれば、にすると、左右あるノードもそこに入る。左ノードがなければ、にすると、左右あるノードがelseまで残ってくれて、
     * 意図した分岐に入ってくれる。
     * ・if (node.left == null && node.right == null) {} のくだりはなくても通ったな。
     * 再帰の回数は増えるけど、カウントはその下の部分でまかなってくれる。
     * ・左があれば左をたどる、と書く場合と、左がなければ右をたどる、と書く場合で、どちらの方が分かりやすいのかについて迷った。
     * 左がなければ右をたどる、と書いた方が、意図が感じやすく、最小深さを求める時に片方が空のノードは両方見るとまずそう、
     * と相手が見た時に分かりやすいのかなと思ったけど、左なら左をたどる、のほうがすんなり読める気もする。
     */
    
    
    // 再帰 深さを持ち回って葉ノードの時だけ更新
    class Solution2_6 {
        private int minDepth;
        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            initializeMinDepth();
            traverseTree(root, 1);
            return minDepth;
        }

        private void initializeMinDepth() {
            minDepth = Integer.MAX_VALUE;
        }

        private void traverseTree(TreeNode node, int nodeDepth) {
            if (node == null) {
                return;
            }
            if (node.left == null && node.right == null) {
                minDepth = Math.min(minDepth, nodeDepth);
            }
            traverseTree(node.left, nodeDepth + 1);
            traverseTree(node.right, nodeDepth + 1);
        }
    }
    /*
     * 思ったこと
     * ・はじめ、minDepthを引数で渡していたが、再帰が呼び出し元に戻るとminDepthが初期化されてしまう。
     * 解決策として、minDepthをIntegerとしたが、やっぱり1つの参照でい続けられない。
     * また、Integerはコレクション型を使うための繋ぎのイメージがあり、参照を渡したいために使うのは本来の意図と違っていると思った。
     * 引数で持てるようにする方法としては、リストを引数にしてminDepthを追加し、呼び出し元で最小値を求めるとかだろうか。
     * https://discord.com/channels/1084280443945353267/1183683738635346001/1202681140008194088
     * ・共通変数を色んな関数で使う形式は怖いと思っていて、また初期値を入れておくと簡単に使い始められてしまうと思ったので、
     * 使い始めたタイミングが分かればいいと思って、初期化用の関数を用意した。
     * 
    class Solution2_6_WA {
        private int INITIALIZED = Integer.MAX_VALUE;
        public int minDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            Integer minDepth = INITIALIZED;
            traverseTree(root, minDepth, 1);
            return minDepth;
        }

        private void traverseTree(TreeNode node, Integer minDepth, Integer nodeDepth) {
            if (node == null) {
                return;
            }
            if (node.left == null && node.right == null) {
                if (nodeDepth < minDepth) {
                    minDepth = nodeDepth;
                }
                minDepth = Math.min(minDepth, nodeDepth);
            }
            traverseTree(node.left, minDepth, nodeDepth + 1);
            traverseTree(node.right, minDepth, nodeDepth + 1);
        }
    }
     */
}
/*
 * https://discord.com/channels/1084280443945353267/1183683738635346001/1202680261108826184
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1209429632579801141
 *   https://github.com/hayashi-ay/leetcode/pull/26
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1216397022194569326
 *   https://github.com/shining-ai/leetcode/pull/22
 * https://github.com/sakupan102/arai60-practice/pull/23
 * https://discord.com/channels/1084280443945353267/1196472827457589338/1237821650527981608
 *   https://github.com/Mike0121/LeetCode/pull/11
 * https://github.com/SuperHotDogCat/coding-interview/pull/36
 * https://github.com/kazukiii/leetcode/pull/23
 * https://github.com/TORUS0818/leetcode/pull/24
 * https://github.com/Ryotaro25/leetcode_first60/pull/24
 */
