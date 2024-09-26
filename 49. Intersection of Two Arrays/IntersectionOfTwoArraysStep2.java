public class IntersectionOfTwoArraysStep2 {
    
    /*
     * 他の回答を参照
     * LeetCodeの回答を参照し、手が止まったら書き直すを実施
     * 回答と完全に一致ではない
     */
    
    /*
     * mapだけを使って重複num判定している。
     * Step1でやったことの上位互換だと思った。
     * Step1ではmapを存在チェックとしてしか使えなかったが、
     * nums1をput(num, 1)とputすれば、nums1に複数回同じnumが出てきても、カウントは1のまま。
     * nums2で同じ数字が見つかった後、カウントを0にすれば、nums2にもう一回同じnumが出てきても、入れなくても済む。
     * あ、だからif文の条件に seen.get(x) == 1 も入っているのか。
     */
    public int[] intersection2_1(int[] nums1, int[] nums2) {
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
    
    /*
     * ソートしてから配列ごとのポインタで比較しながら配列を走査
     * 同じ数字しか見たくないから、大小があったらその先も同じ数はもう出てこないので、ポインタを進めればいい
     * 片方の配列で進み切っても、もう片方の配列にしかない要素は見なくていいので、while は かつ で見ればよい
     */
    public int[] intersection2_2(int[] nums1, int[] nums2) {
        Set<Integer> intersectionNums = new HashSet<>();
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        
        int N = nums1.length;
        int M = nums2.length;
        int pointer1 = 0;
        int pointer2 = 0;

        while (pointer1 < N && pointer2 < M) {
            if (nums1[pointer1] == nums2[pointer2]) {
                intersectionNums.add(nums1[pointer1]);
                pointer1++;
                pointer2++;
            } else if (nums1[pointer1] < nums2[pointer2]) {
                pointer1++;
            } else {
                pointer2++;
            }
        }

        int K = intersectionNums.size();
        int[] ans = new int[K];
        int index = 0;
        for (int num : intersectionNums) {
            ans[index++] = num;
        }
        return ans;
    }

    /*
     * Setの標準機能を使う
     */
    public int[] intersection2_3(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        List<Integer> ans = new ArrayList<>();
        
        for (int num : nums1) {
            set1.add(num);
        }
        for (int num : nums2) {
            set2.add(num);
        }
        
        set1.retainAll(set2);
        ans.addAll(set1);
        
        return ans.stream().mapToInt(i -> i).toArray();
    }
    
    /*
     * 重複を取り除くのみSetを使い、判定は自力で書く
     * 2つのsetの長さが違う時、短い方のsetが最長の重複配列
     */
    public int[] intersection2_4(int[] nums1, int[] nums2) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();

        for (int num : nums1) {
            set1.add(num);
        }
        for (int num : nums2) {
            set2.add(num);
        }

        if (set1.size() < set2.size()) {
            return intersectionNums(set1, set2);
        } else {
            return intersectionNums(set2, set1);
        }
    }

    private int[] intersectionNums(Set<Integer> set1, Set<Integer> set2) {
        int[] ans = new int[set1.size()];
        int index = 0;
        for (int num : set1) {
            if (set2.contains(num)) {
                ans[index++] = num;
            }
        }
        return Arrays.copyOf(ans, index);
    }
    
}