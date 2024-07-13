	public int[] twoSumStep4(int[] nums, int target) {
        Map<Integer, Integer> num_to_index = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (num_to_index.containsKey(complement)) {
                return new int[] { i, num_to_index.get(complement) };
            }
            num_to_index.put(nums[i], i);
        }
        return null;
    }