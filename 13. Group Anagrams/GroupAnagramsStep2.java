public class GroupAnagramsStep2 {

    // 他の回答を確認

    // やり方がStep1とほぼ同じだったもの
    public List<List<String>> groupAnagrams2_1(String[] strs) {
        // この行を書いていなかった。パフォーマンス上がると思うので、書く。
        // また、ArrayListに入る型を書かないのは見慣れていないため、安心するためにも<>は書こうと思う。
        if (strs.length == 0) return new ArrayList();
        
        Map<String, List> ans = new HashMap<String, List>();
        for (String s : strs) {
            // 名前が分かりにくいと思った
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String key = String.valueOf(ca);
            // ちょっと本文が長いので、{}を付けたい
            if (!ans.containsKey(key)) ans.put(key, new ArrayList());
            ans.get(key).add(s);
        }
        
        /*
         * この書き方ができることを知らなかった。
         * Java SE Docsを見たところ、
         * Mapのvalues()の返り値の型： Collection<V>
         * new ArrayList()が取る引数の型： Colection<? extends E> c
         * おそらく、
         * Collection型はList型を許容していて、
         * String型はE(Object型?)を継承しているから、
         * 直接代入できるのかなと思った。
         */
        return new ArrayList(ans.values());
    }
    
    // 出現回数からキーを作る
    public List<List<String>> groupAnagrams2_2(String[] strs) {
        if (strs.length == 0) return new ArrayList<>();

        Map<String, List<String>> anagrams = new HashMap<>();
        /*
         * countsがループの外にあることが少し気になった。
         * strs全体で使うのかと思ったから。
         * でも、単語の数だけcountsを再定義する方が、パフォーマンスが落ちてよくないのかなと思った。
         */
        int[] counts = new int[26];

        for (String str : strs) {
            Arrays.fill(counts, 0);
            for (char c : str.toCharArray()) {
                counts[c - 'a']++;
            }

            StringBuilder charFrequency = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                // 回答で'#'を使っていたため使った
                charFrequency.append('#');
                charFrequency.append(counts[i]);
            }
               
            // 回答では、変数名がkeyだった。keyの方が分かりやすい。
            String charFrequencyStr = charFrequency.toString();
            if (!anagrams.containsKey(charFrequencyStr)) {
                anagrams.put(charFrequencyStr, new ArrayList<>());
            }
            anagrams.get(charFrequencyStr).add(str);
        }

        return new ArrayList<>(anagrams.values());
    }
    
}
