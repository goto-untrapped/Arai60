public class SubsetsStep2 {
    // backtracking(DFS、再帰、ループで順番に値を入れて、戻ってきたら自分の値を消す)
    class Solution2_1 {
        public List<List<Integer>> subsets(int[] nums) {
            List<List<Integer>> allSubsets = new ArrayList<>();
            return subsetsHelper(nums, allSubsets, new ArrayList<Integer>(), 0);
        }

        private List<List<Integer>> subsetsHelper(int[] nums, List<List<Integer>> allSubsets, ArrayList<Integer> subset, int start) {
            allSubsets.add(new ArrayList<>(subset));
            if (start == nums.length) {
                return allSubsets;
            }
            for (int i = start; i < nums.length; i++) {
                subset.add(nums[i]);
                subsetsHelper(nums, allSubsets, subset, i + 1);
                subset.remove(subset.size() - 1);
            }
            return allSubsets;
        }
    }
    /*
     * 思ったこと
     * ・subsetsHelperのif文はあった方が親切と思ったため書いた。
     */
    
    
    // backtracking(DFS、再帰、自分の値を入れると入れない場合でそれぞれ再帰)
    // https://github.com/nittoco/leetcode/pull/19
    class Solution2_2 {
        public List<List<Integer>> subsets(int[] nums) {
            List<List<Integer>> allSubsets = new ArrayList<>();
            return subsetsHelper(nums, allSubsets, 0);
        }

        private List<List<Integer>> subsetsHelper(int[] nums, List<List<Integer>> allSubsets, int start) {
            if (start == nums.length) {
                List<List<Integer>> initialized = new ArrayList<>();
                initialized.add(new ArrayList<>());
                return initialized;
            }
            List<List<Integer>> notInclude = subsetsHelper(nums, allSubsets, start + 1);
            List<List<Integer>> include = subsetsHelper(nums, allSubsets, start + 1);
            for (List<Integer> subset : include) {
                subset.add(nums[start]);
            }
            List<List<Integer>> both = new ArrayList<>();
            both.addAll(notInclude);
            both.addAll(include);
            return both;
        }
    }
    /*
     * 思ったこと
     * ・List<List<Integer>>が何回も並び、くどくなってしまった。
     * varを使えると思ったけど、new ArrayList<>() 時の左辺は書く必要がありそうだし、
     * 左をしっかり書いて右で省略的な書き方をしてきたので、慣れている方でそのまま書いた。
     */
    
    
    // backtracking(DFS、再帰、自分の値を入れると入れない場合でそれぞれ再帰)
    // https://github.com/hayashi-ay/leetcode/pull/63
    class Solution2_3 {
        private List<List<Integer>> allSubsets;
        private ArrayList<Integer> subset;
        public List<List<Integer>> subsets(int[] nums) {
            allSubsets = new ArrayList<>();
            subset = new ArrayList<>();
            return makeAllSubsets(nums, 0);
        }

        private List<List<Integer>> makeAllSubsets(int[] nums, int index) {
            if (index == nums.length) {
                allSubsets.add(new ArrayList<Integer>(subset));
                return allSubsets;
            }
            makeAllSubsets(nums, index + 1);
            subset.add(nums[index]);
            makeAllSubsets(nums, index + 1);
            subset.remove(subset.size() - 1);
            return allSubsets;
        }
    }
    /*
     * 思ったこと
     * ・引数で持ち回らずにstatic的に使えるようにしてみた。
     * スコープがかなり広いので、定数で使うぐらいの方がいいと思っている。
     * ・何も考えずに書いたら関数名がxxxHelperじゃなかったので、名前がある方が
     * 分かりやすいと思ったのだろう。
     */

    
    // Stack(DFS、ループ)
    class Solution2_4 {
        public List<List<Integer>> subsets(int[] nums) {
            List<List<Integer>> allSubsets = new ArrayList<>();
            Stack<BuilderInfo> subsetsBuilder = new Stack<>();
            subsetsBuilder.push(new BuilderInfo(0, new ArrayList<>()));
            while (!subsetsBuilder.isEmpty()) {
                BuilderInfo processing = subsetsBuilder.pop();
                int index = processing.getIndex();
                ArrayList<Integer> subset = processing.getSubset();
                if (index == nums.length) {
                    allSubsets.add(new ArrayList<>(subset));
                    continue;
                }
                subsetsBuilder.push(new BuilderInfo(index + 1, new ArrayList<>(subset)));
                subset.add(nums[index]);
                subsetsBuilder.push(new BuilderInfo(index + 1, new ArrayList<>(subset)));
            }
            return allSubsets;
        }
    }
    class BuilderInfo {
        private int index;
        private ArrayList<Integer> subset;

        public BuilderInfo(int index, ArrayList<Integer> subset) {
            this.index = index;
            this.subset = subset;
        }

        int getIndex() {
            return index;
        }
        ArrayList<Integer> getSubset() {
            return subset;
        }
    }
    /*
     * 思ったこと
     * ・Stackにintとlistのペアを入れたかったけど、Pairクラスの<key, value>という意味合いでもないと思い、
     * 1ペアあたりの変数が増えてもいいように、クラスを用意した。
     * ・変数名はいいのが思いつかず、役割で一旦付けた。
     */
    
    
    // 追加済の組み合わせを使って１個追加した組み合わせを追加
    public List<List<Integer>> subsets2_5(int[] nums) {
        List<List<Integer>> allSubsets = new ArrayList<>();
        allSubsets.add(new ArrayList<>());
        for (int num : nums) {
            List<List<Integer>> partAllSubsets = new ArrayList<>();
            for (List<Integer> subset : allSubsets) {
                ArrayList<Integer> newSubset = new ArrayList<>(subset);
                newSubset.add(num);
                partAllSubsets.add(newSubset);
            }
            allSubsets.addAll(partAllSubsets);
        }
        return allSubsets;
    }
    /*
     * 思ったこと
     * ・はじめ、直接allSubsets.add()して、ConcurrentModificationExceptionしたため、
     * partAllSubsetsを追加。ループ回してるものを操作して大丈夫か？を確認する。
     */
    
    
    // bit演算
    public List<List<Integer>> subsets2_6(int[] nums) {
        List<List<Integer>> allSubsets = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, nums.length); i++) {
            ArrayList<Integer> subset = new ArrayList<>();
            int num = i;
            int index = 0;
            while (num > 0) {
                if ((num & 1) == 1) {
                    subset.add(nums[index]);
                }
                num >>= 1;
                index++;
            }
            allSubsets.add(subset);
        }
        return allSubsets;
    }
    /*
     * 思ったこと
     * ・はじめ、iをそのままwhileに渡して、0にする⇒ループで1にする を繰り返してTLEした。
     * 値が渡されているので、新しい変数を定義するなりnewするなりして対処する。
     */
    
    
    // itertools.combinations みたいな関数を探したところ、Guavaにはありそう
    // ライブラリ導入してまで使うほどの頻度が想定できなかったため、スキップした
}

/*
 * 参考箇所メモ
 * https://github.com/hayashi-ay/leetcode/pull/63
 * ・backtrackingで再帰、それぞれの人が値を渡すか渡さないか
 * ・数字とビットの位置を対応付けて、ビットが立っていればその数字が使われるようにして、全ビットの組み合わせから数字の組み合わせが出せる
 * ・既存のsubsetsに、出てきたsubsetたちすべてに注目している値を追加した配列、を追加すればいい
 * https://github.com/shining-ai/leetcode/pull/51
 * ・渡す側でforループして個数を設定すれば、Step1でbacktrackingを使おうとしてつまずいた 1 -> 1,2 -> 1を入れ直してしまう、を防げる
 * ・いや、個数設定しなくてもbacktrackingでできるのか（level_4, level_5）
 * https://github.com/SuperHotDogCat/coding-interview/pull/18
 * ・Javaも配列コピーして追加できるな
 * ・Javaはitertools.combinations みたいなのあったっけ
 * https://github.com/nittoco/leetcode/pull/19
 * ・うまく頭の中で回せないのでがんばる
 * https://discord.com/channels/1084280443945353267/1226508154833993788/1250316040257404929
 * ・backtrackingの書き方色々例
 */
