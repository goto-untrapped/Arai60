public class PermutationsStep3 {
    
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> allPermutations = new ArrayList<>();
        addAllPermutations(allPermutations, nums, new ArrayList<Integer>());
        return allPermutations;
    }

    private void addAllPermutations(List<List<Integer>> allPermutations, int[] nums, ArrayList<Integer> permutation) {
        if (permutation.size() == nums.length) {
            allPermutations.add(new ArrayList<Integer>(permutation));
            return;
        }

        for (int num : nums) {
            if (permutation.contains(num)) {
                continue;
            }
            permutation.add(num);
            addAllPermutations(allPermutations, nums, permutation);
            permutation.remove(permutation.size() - 1);
        }
    }
    
}
