public class IntersectionOfTwoArraysStep1 {

    // 3ms / 43.15MB
    // 15分くらいかかった。コレクションの変換が面倒だった。
    // 時間計算量：O(n1 + n2)　n1はnums1の要素数、n2はnums2の要素数
    // 最大はnums1とnums2が全部ユニークだった場合。
    // 空間計算量：O(n1 + n2) 
    // mapがn1, setとlistがそれぞれ(n1 + n2) = 3 * n1 + 2 * n2 = n1 + n2
    public int[] intersectionA(int[] nums1, int[] nums2) {
        Set<Integer> uniqueIntersectionNums = new HashSet<>();
        List<Integer> intersectionNums;
        Map<Integer, Integer> existNumMap = new HashMap<>();
        for (int num : nums1) {
            if (!existNumMap.containsKey(num)) {
                existNumMap.put(num, 0);
            }
        }
        
        for (int num : nums2) {
            if (existNumMap.containsKey(num)) {
                uniqueIntersectionNums.add(num);
            }
        }

        intersectionNums = new ArrayList<>(uniqueIntersectionNums);
        int[] ans = new int[intersectionNums.size()];
        for (int i = 0; i < intersectionNums.size(); i++) {
            ans[i] = intersectionNums.get(i);
        }
        return ans;
    }
    
    // ビットベクトルでやれると思った。
    // 2の(1000 - 1)乗を計算することになり、データ型の範囲を超えていた。
    // Javaの整数、intは32ビット、longは64ビット
    public int[] intersectionB(int[] nums1, int[] nums2) {
        int bits1 = 0;
        int bits2 = 0;
        int uniqueBits = 0;
        List<Integer> uniqueNums = new ArrayList<>();

        for (int num : nums1) {
            int mask = 1 << num;
            if ((bits1 & mask) == 0) {
                bits1 |= mask;
            }
        }

        for (int num : nums2) {
            int mask = 1 << num;
            if ((bits2 & mask) == 0) {
                bits2 |= mask;
            }
        }

        uniqueBits = bits1 & bits2;
        int num = 0;
        while (uniqueBits != 0) {
            if ((uniqueBits & 1) == 1) {
                uniqueNums.add(num);
            }
            uniqueBits >>= 1;
            num++;
        }

        return uniqueNums.stream().mapToInt(i -> i).toArray();
    }
    
}
