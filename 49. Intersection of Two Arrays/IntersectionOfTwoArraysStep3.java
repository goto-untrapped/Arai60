public class IntersectionOfTwoArraysStep3 {
    
    /*
     * Step1のやり方をよりきれいだと思った形にしたものを採用
     * 時間計算量：O(n1 + n2) n1はnums1のサイズ、n2はnums2のサイズ 
     * 空間計算量：O(n1) mapはn1、listは最大でn1
     * */ 
    public int[] intersection(int[] nums1, int[] nums2) {
        Map<Integer, Integer> numToCount = new HashMap<>();
        List<Integer> intersectionNums = new ArrayList<>();

        for (int num : nums1) {
            numToCount.put(num, 1);
        }

        for (int num : nums2) {
            if (numToCount.containsKey(num) && numToCount.get(num) == 1) {
                intersectionNums.add(num);
                numToCount.put(num, 0);
            }
        }

        return intersectionNums.stream().mapToInt(i -> i).toArray();
    }
    
}
