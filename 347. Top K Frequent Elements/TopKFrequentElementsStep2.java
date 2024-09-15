public class TopKFrequentElementsStep2 {
    /*
     * Step1を改善
     * https://github.com/wf9a5m75/leetcode3/pull/3/files
     */
    class Solution2_1 {
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> numToFrequency = new HashMap<>();
            for (int num : nums) {
                int lastFrequency = numToFrequency.getOrDefault(num, 0);
                numToFrequency.put(num, lastFrequency + 1);
            }
            
            List<Integer> ascFrequentNums = new ArrayList<>(numToFrequency.keySet());
            ascFrequentNums.sort((a, b) -> numToFrequency.get(b) - numToFrequency.get(a));

            int[] topKFrequentNums = new int[k];
            for (int i = 0; i < k; i++) {
                topKFrequentNums[i] = ascFrequentNums.get(i);
            }
            return topKFrequentNums;
        }
    }
    /*
     * ・Mapごとソートする必要はなくて、もう出現回数は分かっていて多い順に数字のみ知りたいので、
     * 出現回数で数字を並び替えて、数字のみを取り出せばいい。
     * ・おそらくHeapが使えるかの問題だけど、他の解決策としてMapが候補に挙がりやすいと思うので、上手に扱えるようになっておきたい。
     * ・ascFrequentNumsはまだソートしていないのにその名前を付けることが気になった。
     * 　・Collections.sort()もList.sort()も、元の変数自体をソートする（変更する）関数。
     */
    
    // Solution2_1を改善
    class Solution2_2 {
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> numToFrequency = new HashMap<>();
            for (int num : nums) {
                int lastFrequency = numToFrequency.getOrDefault(num, 0);
                numToFrequency.put(num, lastFrequency + 1);
            }

            List<Integer> ascFrequentNums = numToFrequency.keySet().stream()
                .sorted((a, b) -> numToFrequency.get(b) - numToFrequency.get(a))
                .collect(Collectors.toList());

            int[] topKFrequentNums = new int[k];
            Iterator<Integer> iterator = ascFrequentNums.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                topKFrequentNums[i] = iterator.next();
                i++;
                if (i == k) {
                    break;
                }
            }
            return topKFrequentNums;
        }
    }
    /*
     * ・ascFrequentNumsを直接つくる。今の目標は並び替えたリストを作ることなので、途中のリストは変数にしなくてもいい。
     * 　一気に処理しているが、別処理を挟む場合、また変数名が変わると思うので、これでよさそう。
     * 　　そういえば他の方のソースを読んでいると一気にやっていることが多いので、自分の業務で細かい書き方に慣れているだけかもしれない。
     * 　　　（よくPythonを読むが、言語的にJavaが細かくてPythonがシンプルな書き方ができるのもあるかも）
     * 　でも言語によってどこまで命名するか異なると思うので、あんまりこだわりすぎない。
     * ・最後のTop頻度の取り出しでforを使うと、kより小さい要素の場合にエラーになる。
     * https://github.com/ryoooooory/LeetCode/pull/16/files#r1671605983
     * 　・Step1をよく見たら、地味に最後のループでkより小さくてもエラーにならなかった。（想定できるとよい）
     */
    
    
    class Solution2_3 {
        record NumAndCount(int num, int count) {}

        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> numToCount = new HashMap<>();
            for (int num : nums) {
                int lastCount = numToCount.getOrDefault(num, 0);
                numToCount.put(num, lastCount + 1);
            }

            Queue<NumAndCount> numAndCounts = new PriorityQueue<>((a, b) -> a.count() - b.count());
            for (int num : numToCount.keySet()) {
                if (numAndCounts.size() == k && numToCount.get(num) < numAndCounts.peek().count()) {
                    continue;
                }

                numAndCounts.offer(new NumAndCount(num, numToCount.get(num)));
                if (numAndCounts.size() > k) {
                    numAndCounts.poll();
                }
            }

            int[] topKFrequentNums = new int[numAndCounts.size()];
            int i = 0;
            for (NumAndCount numAndCount : numAndCounts) {
                topKFrequentNums[i] = numAndCount.num();
                i++;
            }
            return topKFrequentNums;
        }
    }
    /*
     * ・PriorityQueueは、このルールで値入れましょうとできるもの
     * 　https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/PriorityQueue.java#L635。
     * 　・なので、入れる時に頻度の順位がkのラインに届かなかったらもういらないよねで捨てられる。
     */
    
    // Solution2_3を変更
    class Solution2_4 {
        record NumAndCount(int num, int count) {}

        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> numToCount = new HashMap<>();
            for (int num : nums) {
                int lastCount = numToCount.getOrDefault(num, 0);
                numToCount.put(num, lastCount + 1);
            }

            PriorityQueue<NumAndCount> numAndCounts = new PriorityQueue<>((a, b) -> a.count() - b.count());
            for (int num : numToCount.keySet()) {
                if (numAndCounts.size() == k && numToCount.get(num) < numAndCounts.peek().count()) {
                    continue;
                }

                numAndCounts.offer(new NumAndCount(num, numToCount.get(num)));
                while (numAndCounts.size() > k) {
                    numAndCounts.poll();
                }
            }

            int[] topKFrequentNums = new int[numAndCounts.size()];
            for (int i = 0; i < topKFrequentNums.length; i++) {
                topKFrequentNums[i] = numAndCounts.poll().num();
            }
            return topKFrequentNums;
        }
    }
    /*
     * ・whileの方がパッと見の時に1個多くまでしか入らないのかな、と気にすることがなさそう。
     * 細かく読むときに分かればいいくらいかなと思った。
     * ・numAndCountsは取り出しながら入れられる。
     * https://github.com/seal-azarashi/leetcode/pull/9/files
     */
    
    
    // バケットソート
    // https://github.com/Ryotaro25/leetcode_first60/pull/10/files
    class Solution2_5 {
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> numToCount = new HashMap<>();
            int maxCount = 0;
            for (int num : nums) {
                int lastCount = numToCount.getOrDefault(num, 0);
                numToCount.put(num, lastCount + 1);
                maxCount = Math.max(maxCount, numToCount.get(num));
            }

            List<Integer>[] numsEachCount = new ArrayList[maxCount + 1];
            for (var numAndCount : numToCount.entrySet()) {
                int num = numAndCount.getKey();
                int count = numAndCount.getValue();
                if (numsEachCount[count] == null) {
                    numsEachCount[count] = new ArrayList<>();
                }
                numsEachCount[count].add(num);
            }

            int[] topKFrequentNums = new int[k];
            int i = 0;
            for (int count = numsEachCount.length - 1; count > 0; count--) {
                if (numsEachCount[count] == null) {
                    continue;
                }
                List<Integer> currentNums = numsEachCount[count];
                while (currentNums.size() > 0) {
                    topKFrequentNums[i] = currentNums.remove(currentNums.size() - 1);
                    i++;
                    if (i == k) {
                        return topKFrequentNums;
                    }
                }
            }
            return new int[0]; // never reached
        }
    }
    /*
     * ・要素の入れ替えが他の解法に比べて多く発生し、何回かインデックスエラーした。
     * ・entrySetしなくても足りたな。
     * ・whileでlistを消していくような副作用がある方法ではなくて、ふつうにループした方がよかったな。
     * ・入力が条件を満たさない場合に最後の行に到達してしまうので、その場合に何を返すのか合意できていればよさそう。
     * https://github.com/seal-azarashi/leetcode/pull/9/files#r1667702265
     * 一旦はシステムを止めるほどではないけどケースによっては実行時に異変に気付いて直せる程度で置くといいのかなと思ったけど、どうなんだろう。
     */
    
}
/*
Mapをソートして、出現回数が大きいものをk個取り出す。
　これが書きたかったものでは？
　https://github.com/wf9a5m75/leetcode3/pull/3/files
ProiorityQueueでk超えたら入れない　小さいものが前、queueは先頭から取り出す
　https://github.com/seal-azarashi/leetcode/pull/9/files
　https://github.com/kazukiii/leetcode/pull/10/files
Mapの書き方、Javaの書き方が参考になる
　https://github.com/ryoooooory/LeetCode/pull/16/files
TreeMapはキーの順番を保持している
　https://github.com/ryoooooory/LeetCode/pull/16/files
　https://docs.oracle.com/javase/jp/17/docs/api/java.base/java/util/TreeMap.html

https://discord.com/channels/1084280443945353267/1201211204547383386/1205149648139063326
　https://github.com/shining-ai/leetcode/pull/9/files
https://github.com/sakupan102/arai60-practice/pull/10/files
 */
