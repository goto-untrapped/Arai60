public class KthLargestElementInAStreamStep4 {
    /*
     * 2-1, 2-2: KthLargestのadd()について、Step3のoffer();pop();との手順違い、どれがよさそうかの比較
     * 2-3: Step2で実装したProrityQueueを可変リストで書き直してみる
     * 2-4: quick selectを使った処理を書いてみる
     */
}

// 2m30s
class KthLargestStep4 {
    int kth;
    PriorityQueue<Integer> kLargerNums;

    public KthLargestStep4(int k, int[] nums) {
        kth = k;
        kLargerNums = new PriorityQueue<>();

        for (int num : nums) {
            kLargerNums.offer(num);
            if (kLargerNums.size() > kth) {
                kLargerNums.poll();
            }
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

// Step3では、queueに全部追加した後、k個取り出して減らす
// queueに追加しながら、常にk個保つようにも取り出せるよね
class KthLargest4_1 {
    private PriorityQueue<Integer> kLargerNums;
    private int kth;

    public KthLargest4_1(int k, int[] nums) {
        kth = k;
        kLargerNums = new PriorityQueue<>();

        for (int num : nums) {
            kLargerNums.offer(num);
            if (kLargerNums.size() > kth) {
                kLargerNums.poll();
            }
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

// k=1, nums.size()=100の時、100回追加して取り出さなくてもいいよね
class KthLargest4_2 {
    private PriorityQueue<Integer> kLargerNums;
    private int kth;

    public KthLargest4_2(int k, int[] nums) {
        kth = k;
        kLargerNums = new PriorityQueue<>();

        for (int num : nums) {
            if (kLargerNums.size() < k) {
                kLargerNums.offer(num);
                continue;
            }
            if (num < kLargerNums.peek()) {
                continue;
            }
            kLargerNums.offer(num);
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

/*
 * 可変リストと固定リストのパフォーマンスについて(同時刻3回平均)
 * 可変リスト：17ms / 48.9MB
 * 固定リスト：12ms / 47.0MB
 * 問題文より、1万回くらいの入れ替えまでだと大きな差はないと思った。
 * 可変の方が変更に対応している分、プロパティや条件分岐が多く、つもると時間がかかるのだろう
 * なのでJDKも固定リストで書かれているのかなと思った。でももし実務でPriorityQueueのようなクラスを書くのであれば、
 * 汎用的な使い方をしたい時だと思うので、固定リストで書けるといいと思った
 */
class KthLargest2_3 {
    private DynamicIntMinHeap kLargerNums;
    private int kth;

    public KthLargest2_3(int k, int[] nums) {
        kth = k;
        kLargerNums = new DynamicIntMinHeap(nums);
        while (kLargerNums.getSize() > kth) {
            kLargerNums.pop();
        }
    }

    public int add(int val) {
        kLargerNums.push(val);
        if (kLargerNums.getSize() > kth) {
            kLargerNums.pop();
        }
        return kLargerNums.peek();
    }
}

class DynamicIntMinHeap {
    private List<Integer> heap;

    public DynamicIntMinHeap(int[] nums) {
        heapify(nums);
    }

    public void push(int value) {
        heap.add(value);
        shiftToRoot(getLastIndex());
    }

    public int pop() {
        int poppedValue = heap.get(0);
        heap.set(0, heap.get(getLastIndex()));
        heap.remove(getLastIndex());
        shiftToLeaf(0);
        return poppedValue;
    }

    public int getSize() {
        return heap.size();
    }

    private void heapify(int[] nums) {
        /*
         * どちらのやり方でも int[]->List<Integer> できる
         * どっちみちややこしいけど、より定型句の長さが短いStreamを採用
         * Integer[] boxedNums = Arrays.stream(nums).boxed().toArray(Integer[]::new);
         * heap = Arrays.asList(boxedNums);
         * 
         * List<Integer> numList = Arrays.stream(nums).boxed().toList(); と書いたときのtoList()は
         * Collections.unmodifiableList(new xxx); と実装されており、変更不可で生成されてしまう
         */
        heap = IntStream.of(nums)
                .boxed()
                .collect(Collectors.toList());
        for (int index = nums.length / 2 - 1; index >= 0; index--) {
            shiftToLeaf(index);
        }
    }

    private int getLastIndex() {
        return heap.size() - 1;
    }

    private void shiftToLeaf(int index) {
        int rootValue = heap.get(index);
        while (index < getLastIndex()) {
            int minChildIndex = getMinChildIndex(index);
            if (minChildIndex < 0) {
                break;
            }
            if (heap.get(minChildIndex) >= rootValue) {
                break;
            }
            heap.set(index, heap.get(minChildIndex));
            index = minChildIndex;
        }
        heap.set(index, rootValue);
    }

    private int getMinChildIndex(int index) {
        int leftChildIndex = 2 * index + 1;
        if (leftChildIndex > getLastIndex()) {
            return -1;
        }
        if (leftChildIndex == getLastIndex()) {
            return leftChildIndex;
        }
        int rightChildIndex = 2 * index + 2;
        if (heap.get(rightChildIndex) < heap.get(leftChildIndex)) {
            return rightChildIndex;
        }
        return leftChildIndex;
    }

    private void shiftToRoot(int index) {
        int newValue = heap.get(index);
        int parentIndex = getParentIndex(index);
        while (index > 0 && heap.get(parentIndex) > newValue) {
            heap.set(index, heap.get(parentIndex));
            index = parentIndex;
            parentIndex = getParentIndex(index);
        }
        heap.set(index, newValue);
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    public int peek() {
        return heap.get(0);
    }

    /*
     * 2.5h
     * https://discord.com/channels/1084280443945353267/1183683738635346001/1185972070165782688
     * クイックソートについて知っていなくてはいけないことは を整理
        最悪・平均計算量 
          ⇒ O(n^2):常にpivotが最小だと、全部見ても1つずつしか並び替えて行けないため / O(nlogn):logn回pivotを選ぶ、全体的には各要素は1回だけ並び替えているため
        末尾再帰最適化
          ⇒ 関数の末尾のreturnで再帰呼び出しのみ行うことで、スタックを積まずに処理ができる
             Javaだと末尾再帰最適化がサポートされていないため、実装が必要（デバッグしたら、末尾再帰最適化で書いてもスタックが積まれる）
             https://qiita.com/tosh_m/items/2be312ba08c8b7ecb3b6
        ピボット選択
          ⇒ min/maxを選ばないようにしたい。配列からランダムで選んだり、中央値を取ったりする。中央値を取る方がよいらしい
            https://ja.stackoverflow.com/questions/68704/%E3%82%AF%E3%82%A4%E3%83%83%E3%82%AF%E3%82%BD%E3%83%BC%E3%83%88%E3%81%AEpivot%E3%81%AE%E6%B1%BA%E3%82%81%E6%96%B9%E3%81%AB%E3%81%A4%E3%81%84%E3%81%A6
        マージソートとのプロコン
          ⇒ マージソートは半分に分割していき、最小要素になったら並び替えを行い、並び替えた2配列を並び替えながら合成していく
             O(nlogn)/O(nlogn): 最小要素まで分割する時の深さはlogn、その深さでの合成はn回するため
             http://sap.ist.i.kyoto-u.ac.jp/members/yoshii/lectures/algo_data/2014/20150127-algo_data.pdf 
             クイックソートの方が最速が出るらしい
             
     * PriorityQueueと同じ計算量O(n) (min/maxをピボットにするとO(n^2))
     * 同じように全部並び替える必要がなく最小を求められる
     * 書きやすい方を使えばいいと思ったが、オーソドックスなのはデータ構造である上にJavaクラスにもなっている
     * PriorityQueueだと思うので、こちらを優先して使いたいと思った。
     * 
     * クイックソートに似ている
     * 効率よく最小、最大を求められる
     * 範囲特定のための分割方法はクイックソートと同じで、
     * k番目に大きい値を、あるインデックスを境に2つに分けた時の、インデックスの位置が前からk番目かどうかで見つけられる
     * 中央値を選ぶために、その範囲からランダムでpivotを選ぶようにする
     * 
     * QuickSelect
     * https://discord.com/channels/1084280443945353267/1183683738635346001/1185972070165782688
     * https://medium.com/nerd-for-tech/quick-select-algorithm-17ac146b6218 を参考
     */
    class KthLargest2_4 {
        private int kth;
        private ArrayList<Integer> nums = new ArrayList<>();

        public KthLargest2_4(int k, int[] nums) {
            this.kth = k;
            for (int num : nums) {
                this.nums.add(num);
            }
        }

        public int add(int val) {
            nums.add(val);
            return quickSelectKth(0, nums.size() - 1);
        }

        private Integer quickSelectKth(int start, int end) {
            int kInIndex = kth - 1;
            int minimumPositionInRange = partitionFromLarger(start, end);

            if (minimumPositionInRange == kInIndex) {
                return nums.get(minimumPositionInRange);
            } else if (minimumPositionInRange < kInIndex) {
                return quickSelectKth(minimumPositionInRange + 1, end);
            } else {
                return quickSelectKth(start, minimumPositionInRange - 1);
            }
        }

        /*
         * 下記と書くとTLE。Math.random()が遅いからだと判断
         * int pivotIndex = (int) Math.random() * (end - start) + start;
         *   testcase9 (n=20000)
         *   ["KthLargest","add","add","add","add","add","add","add",...]
         *   [[9999,[5917,-7390,8688,8851,4070,1999,143,...]
         */
        private int partitionFromLarger(int start, int end) {
            int pivotIndex = start + (end - start) / 2;
            int pivot = nums.get(pivotIndex);
            Collections.swap(nums, pivotIndex, end);

            int largerIndex = start;
            for (int index = start; index < end; index++) {
                if (nums.get(index) < pivot) {
                    continue;
                }
                Collections.swap(nums, index, largerIndex);
                largerIndex++;
            }

            Collections.swap(nums, largerIndex, end);
            return largerIndex;
        }
    }
}
