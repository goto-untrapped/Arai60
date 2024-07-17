public class SubsetsStep3 {
    // 3min / 3min / 3min
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> allSubsets = new ArrayList<>();
        return makeAllSubsets(nums, allSubsets, new ArrayList<Integer>(), 0);
    }

    private List<List<Integer>> makeAllSubsets(int[] nums, List<List<Integer>> allSubsets, ArrayList<Integer> subset, int start) {
        allSubsets.add(new ArrayList<>(subset));
        if (start == nums.length) {
            return allSubsets;
        }
        for (int i = start; i < nums.length; i++) {
            subset.add(nums[i]);
            makeAllSubsets(nums, allSubsets, subset, i + 1);
            subset.remove(subset.size() - 1);
        }
        return allSubsets;
    }
}
