public class NumberOfConnectedComponentsInAnUndirectedGraphStep4 {
    // Step1 書き直し
    public int countComponents5_1(int n, int[][] edges) {
        Queue<int[]> connected = new LinkedList<>();
        boolean[] seen = new boolean[edges.length];
        int numComponents = 0;
        for (int i = 0; i < edges.length; i++) {
            if (seen[i]) {
                continue;
            }
            connected.offer(edges[i]);
            seen[i] = true;
            while (!connected.isEmpty()) {
                int[] edge = connected.poll();
                int source = edge[0];
                int destination = edge[1];
                for (int j = i + 1; j < edges.length; j++) {
                    if (seen[j]) {
                        continue;
                    }
                    if (source != edges[j][0] && source != edges[j][1] 
                            && destination != edges[j][0] && destination != edges[j][1]) {
                        continue;
                    }
                    connected.offer(edges[j]);
                    seen[j] = true;
                }
            }
            numComponents++;
        }

        HashSet<Integer> existed = new HashSet<>();
        for (int[] edge : edges) {
            existed.add(edge[0]);
            existed.add(edge[1]);
        }
        return numComponents + n - existed.size();
    }
    
    // Step2_1 隣接リスト + BFS 書き直し
    public int countComponents(int n, int[][] edges) {
        ArrayList<Integer>[] adjacentList = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adjacentList[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adjacentList[edge[0]].add(edge[1]);
            adjacentList[edge[1]].add(edge[0]);
        }

        int numConnectedComponents = 0;
        boolean[] isSeens = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (isSeens[i]) {
                continue;
            }
            numConnectedComponents++;
            isSeens[i] = true;
            Queue<Integer> connected = new LinkedList<>();
            connected.offer(i);
            while (!connected.isEmpty()) {
                int node = connected.poll();
                for (int adjacentNode : adjacentList[node]) {
                    if (isSeens[adjacentNode]) {
                        continue;
                    }
                    connected.offer(adjacentNode);
                    isSeens[adjacentNode] = true;
                }
            }
        }
        return numConnectedComponents;
    }
    
    // Step2_2 隣接リスト + DFS(再帰) 書き直し
    class Solution5_3 {
        public int countComponents(int n, int[][] edges) {
            ArrayList<Integer>[] adjacentList = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                adjacentList[i] = (new ArrayList<>());
            }
            for (int[] edge : edges) {
                adjacentList[edge[0]].add(edge[1]);
                adjacentList[edge[1]].add(edge[0]);
            }

            int numComponents = 0;
            boolean isSeens[] = new boolean[n];
            for (int node = 0; node < n; node++) {
                if (isSeens[node]) {
                    continue;
                }
                numComponents++;
                traverseConnectedNodes(adjacentList, isSeens, node);
            }
            return numComponents;
        }

        private void traverseConnectedNodes(ArrayList<Integer>[] adjacentList, boolean[] isSeens, int startNode) {
            isSeens[startNode] = true;
            for (int adjacentNode : adjacentList[startNode]) {
                if (isSeens[adjacentNode]) {
                    continue;
                }
                traverseConnectedNodes(adjacentList, isSeens, adjacentNode);
            }
        }
    }
    
    // Step2_3 隣接リスト + BFS(Stack) 書き直し
    class Solution5_4 {
        public int countComponents(int n, int[][] edges) {
            ArrayList<Integer>[] adjacentList = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                adjacentList[i] = (new ArrayList<>());
            }
            for (int[] edge : edges) {
                adjacentList[edge[0]].add(edge[1]);
                adjacentList[edge[1]].add(edge[0]);
            }

            int numComponents = 0;
            boolean isSeens[] = new boolean[n];
            for (int node = 0; node < n; node++) {
                if (isSeens[node]) {
                    continue;
                }
                numComponents++;
                traverseConnectedNodes(adjacentList, isSeens, node);
            }
            return numComponents;
        }

        private void traverseConnectedNodes(ArrayList<Integer>[] adjacentList, boolean[] isSeens, int startNode) {
            Stack<Integer> connected = new Stack<>();
            connected.add(startNode);
            while (!connected.isEmpty()) {
                int node = connected.pop();
                if (isSeens[node]) {
                    continue;
                }
                isSeens[node] = true;
                for (int adjacentNode : adjacentList[node]) {
                    if (isSeens[adjacentNode]) {
                        continue;
                    }
                    connected.add(adjacentNode);
                }
            }
        }
    }
}
