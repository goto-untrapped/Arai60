public class PermutationsStep1 {
    
    /*
     * 余りを使ってインデックスを制御すれば楽に書けそうと思ったけど、
     * backtrackingと合わせた時の書き方が分からず、10分経ってしまった。
     * 答えを見たところ、単純に!contains()を使って全通りを見ていた。(そういえば問題文も要素はユニークだと書いていた)
     * 何も書けないのが一番まずいので、最初に思いついたやり方が難しそうなら、
     * 一旦区切りをつけて、ブルートフォースでまずは考える。TLEになっても何かしら書く。
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> allPermutations = new ArrayList<>();
        addPermutations(allPermutations, nums, new ArrayList<Integer>());
        return allPermutations;
    }

    private void addPermutations(List<List<Integer>> allPermutations, int[] nums, ArrayList<Integer> permutation) {
        if (permutation.size() == nums.length) {
            allPermutations.add(new ArrayList<Integer>(permutation));
            return;
        }

        for (int num : nums) {
            if (permutation.contains(num)) {
                continue;
            }
            permutation.add(num);
            addPermutations(allPermutations, nums, permutation);
            permutation.remove(permutation.size() - 1);
        }
    }
    
}
