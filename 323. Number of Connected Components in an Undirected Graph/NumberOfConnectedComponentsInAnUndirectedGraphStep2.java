public class NumberOfConnectedComponentsInAnUndirectedGraphStep2 {
    /*
     * 隣接リスト + BFS
     * 40min
     */
    public int countComponents2_1(int n, int[][] edges) {
        HashMap<Integer, List<Integer>> nodeToAdjacent = new HashMap<>();
        HashSet<Integer> seen = new HashSet<>();
        for (int[] edge : edges) {
            if (!nodeToAdjacent.containsKey(edge[0])) {
                nodeToAdjacent.put(edge[0], new ArrayList<>());
            }
            nodeToAdjacent.get(edge[0]).add(edge[1]);

            if (!nodeToAdjacent.containsKey(edge[1])) {
                nodeToAdjacent.put(edge[1], new ArrayList<>());
            }
            nodeToAdjacent.get(edge[1]).add(edge[0]);
        }

        int connectedComponentsNum = 0;
        Queue<Integer> connected = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (seen.contains(i)) {
                continue;
            }
            connectedComponentsNum++;
            seen.add(i);
            if (!nodeToAdjacent.containsKey(i)) {
                continue;
            }
            connected.offer(i);
            while (!connected.isEmpty()) {
                int node = connected.poll();
                for (int adjacentNode : nodeToAdjacent.get(node)) {
                    if (seen.contains(adjacentNode)) {
                        continue;
                    }
                    connected.offer(adjacentNode);
                    seen.add(adjacentNode);
                }
            }
        }
        return connectedComponentsNum;
    }
    /*
     * 思ったこと
     * ・はじめ nodeToConnected にしていたけど、全体として1つのコンポーネントか調べる
     * connected と混合して見にくいと思い、似た意味だけど隣接リストとして意味を分けられそうな nodeToAdjacent に変更した。
     * ・seen を何回も確認したり、queue の処理でインデントが深くなりがちで読むのみ労力を使うと思ったが、
     * 隣接リストがそもそも2回数えているからまだ seen を使った方がスッキリした処理になるし、やってることは繋がっているものを
     * 見回って確かめているだけで、n から見た node だろうと queue から見た node だろうと同じ処理をしていると思ったので、
     * 意外とそこまで読みにくくないと思った。
     * ・n の中身を i という名前で見ているのは何の名前か連想しにくくよくないと思ったが、node は既に使っていたため、
     * n のインデックスがそのままそのノードの値になる、という捉え方もできると思い、そのままにした。
     */
    
    
    /*
     * 隣接リスト + DFS
     * 20min
     */
    class Solution2_2 {
        public int countComponents(int n, int[][] edges) {
            HashMap<Integer, List<Integer>> nodeToAdjacent = new HashMap<>();
            HashSet<Integer> seen = new HashSet<>();
            for (int[] edge : edges) {
                if (!nodeToAdjacent.containsKey(edge[0])) {
                    nodeToAdjacent.put(edge[0], new ArrayList<>());
                }
                nodeToAdjacent.get(edge[0]).add(edge[1]);

                if (!nodeToAdjacent.containsKey(edge[1])) {
                    nodeToAdjacent.put(edge[1], new ArrayList<>());
                }
                nodeToAdjacent.get(edge[1]).add(edge[0]);
            }

            int numComponents = 0;
            for (int node = 0; node < n; node++) {
                if (seen.contains(node)) {
                    continue;
                }
                numComponents++;
                seen.add(node);
                markConnectedNode(nodeToAdjacent, seen, node);
            }
            return numComponents;
        }

        private void markConnectedNode(HashMap<Integer, List<Integer>> nodeToAdjacent, HashSet<Integer> seen, int node) {
            if (!nodeToAdjacent.containsKey(node)) {
                return;
            }
            for (int adjacentNode : nodeToAdjacent.get(node)) {
                if (seen.contains(adjacentNode)) {
                    continue;
                }
                seen.add(adjacentNode);
                markConnectedNode(nodeToAdjacent, seen, adjacentNode);
            }
            return;
        }
    }
    /*
     * 思ったこと
     * ・numComponents で十分分かりやすいと思った。
     * ・2_1 で行った quque への手動追加を関数として分けることで、一度に処理する情報量が減ったうえ、
     * 探し方もDFS関数のプログラムの流れ方で表現できたため、だいぶ読みやすくなったと思う。
     */
    

    /*
     * 隣接リスト + stack
     */
    class Solution2_3 {
        public int countComponents(int n, int[][] edges) {
            HashMap<Integer, List<Integer>> adjs = new HashMap<>();
            for (int[] edge : edges) {
                if (!adjs.containsKey(edge[0])) {
                    adjs.put(edge[0], new ArrayList<>());
                }
                adjs.get(edge[0]).add(edge[1]);

                if (!adjs.containsKey(edge[1])) {
                    adjs.put(edge[1], new ArrayList<>());
                }
                adjs.get(edge[1]).add(edge[0]);
            }

            HashSet<Integer> seen = new HashSet<>();
            int numComponents = 0;
            for (int node = 0; node < n; node++) {
                if (seen.contains(node)) {
                    continue;
                }
                numComponents++;
                markConnectedSeen(adjs, seen, node);
            }
            return numComponents;
        }

        private void markConnectedSeen(HashMap<Integer, List<Integer>> adjs, HashSet<Integer> seen, int inspect) {
            Stack<Integer> connected = new Stack<>();
            connected.add(inspect);
            while (!connected.isEmpty()) {
                int node = connected.pop();
                if (seen.contains(node)) {
                    continue;
                }
                seen.add(node);
                if (!adjs.containsKey(node)) {
                    continue;
                }
                for (int nextNode : adjs.get(node)) {
                    connected.add(nextNode);
                }    
            }
        }
    }
    
    
    /*
     * Union Find
     */
    class Solution2_4 {
        public int countComponents(int n, int[][] edges) {
            UnionFind nodeGroup = new UnionFind(n);
            for (int[] edge : edges) {
                nodeGroup.union(edge[0], edge[1]);
            }

            int numComponents = 0;
            for (int node = 0; node < n; node++) {
                if (!nodeGroup.isRoot(node)) {
                    continue;
                }
                numComponents++;
            }
            return numComponents;
        }
    }

    class UnionFind {
        int[] groups;

        UnionFind(int n) {
            groups = new int[n];
            for (int i = 0; i < n; i++) {
                groups[i] = i;
            }
        }

        void union(int node1, int node2) {
            int group1 = find(node1);
            int group2 = find(node2);
            if (group1 == group2) {
                return;
            }
            groups[group2] = group1;
        }

        int find(int node) {
            if (groups[node] == node) {
                return node;
            }
            return find(groups[node]);
        }

        boolean isRoot(int node) {
            return groups[node] == node;
        }
    }
}

/*
 * https://discord.com/channels/1084280443945353267/1183683738635346001/1197650475160449025
 * https://github.com/hayashi-ay/leetcode/pull/37
 *   https://discord.com/channels/1084280443945353267/1200089668901937312/1212480543673950249
 * https://github.com/shining-ai/leetcode/pull/19
 * https://github.com/sakupan102/arai60-practice/pull/22
 *   https://discord.com/channels/1084280443945353267/1227073733844406343/1236922609254269000
 * https://github.com/kazukiii/leetcode/pull/20
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/21
 */
