public class NextPermutationStep2 {

    /*
     * 参照
     * https://github.com/SuperHotDogCat/coding-interview/pull/8/files
     * https://github.com/shining-ai/leetcode/pull/58/files
     * https://github.com/hayashi-ay/leetcode/pull/67/files
     */
    // 入れ替える先のインデックスをforで前から探す
    public void nextPermutation2_1(int[] nums) {
        int swapIndex = -1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                swapIndex = i;
                break;
            }
        }

        if (swapIndex == -1) {
            Arrays.sort(nums);
            return;
        }

        int minValue = nums[swapIndex];
        int nextNumIndex = swapIndex + 1;
        int maxValue = Integer.MAX_VALUE;
        for (int j = swapIndex + 1; j < nums.length; j++) {
            if (minValue < nums[j] && nums[j] <= maxValue) {
                nextNumIndex = j;
                maxValue = nums[j];
            }
        }
        swap(nums, swapIndex, nextNumIndex);
        reverse(nums, swapIndex + 1);
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private void reverse(int[] nums, int i) {
        int j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    // できるだけJDKを使う
    public void nextPermutation2_2(int[] nums) {
        int swapIndex = -1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                swapIndex = i;
                break;
            }
        }

        if (swapIndex == -1) {
            Arrays.sort(nums);
            return;
        }

        int minValue = nums[swapIndex];
        int nextNumIndex = swapIndex + 1;
        int maxValue = Integer.MAX_VALUE;
        for (int j = swapIndex + 1; j < nums.length; j++) {
            if (minValue < nums[j] && nums[j] <= maxValue) {
                nextNumIndex = j;
                maxValue = nums[j];
            }
        }

        /*
         * jdkを使う場合
         * swapするためには、listにする必要がある：swap(List<?> list, int i, int j)
         * 変数を定義する必要があるため問題文に合わないが、一旦書いてみる
         */

        /*
         * int[] -> List<Integer>
         * 
         * 固定サイズのlist（型はlistで中身はarray）として。今の問題ならこれで足りる
         * toList()を使うと、読み取り専用になり変更できない: https://blog1.mammb.com/entry/2021/06/07/000000
         *   ex) List<Integer> numList = Arrays.stream(nums).boxed().toList();
         * 
         * List<Integer> numList = Arrays.stream(nums).boxed().collect(Collectors.toList());
         * Collections.swap(numList, swapIndex, nextNumIndex);
         * nums = numList.stream().mapToInt(i -> i).toArray();
         * Collections.swap(numList, swapIndex, nextNumIndex);
         */

        /*
         * int[] -> Integer[] -> List<Integer>
         * 
         * Integer[] boxedNums = Arrays.stream(nums).boxed().toArray(Integer[]::new);
         * List<Integer> numList2 = Arrays.asList(boxedNums);
         * Collections.swap(numList2, swapIndex, nextNumIndex);
         * nums = numList.stream().mapToInt(i -> i).toArray();
         * */
        swap(nums, swapIndex, nextNumIndex);

        Arrays.sort(nums, swapIndex + 1, nums.length);
    }

    // 2_2の延長
    // 入れ替え先のインデックスをより少ないステップで見つける
    public void nextPermutation2_3(int[] nums) {
        int swapIndex = -1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                if (i == 0) {
                    // indexOf()あるいはlastIndexOf()は使えない
                    // 前後の大小関係を見る必要があるため
                    swapIndex = i;
                }

                swapIndex = i;
                break;
            }
        }

        if (swapIndex == -1) {
            Arrays.sort(nums);
            return;
        }

        int minValue = nums[swapIndex];
        int nextNumIndex = swapIndex + 1;
        List<Integer> numList = Arrays.stream(nums).boxed().collect(Collectors.toList());

        for (int j = swapIndex + 1; j < nums.length; j++) {
            if (minValue < nums[j]) {
                nextNumIndex = numList.lastIndexOf(nums[j]);
            }
        }

        Collections.swap(numList, swapIndex, nextNumIndex);
        int[] swappedNums = numList.stream().mapToInt(i -> i).toArray();
        for (int i = 0; i < swappedNums.length; i++) {
            nums[i] = swappedNums[i];
        }
        Arrays.sort(nums, swapIndex + 1, nums.length);
    }

}
