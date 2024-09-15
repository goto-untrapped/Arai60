public class TopKFrequentElementsStep1 {
    
    /*
     * Mapのソート方法は調べた。
     * ソート順が反対になったりして、調べながら書いて、全体で25min
     * 時間計算量:O(nlogn)
     * 　https://stackoverflow.com/questions/31301471/big-o-complexity-of-java-util-stream-streamt-sorted
     * 空間計算量:O(n)
     */
    class Solution {
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> numToCount = new HashMap<>();
            for (int num : nums) {
                int count = numToCount.getOrDefault(num, 0);
                numToCount.put(num, count + 1);
            }

            Map<Integer, Integer> sortedNumToCount = numToCount.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                    Map.Entry::getKey, 
                    Map.Entry::getValue, 
                    (oldValue, newValue) -> oldValue, LinkedHashMap::new));
            int[] mostFrequentElements = new int[k];
            int frequentCount = 0;
            int i = 0;
            for (int num : sortedNumToCount.keySet()) {
                mostFrequentElements[i] = num;
                i++;
                frequentCount++;
                if (frequentCount == k) {
                    break;
                }
            }
            return mostFrequentElements;
        }
    }
    /*
     * ・ごり押し感がすごいけど、一旦動く形にはなった。
     * ・もっときれいに書けるようにがんばる。
     * ・公式解答を見て、昔にHeapをがんばって実装したことを思い出した。（全然道具箱に入ってなかった。）
     */
    
    /*
     * PriorityQueueかMapを使えばできそうだけど、
     * どちらも実装途中で書き方が分からない箇所があった。
     * 下記はMapで実装した時のイメージ
     * 13m40s
     */
    class SolutionTry1 {
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> numToCount = new HashMap<>();
            for (int num : nums) {
                int count = numToCount.getOrDefault(num, 0);
                numToCount.put(num, count + 1);
            }

//            Map.sort(numToCount.values()); コンパイルエラーになるため一旦コメントアウト
            int[] mostFrequentElements = new int[k];
            int frequentCount = 0;
            int i = 0;
            for (int num : numToCount.keySet()) {
                mostFrequentElements[i] = num;
                i++;
                frequentCount++;
                if (frequentCount == k) {
                    break;
                }
            }
            return mostFrequentElements;
        }
    }
    /*
     * ・PriorityQueueはよく考えたらQueueなので、どうやってnumとcountを渡すのかが整理できなかった。
     * 　・公式解答や他の解答を見たところ、Queueはただのデータの入れ方や出し方で、何を入れるかとは関係なかった。
     * 　　・というか、JavaにとってのHeapだった。
     * 　　https://docs.oracle.com/javase/jp/17/docs/api/java.base/java/util/PriorityQueue.html
     * 　　　・最大順に、最小順に並べましょうというルールがあって、そのルールを搭載する（そうなるように処理してルールを実現する）ために
     * 　　　使っている入れ物がQueueということか。（QueueにはQueueのルールがあるけど、そのデータの出し入れのルールを最大最小を満たすようにする）
     */
}
