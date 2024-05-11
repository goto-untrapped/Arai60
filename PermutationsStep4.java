public class PermutationsStep4 {
    
    // スタックを使う
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> permutations = new ArrayList<>();
        Stack<ArrayList<Integer>> parts = new Stack<>();

        parts.push(new ArrayList<>());
        while (!parts.isEmpty()) {
            ArrayList<Integer> part = parts.pop();
            if (part.size() == nums.length) {
                permutations.add(part);
                continue;
            }

            for (int num : nums) {
                if (part.indexOf(num) != -1) {
                    continue;
                }

                part.add(num);
                parts.push(new ArrayList<Integer>(part));
                part.remove(part.size() - 1);
            }
        }

        return permutations;
    }
    
}
