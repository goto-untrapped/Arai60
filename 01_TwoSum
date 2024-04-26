```
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> search = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (search.containsKey(complement)) {
                return new int[] { i, search.get(complement) };
            }
            search.put(nums[i], i);
        }
        return null;
    }
}
```
