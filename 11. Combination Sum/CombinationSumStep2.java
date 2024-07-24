public class CombinationSumStep2 {
    
    /*
     * 参照
     * https://github.com/SuperHotDogCat/coding-interview/pull/11/files
     * https://github.com/Mike0121/LeetCode/pull/1/files
     * https://github.com/Mike0121/LeetCode/compare/39.-Combination-Sum-(Stack)
     * https://github.com/shining-ai/leetcode/pull/52/files
     * https://github.com/hayashi-ay/leetcode/pull/65/files
     */
    
    // ループで書く。再帰は処理が深くてよくないという話なので、こちらでできたら書くと良さそう
    // 再帰は、最初の状態も覚えてる必要があってメモリにとって優しくないからだと思った
    // 書いた結果、仰々しくなってしまった・・・（時間的に間に合わない）
    // PythonだとStackにまとめて入れられるのをやりたかった
    // 3つのStackにするような方法は、あいだのコードが増えた時に分かりにくくなりそう
    public List<List<Integer>> combinationSum2_1(int[] candidates, int target) {
        List<List<Integer>> allCombinations = new ArrayList<>();
        Stack<Candidate> maybeCombinations = new Stack<>();
        maybeCombinations.push(new Candidate(new ArrayList<Integer>(), target, 0));

        while (!maybeCombinations.isEmpty()) {
            Candidate candidate = maybeCombinations.pop();
            List<Integer> combination = candidate.combination;
            int remain = candidate.remain;
            int startIndex = candidate.startIndex;

            if (remain == 0) {
                allCombinations.add(combination);
                continue;
            }
            if (remain < 0) {
                continue;
            }

            for (int i = startIndex; i < candidates.length; i++) {
                if (candidates[i] <= remain) {
                    combination.add(candidates[i]);
                    List<Integer> copiedCombination = new ArrayList<>(combination);
                    maybeCombinations.push(new Candidate(copiedCombination, remain - candidates[i], i));
                    combination.remove(combination.size() - 1);
                }
            }
        }
        return allCombinations;
    }

    private class Candidate {
        List<Integer> combination;
        int remain;
        int startIndex;

        public Candidate(List<Integer> combination, int remain, int startIndex) {
            this.combination = combination;
            this.remain = remain;
            this.startIndex = startIndex;
        }
    }
    
    /*
     * 時間計算量 / 空間計算量の算出方法を理解する
     * 時間計算量はO(候補の数 の ((ターゲット / 最小の候補) + 1)乗) = O(n * (target / min(n)) + 1)
     * 1候補あたり、最大で (ターゲット / 最小の候補) 回、数を組み合わせられる（今回の計算では自分より後しか見ないので、もっと小さくなるのか）
     * 最初の候補も選ぶ必要があるため、候補を選ぶたびに枝分かれすると考えると、((ターゲット / 最小の候補) + 1)回枝分かれする
     * これが候補の数の分ありえる
     * 
     * 空間計算量はO(ターゲット / 最小の候補) = O((target / min(n)) + 1)
     * 一番階層が深くなるのは組み合わせの個数が最大の時、これが(ターゲット / 最小の候補)
     */
}