public class ConvertSortedArrayToBinarySearchTreeStep3 {
    // 7m50s / 1m30s
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBSTHelper(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBSTHelper(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int middle = (left + right) / 2;
        TreeNode root = new TreeNode(nums[middle]);
        root.left = sortedArrayToBSTHelper(nums, left, middle - 1);
        root.right = sortedArrayToBSTHelper(nums, middle + 1, right);
        return root;
    }
}
