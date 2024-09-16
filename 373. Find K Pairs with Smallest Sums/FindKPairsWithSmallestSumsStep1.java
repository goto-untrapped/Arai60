public class FindKPairsWithSmallestSumsStep1 {
    /*
     * 解答を見た。累計2h弱くらい。
     * 時間計算量:O(min(klogk, len1*len2*log(len1*len2))
     * ⇒ k<=len1*len2 なのでO(klogk) で足りると思ったが、最悪ケースの場合は
     * len1, len2がmaxの時で、その意味合いを表すためにはやっぱり配列の長さで書いた方が分かりやすいか。 
     * 空間計算量:O(min(k, (len1*len2))
     */
    class Solution {
        record SumAndIndex(int sum, int index1, int index2) {}

        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            List<List<Integer>> kSmallestNums = new ArrayList<>();
            PriorityQueue<SumAndIndex> sumAndIndexes = new PriorityQueue<>((a, b) -> a.sum() - b.sum());
            sumAndIndexes.offer(new SumAndIndex(nums1[0] + nums2[0], 0, 0));
            Set<SumAndIndex> visitedIndexPairs = new HashSet<>();
            visitedIndexPairs.add(new SumAndIndex(nums1[0] + nums2[0], 0, 0));
            int numPairs = 0;
            while (numPairs < k && sumAndIndexes.size() > 0) {
                SumAndIndex sumAndIndex = sumAndIndexes.poll();
                int index1 = sumAndIndex.index1();
                int index2 = sumAndIndex.index2();

                List<Integer> newNums = new ArrayList<>();
                newNums.add(nums1[index1]);
                newNums.add(nums2[index2]);
                kSmallestNums.add(newNums);
                numPairs++;

                if (index1 + 1 < nums1.length 
                        && !visitedIndexPairs.contains(new SumAndIndex(nums1[index1 + 1] + nums2[index2], index1 + 1, index2))) {
                    
                    sumAndIndexes.offer(new SumAndIndex(nums1[index1 + 1] + nums2[index2], index1 + 1, index2));
                    visitedIndexPairs.add(new SumAndIndex(nums1[index1 + 1] + nums2[index2], index1 + 1, index2));
                }
                if (index2 + 1 < nums2.length 
                        && !visitedIndexPairs.contains(new SumAndIndex(nums1[index1] + nums2[index2 + 1], index1, index2 + 1))) {
                    
                    sumAndIndexes.offer(new SumAndIndex(nums1[index1] + nums2[index2 + 1], index1, index2 + 1));
                    visitedIndexPairs.add(new SumAndIndex(nums1[index1] + nums2[index2 + 1], index1, index2 + 1));
                }
            }
            return kSmallestNums;
        }
    }
    /*
     * ・[1,1,1][1,2,3]k=3の場合も、[1,2]が入っても[,1][1,]まではちょうど見れるから
     * やっぱり[1,1]×3になれるという感覚すごいな。それがないので自力でGoが出せないのだけれど、どうしたものか。
     * 　https://discord.com/channels/1084280443945353267/1183683738635346001/1187654137257672745
     * 　・上記のようにマトリックスで考えたら、そりゃより小さいものを展開していって今展開しているものの中から一番小さいものを選んでいくから、
     * 　それで足りるじゃん、になれるか？
     * 　　確かに、結局k+1回分の答えが欲しいとき、今既に最小の組み合わせから見ていて、じゃあその組み合わせの次に最小になる組み合わせを出して、
     * 　他の候補と合わせてその中から最小の組み合わせを求めたら、k+1回目も最小の組み合わせになるよね、か。
     * ・同じレコードを何回も使っている。中身を変えられないのだから、1回定義して使いまわせばよさそう。
     */
    
    
    /*
     * 累計18m15s TLE
     * 前に最小を入れているのに前からpop()していたためWA。
     * でもTLE。-10^9 <= nums1[i], nums2[i] <= 10^9 を最初に確認した後、
     * できそうな実装をそのまま始めてしまった。
     */
    class Solution_TLE {
        record Num1Num2(int num1, int num2) {}

        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            PriorityQueue<Num1Num2> numPairs = new PriorityQueue<>((a, b) -> (b.num1 + b.num2) - (a.num1 + a.num2));
            for (int num1 : nums1) {
                for (int num2 : nums2) {
                    numPairs.offer(new Num1Num2(num1, num2));
                    while (numPairs.size() > k) {
                        numPairs.poll();
                    }
                }
            }
            List<List<Integer>> kSmallestNums = new ArrayList<>();
            for (Num1Num2 nums : numPairs) {
                List<Integer> newNums = new ArrayList<>();
                newNums.add(nums.num1());
                newNums.add(nums.num2());
                kSmallestNums.add(newNums);
            }
            return kSmallestNums;
        }
    }
    
    // 13m45s WA
    class Solution_WA {
        record Num1Num2(int num1, int num2) {}

        public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
            PriorityQueue<Num1Num2> numPairs = new PriorityQueue<>((a, b) -> (a.num1 + a.num2) - (b.num1 + b.num2));
            for (int num1 : nums1) {
                for (int num2 : nums2) {
                    numPairs.offer(new Num1Num2(num1, num2));
                    while (numPairs.size() > k) {
                        numPairs.poll();
                    }
                }
            }
            List<List<Integer>> kSmallestNums = new ArrayList<>();
            for (Num1Num2 nums : numPairs) {
                List<Integer> newNums = new ArrayList<>();
                newNums.add(nums.num1());
                newNums.add(nums.num2());
                kSmallestNums.add(newNums);
            }
            return kSmallestNums;
        }
    }
}
