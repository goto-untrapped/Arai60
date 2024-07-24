public class CombinationSumStep1 {
    
    // 解き直し
    // 計算量が分からず答えを見た
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> allCombinations = new ArrayList<>();
        composeCombinationSum(allCombinations, new ArrayList<Integer>(), candidates, target, 0);
        return allCombinations;
    }

    private void composeCombinationSum(List<List<Integer>> all, ArrayList<Integer> composed, int[] candidates, int sum, int startIndex) {
        if (sum == 0) {
            all.add(new ArrayList<Integer>(composed));
            return;
        }
        if (sum < 0) {
            return;
        }

        for (int i = startIndex; i < candidates.length; i++) {
            composed.add(candidates[i]);
            composeCombinationSum(all, composed, candidates, sum - candidates[i], i);
            composed.remove(composed.size() - 1);
        }
    }
    
}