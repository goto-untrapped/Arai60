public class PaintFenceStep2 {
    // 再帰
    class Solution2_1 {
        public int numWays(int n, int k) {
            if (n == 1) {
                return k;
            }
            if (n == 2) {
                return k * k;
            }
            return (k - 1) * (numWays(n - 1, k) + numWays(n - 2, k));
        }
    }
    /*
     * 思ったこと
     * ・組み合わせ数のパターンの分け方がピンと来ず何回も解説を読んだため、何となくで書けてしまう。
     */
    
    
    // 再帰+メモ
    class Solution2_2 {
        public int numWays(int n, int k) {
            return numWaysHelper(n, k, new int[n + 1]);
        }

        private int numWaysHelper(int n, int k, int[] numOfWays) {
            if (n == 1) {
                return k;
            }
            if (n == 2) {
                return k * k;
            }
            if (numOfWays[n] > 0) {
                return numOfWays[n];
            }
            numOfWays[n] = (k - 1) * (numWaysHelper(n - 1, k, numOfWays) + numWaysHelper(n - 2, k, numOfWays));
            return numOfWays[n];
        }
    }
    /*
     * 思ったこと
     * ・組み合わせ数のパターンの分け方がピンと来ず何回も解説を読んだため、何となくで書けてしまう。
     */
    
    
    // 0-indexed
    public int numWays2_3(int n, int k) {
        if (n == 1) {
            return k;
        }
        int[] numOfWays = new int[n];
        numOfWays[0] = k;
        numOfWays[1] = k * k;
        for (int i = 2; i < numOfWays.length; i++) {
            numOfWays[i] = (k - 1) * (numOfWays[i - 2] + numOfWays[i - 1]);
        }
        return numOfWays[numOfWays.length - 1];
    }
    /*
     * 思ったこと
     * ・最小でn = 1なので、それだけ例外にすればよかったな。
     */
    
    
    // BottomUpDp（Step1）を空間計算量O(1)で書く
    public int numWays2_4(int n, int k) {
        if (n == 1) {
            return k;
        }
        int prevNumWays = k;
        int currentNumWays = k * k;
        for (int i = 3; i < n + 1; i++) {
            int nextNumWays = (k - 1) * (prevNumWays + currentNumWays);
            prevNumWays = currentNumWays;
            currentNumWays = nextNumWays;
        } 
        return currentNumWays;
    }
    /*
     * 思ったこと
     * ・今の組み合わせの総数は、
     * 前と違う色を塗り分ける時は、1個前までの組み合わせ数×(k-1)通りで、前と同じ色を塗る時は2個前までの組み合わせ数×(k-1)×1通り、の和
     */
    
    
    /*
     * ライブラリのLinkedHashMapを使って LRU cache で書いてみる
     *   https://kazuhira-r.hatenablog.com/entry/20151226/1451134718
     *   コンストラクタのloadFactorは75%が、拡張に使うメモリ容量と要素取得にかかる時間のバランスがいいようだ。
     *     https://stackoverflow.com/questions/10901752/what-is-the-significance-of-load-factor-in-hashmap
     * key が node だと、同じ n,k でも違う参照パスになりそうと思い、レコードを使った。
     */
    class Solution2_5 {
        public int numWays(int n, int k) {
            MyCache<NumFencesAndPosts, Integer> myCache = new MyCache<>(1000);
            LinkedHashMap<NumFencesAndPosts, Integer> cache = myCache.cache;
            cache.put(new NumFencesAndPosts(1, k), k);
            cache.put(new NumFencesAndPosts(2, k), k * k);
            return numWaysHelper(n, k, cache);
        }
    
        private int numWaysHelper(int n, int k, LinkedHashMap<NumFencesAndPosts, Integer> cache) {
            NumFencesAndPosts parameters = new NumFencesAndPosts(n, k);
            if (cache.containsKey(parameters)) {
                return cache.get(parameters);
            }
            cache.put(parameters, (k - 1) * (numWaysHelper(n - 1, k, cache) + numWaysHelper(n - 2, k, cache)));
            return cache.get(parameters);
        }
        
        class MyCache<K, V> extends LinkedHashMap<K, V> {
            LinkedHashMap<NumFencesAndPosts, Integer> cache;
            private int limit;
            
            MyCache(int size) {
                cache = new LinkedHashMap<>(size, 0.75f, true);
                limit = size;
            }
            
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > limit;
            }
        }
        record NumFencesAndPosts(int n, int k) {}
    }
    /*
     * 思ったこと
     * ・再帰関数を呼び出すたびに、同じ引数でも新しくrecordのインスタンスをつくっているのがメモリを無駄に使っていると思うが、値が不変なので使いまわせない。
     * でも、値の一致判定をするための最小コード(equals()やhashCode())を補完してくれるので、分かりやすさによるメリットの方がかなり勝ると思う。
     * ・get()をすると要素の順番が変わるため、イテレートでget()をすると、ConcurrentModificationException例外が飛ぶため、
     * LinkedHashMapをLRU cacheとして使う時は注意が必要。
     *   https://kazuhira-r.hatenablog.com/entry/20151226/1451134718
     *   https://docs.oracle.com/javase/jp/17/docs/api/java.base/java/util/LinkedHashMap.html
     * ・k <= 10^5 のため、maxSize = 1000 という中間ぐらいの値にした。
     */
    
    
    /*
     * LRU cache 自作で書いてみる
     */
    class Solution2_6 {
        public int numWays(int n, int k) {
            MyLRUCache cache = new MyLRUCache(1000);
            cache.put(new NumFencesAndPosts(1, k), k);
            cache.put(new NumFencesAndPosts(2, k), k * k);
            return numWaysHelper(n, k, cache);
        }

        private int numWaysHelper(int n, int k, MyLRUCache cache) {
            NumFencesAndPosts parameters = new NumFencesAndPosts(n, k);
            if (cache.get(parameters) != -1) {
                return cache.get(parameters);
            }
            cache.put(parameters, (k - 1) * (numWaysHelper(n - 1, k, cache) + numWaysHelper(n - 2, k, cache)));
            return cache.get(parameters);
        }
        
        class Node {
            NumFencesAndPosts key;
            final int value;
            Node prev;
            Node next;
            
            Node(NumFencesAndPosts key, int value) {
                this.key = key;
                this.value = value;
            }
            public int getValue() { return value; }
        }
        record NumFencesAndPosts(int n, int k) {}

        class MyLRUCache {
            HashMap<NumFencesAndPosts, Node> cache;
            int limit;
            int size = 0;
            Node sentinel;
            
            MyLRUCache(int size) {
                cache = new HashMap<>();
                this.limit = size;
                sentinel = new Node(null, -1);
                sentinel.next = sentinel;
                sentinel.prev = sentinel;
            }
            
            public int get(NumFencesAndPosts parameters) {
                if (!cache.containsKey(parameters)) {
                    return -1;
                }
                int numWays = cache.get(parameters).getValue();
                remove(parameters);
                put(parameters, numWays);
                return numWays;
            }
            
            public void put(NumFencesAndPosts parameters, int numWays) {
                Node inserting = new Node(parameters, numWays);
                Node head = sentinel.next;
                sentinel.next = inserting;
                inserting.next = head;
                head.prev = inserting;
                inserting.prev = sentinel;
                cache.put(parameters, inserting);
                size++;
                if (limit < size()) {
                    remove(sentinel.prev.key);
                }
            }
            
            private void remove(NumFencesAndPosts parameters) {
                Node node = cache.get(parameters);
                node.prev.next = node.next;
                node.next.prev = node.prev;
                cache.remove(parameters);
                size--;
            }
            
            private int size() {
                return size;
            }
        }
    }
    /*
     * 思ったこと
     * ・hayashiさんのソースを読んでいたので、何も考えずに先頭を最後にアクセスした要素にしていた。
     * ネイティブ実装は先頭最後も末尾最後もできる。
     * https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/LinkedHashMap.java#L336
     * ・下記を参照し、get(), put(), size(), remove()をO(1)になるように書いた。
     * https://github.com/hayashi-ay/leetcode/pull/17/files
     * ・Nodeクラスのvalueは変更しない想定を示す時、finalが分かりやすくていいと思った。
     * https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/HashMap.java#L283
     * またオブジェクトの場合だと、気持ちを伝えるための程度で final を付けて(再代入はできないけど中身は書き換えられるため)、
     * 変更する必要が出てきたときに return { new } のようにしてもらうと変更しないことになると思った。
     * ・keyの一致判定のため、Recordを使ったが、hashCodeを使えるのだなと思った。
     * https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/util/HashMap.java#L282
     */
    
    
    /*
     * 他に思ったこと
     * ・@cache に相当する機能もアノテーションとリフレクションを使えばできそう
     *   https://github.com/matthewmichihara/lrucache
     *     標準ライブラリでもできそう
     */
}
/*
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1205715212276342784
 *   https://github.com/hayashi-ay/leetcode/pull/17/files
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1220426354097651743
 *   https://github.com/shining-ai/leetcode/pull/30
 * https://discord.com/channels/1084280443945353267/1227073733844406343/1242354303725735978
 *   https://github.com/sakupan102/arai60-practice/pull/31
 */
