public class CombinationSumStep3 {
    
    /*
     * 気を付けた所
     * ・関数名や変数名を分かりやすくする
     * ・処理の流れを分かりやすくする
     * ・whileループで書いたものと実行時間・メモリ量に差が見られなかったため、
     * タイプ量が少ない方の深さ探索を採用
     * ・関数のパラメータは、重要なものから左側に置く
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> allCombinations = new ArrayList<>();
        addCombinationsMeetTarget(allCombinations, candidates, target, 0, new ArrayList<Integer>());
        return allCombinations;
    }

    private void addCombinationsMeetTarget( List<List<Integer>> allCombinations, 
                                            int[] candidates, 
                                            int remain, 
                                            int startIndex, 
                                            ArrayList<Integer> combination) {
        
        if (remain == 0) {
            allCombinations.add(new ArrayList<Integer>(combination));
            return;
        }
        if (remain < 0) {
            return;
        }

        for (int i = startIndex; i < candidates.length; i++) {
            if (candidates[i] > remain) {
                continue;
            }
            combination.add(candidates[i]);
            addCombinationsMeetTarget(allCombinations, candidates, remain - candidates[i], i, combination);
            combination.remove(combination.size() - 1);
        }
    }
    
}