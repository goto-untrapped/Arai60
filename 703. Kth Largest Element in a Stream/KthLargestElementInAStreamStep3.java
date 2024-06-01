public class KthLargestElementInAStreamStep3 {
    /*
     * 5min
     * 
     * はじめ、10分かけて下記のように書いたが、空の配列の初期化時にRuntimeError
     * ・JavaのPriorityQueueにサイズを入れて初期化していたため、三項演算子で処理を追加してAC
     * =>答えを確認し、サイズ空欄で初期化できることを知る
     * 
     * JDKを確認し、Arrays.asList(nums)で初期化できるか試すが、List<int[]>はRuntimeError
     * =>List<Integer[]>にすればできそうだけど、型変換して戻すより1つずつqueueに追加すればいいや
     * 何となくoffer()の方をよく見てるけど、本当はどちらの方が速そうか、確認してみる
     * offer(): n回行うため、O(n)
     * 型変換して初期化:
     *   initElementsFromCollection(): 長さ1あるいはcomparator使わなければO(1)、最悪O(n)
     *   heapify(): O(n/2) = O(n)
     * offer()の方が簡潔かつ速いと思った
     * 
     */
    class KthLargest {
        private PriorityQueue<Integer> kLargerNums;
        private int kth;

        public KthLargest(int k, int[] nums) {
            kth = k;
            kLargerNums = new PriorityQueue<>();

            for (int num : nums) {
                kLargerNums.offer(num);
            }

            while (kLargerNums.size() > kth) {
                kLargerNums.poll();
            }
        }
        
        public int add(int val) {
            kLargerNums.offer(val);
            if (kLargerNums.size() > kth) {
                kLargerNums.poll();
            }
            return kLargerNums.peek();
        }
    }
}
