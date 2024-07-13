public class NumberOfConnectedComponentsInAnUndirectedGraphStep3 {
    // 9min / 5min / 4m30s
    public int countComponents(int n, int[][] edges) {
        HashMap<Integer, List<Integer>> nodeToAdjs = new HashMap<>();
        for (int[] edge : edges) {
            if (!nodeToAdjs.containsKey(edge[0])) {
                nodeToAdjs.put(edge[0], new ArrayList<>());
            }
            nodeToAdjs.get(edge[0]).add(edge[1]);

            if (!nodeToAdjs.containsKey(edge[1])) {
                nodeToAdjs.put(edge[1], new ArrayList<>());
            }
            nodeToAdjs.get(edge[1]).add(edge[0]);
        }

        int numComponents = 0;
        HashSet<Integer> seen = new HashSet<>();
        for (int node = 0; node < n; node++) {
            if (seen.contains(node)) {
                continue;
            }
            numComponents++;
            seen.add(node);
            markNextAsSeen(nodeToAdjs, seen, node);
        }
        return numComponents;
    }

    private void markNextAsSeen(HashMap<Integer, List<Integer>> nodeToAdjs, HashSet<Integer> seen, int inspect) {
        if (!nodeToAdjs.containsKey(inspect)) {
            return;
        }
        for (int adjNode : nodeToAdjs.get(inspect)) {
            if (seen.contains(adjNode)) {
                continue;
            }
            seen.add(adjNode);
            markNextAsSeen(nodeToAdjs, seen, adjNode);
        }
    }
    
    /*
     * 思ったこと
     * ・業務では再帰をしない方法で書きたいが、DFSの方が分かりやすく書けたため、まずは動くソースを書くことを優先して、DFSを使った。
     * ・mapの中身は抽象化する使い方を想定していないため、ArrayListでよかった。
     * ・next と adjacent という言葉の使い方に迷ったが、隣接リストという名前から連想できる adjacent をメインで使った。
     */
}
