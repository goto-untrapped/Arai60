/*
 * binary heapは、完全二分木になるように要素の位置を調整する
 * 最大もしくは最小が根に来るように調整する
 * 挿入は一番最後に追加して根に向かって位置を調整する
 * 削除は削除する位置に最後の要素を持ってきて、葉に向かって位置を調整する
 * おそらく、MinHeap/MaxHeapと比べて、binary heapが汎用的なイメージ。
 * binary heapは挿入、削除する位置を指定でき、
 * MinHeap/MaxHeapは常に最小/最大になるように自動調整するイメージ。
 * JavaのPriorityQueueはbinary heapのイメージ。
 * 
 * 一旦MinHeapを実装してみる
 * 1. heap を作成 pop と push ができる
 * 2. 配列の大きさを変えられる
 * 3. heapify ができる
 */

/*
 * 5hかかった。
 * 下記を参考に書いた。
 * https://github.com/fhiyo/leetcode/pull/10/files
 * https://selcote.medium.com/how-to-implement-a-binary-heap-in-java-9bfcf3b9a947
 * https://www.edureka.co/blog/binary-heap-in-java/
 * PriorityQueue.Class: grow()
 * https://github.com/python/cpython/blob/31a28cbae0989f57ad01b428c007dade24d9593a/Lib/heapq.py#L170
 * 
 * はじめ、Javaのソースを見ていたが、siftUpやsiftDownが、見慣れない総称型と変数名もあり、アルゴリズムが何をしているのか分類できず、心が折れた。
 * そのため、一旦fhiyoさんとpythonのソースを確認して、URL2つをまねして実装して動かしてみた。
 * その後、IntMinHeapを作りながらJavaのソースをもう一度見たところ、何となく何をしているか前より分かることができた。
 */
class IntMinHeap {
    private int[] heap;
    private int lastHasValueIndex;
    
//    public IntMinHeap(int size) {
//        this.heap = new int[size];
//        lastHasValueIndex = -1;
//    }
    
    public IntMinHeap(int[] nums) {
        lastHasValueIndex = nums.length - 1;
        heapify(nums);
    }
    
    private void heapify(int[] nums) {
        heap = nums;
        /*
         * 子がいない最後の親ノードから、葉ノードと比較しながら根ノードの最小値を更新して、これを根まで続ければ、
         * 最小のノードから順に根に来る。子がいない最後の親ノードは、計算すると 要素の数n // 2 - 1 インデックス目。 
         * 親ノードは最低1つの子ノードを持ち(2 * i + 1)、2 * i + 1 < n を満たすと、2つ子ノードを持っている場合も同じ親ノードを指せる
         * 2 * i + 2 < n で計算しても同じ結果になる
         */
        for (int index = nums.length / 2 - 1; index >= 0; index--) {
            shiftToLeaf(index);
        }
    }
    
    public void push(int value) {
        lastHasValueIndex++;
        heap[lastHasValueIndex] = value;
        shiftToRoot(lastHasValueIndex);
    }
    
    public int pop() {
        int poppedValue = heap[0];
        heap[0] = heap[lastHasValueIndex];
        heap[lastHasValueIndex] = 0;
        lastHasValueIndex--;
        shiftToLeaf(0);
        return poppedValue;
    }
    
    private void shiftToLeaf(int index) {
        int rootValue = heap[index];
        while (index < lastHasValueIndex) {
            int minChildIndex = getMinChildIndex(index);
            if (minChildIndex < 0) {
                break;
            }            
            if (heap[minChildIndex] >= rootValue) {
                break;
            }
            heap[index] = heap[minChildIndex];
            index = minChildIndex;           
        }
        heap[index] = rootValue;
    }
    
    private int getMinChildIndex(int index) {
        int leftChildIndex = 2 * index + 1;
        if (leftChildIndex > lastHasValueIndex) {
            return -1;
        }
        if (leftChildIndex == lastHasValueIndex) {
            return leftChildIndex;
        }
        int rightChildIndex = 2 * index + 2;
        if (heap[rightChildIndex] < heap[leftChildIndex]) {
            return rightChildIndex;
        }
        return leftChildIndex;
    }
    
    private void shiftToRoot(int index) {
        int newValue = heap[index];
        int parentIndex = getParentIndex(index);
        while (index > 0 && heap[parentIndex] > newValue) {
            heap[index] = heap[parentIndex]; 
            index = parentIndex;      
            parentIndex = getParentIndex(index);
        }
        heap[index] = newValue;
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }
    
    public int getSize() {
        return lastHasValueIndex + 1;
    }
    
    public int peek() {
        return heap[0];
    }
    
    public boolean isFull() {
        return lastHasValueIndex == heap.length - 1;
    }
    
    public void grow() {
        int[] updatedHeap = Arrays.copyOf(heap, heap.length + 1);
        heap = updatedHeap;
    }
    
}

// PriorityQueueを使う
class KthLargest2_1 {
    private int kth;
    private PriorityQueue<Integer> kNums;

    public KthLargest2_1(int k, int[] nums) {
        kth = k;
        kNums = new PriorityQueue<>();
        for (int num : nums) {
            // addはofferを呼び出す
            kNums.offer(num); 
        }

        while (kNums.size() > kth) {
            kNums.poll();
        }
    }
    
    public int add(int val) {
        kNums.offer(val);
        while (kNums.size() > kth) {
            kNums.poll();
        }
        return kNums.peek();
    }
}

// 実装したPriorityQueueを使う
class KthLargest2_2 {
    private int kth;
    private IntMinHeap heap;

    public KthLargest2_2(int k, int[] nums) {
        kth = k;
        heap = new IntMinHeap(nums);
//        heap = new IntMinHeap(nums.length);
//        for (int num : nums) {
//            heap.push(num);
//        }

        while (heap.getSize() > kth) {
            heap.pop();
        }
    }
    
    public int add(int val) {
        if (heap.isFull()) {
            heap.grow();
        }
        heap.push(val);
        while (heap.getSize() > kth) {
            heap.pop();
        }
        return heap.peek();
    }
}
