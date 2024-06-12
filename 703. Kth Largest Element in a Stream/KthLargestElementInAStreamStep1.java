class KthLargest {
    /*
     * 解き直し 30分くらいかかった
     * heapでならしたかったが、書き方が分からなかった
     * ArrayListのsort()やadd()を使ったら問題どころじゃないと思ったが、せめてパフォーマンスを上げられないかと思って二分探索を使った
     */
    private int kth; 
    private ArrayList<Integer> sorted;

    public KthLargest(int k, int[] nums) {
        kth = k;
        sorted = new ArrayList<>();
        for (int num : nums) {
            sorted.add(num);
        }
        Collections.sort(sorted);
    }
    
    public int add(int val) {
        int left = 0;
        int right = sorted.size();

        while (left < right) {
            int middle = left + (right - left) / 2;

            if (sorted.get(middle) < val) {
                left = middle + 1; 
            } else {
                right = middle;
            }
        }
        sorted.add(left, val);
        return sorted.get(sorted.size() - kth);
    }
    
    // その後１時間くらいかけて、
    // heapの動きを再現するためにListNodeを使って連結を変えて対応しようとしたが、クラスの定義をするしソースも50行ぐらい書いてWAだった
}

/**
 * Your KthLargest object will be instantiated and called as such:
 * KthLargest obj = new KthLargest(k, nums);
 * int param_1 = obj.add(val);
 */