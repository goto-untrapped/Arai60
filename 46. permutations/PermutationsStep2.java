public class PermutationsStep2 {
    
    /*
     * 参照
     * https://github.com/SuperHotDogCat/coding-interview/pull/12/files
     *   phase1の書き方で書いてみたい
     *   itertoolsより再帰backtrackの方が速いのか
     *   phase1のやり方、見る回数はbacktrackingと変わらない気がしたけど、そんなに遅いのか
     * https://github.com/shining-ai/leetcode/pull/50/files
     *   itertoolのやり方が理解できなかったけど、読んだら分かるくらいの状態を作っておく必要があるのだろうか
     */
    // ループで書く
    public List<List<Integer>> permute2_1(int[] nums) {
        List<List<Integer>> allPermutations = new ArrayList<>();

        Queue<List<Integer>> candidateCombinations = new LinkedList<>();
        candidateCombinations.add(new ArrayList<Integer>());
        
        for (int num : nums) {
            int iterateCount = candidateCombinations.size();
            for (int i = 0; i < iterateCount; i++) {

                List<Integer> permutation = candidateCombinations.poll();
                for (int j = 0; j <= permutation.size(); j++) {
                    List<Integer> addedPermutation = new ArrayList<>(permutation);
                    addedPermutation.add(j, num);
                    candidateCombinations.add(addedPermutation);
                }
                
            }
        }
        
        while (!candidateCombinations.isEmpty()) {
            allPermutations.add(candidateCombinations.poll());
        }
        
        return allPermutations;
    }
    
}
