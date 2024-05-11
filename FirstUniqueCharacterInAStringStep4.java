public class FirstUniqueCharacterInAStringStep4 {
    /*
     * https://github.com/sakupan102/arai60-practice/pull/16/files
     *   https://discord.com/channels/1084280443945353267/1227073733844406343/1233835078509924452
     *   (defaultdict 類似品を書いてみるのと、dict で書いてみるのですかね。)
     * https://github.com/tshimosake/arai60/pull/5/files
     * ⇒JavaのMapを使った解法。その文字が出現したら回数をカウントして、
     * 元の文字列をループして最初の出現回数１の文字のインデックスを返す。
     * Javaにはdefaultdictはない代わりに、getOrDefault()で初期値を設定しながら格納すればいいのかなと思った。
     *   
     * https://github.com/hayashi-ay/leetcode/pull/28/files
     *   https://discord.com/channels/1084280443945353267/1233603535862628432/1237973103670198292
     *   (その次に、ループを実は1周にできるのでは...ハッシュテーブルの先頭要素を返す感じになるかなと思います。)
     * ⇒1stがアルファベット小文字26個を使った解法。
     * 4thの1つ目は、2回出現した時に出現したインデックスを∞に飛ばして、最小のインデックスか-1を返す。
     * 2つ目は、1回のループで1回または2回出現した文字を判別する。1回しか出現していない文字と出現インデックスをmapに格納して、直に返す。
     */

    // どれもメモリが45MBくらいだった
    // 速さは英語小文字配列のみ速くて(5ms)、他は同じくらいだった(30ms)
    // 4_1が一番安心して書けそう 考えること少なそう
    // 速さが必要な時だけ英語小文字配列を使えばいいと思った
    
    // mapの書き直し 変数名がおかしかった
    public int firstUniqChar4_1(String s) {
        Map<Character, Integer> letterToAppearCount = new HashMap<>();

        for (char c : s.toCharArray()) {
            int currentCount = letterToAppearCount.getOrDefault(c, 0);
            letterToAppearCount.put(c, currentCount + 1);
        }

        for (int index = 0; index < s.length(); index++) {
            char c = s.charAt(index);
            if (letterToAppearCount.get(c) == 1) {
                return index;
            }
        }
        return -1;
    }
    
    // 英語小文字配列を使う
    public int firstUniqChar4_2(String s) {
        int[] counts = new int[26];

        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }

        for (int index = 0; index < s.length(); index++) {
            char c = s.charAt(index);
            if (counts[c - 'a'] == 1) {
                return index;
            }
        }
        return -1;
    }
    
    // 2回出現したら出現インデックスは∞
    // 入力文字長は最大で1e5。intを返すので、intのmaxで揃えればいいかなと思った
    public int firstUniqChar4_3(String s) {
        LinkedHashMap<Character, Integer> letterToFirstAppearIndex = new LinkedHashMap<>();

        for (int index = 0; index < s.length(); index++) {
            char c = s.charAt(index);
            if (letterToFirstAppearIndex.containsKey(c)) {
                letterToFirstAppearIndex.put(c, Integer.MAX_VALUE);
            } else {
                letterToFirstAppearIndex.put(c, index);
            }
        }

        int firstAppearIndex = Collections.min(letterToFirstAppearIndex.values());
        return firstAppearIndex != Integer.MAX_VALUE ? firstAppearIndex : -1;
    }
    
    // 1回ループして値を直接返せるmapを作る
    public int firstUniqChar4_4(String s) {
        LinkedHashMap<Character, Integer> letterToFirstAppearIndex = new LinkedHashMap<>();
        Set<Character> duplicated = new HashSet<>();

        for (int index = 0; index < s.length(); index++) {
            char c = s.charAt(index);
            if (duplicated.contains(c)) {
                letterToFirstAppearIndex.remove(c);
                continue;
            } 
            letterToFirstAppearIndex.put(c, index);
            duplicated.add(c);
        }

        for (int appearIndex : letterToFirstAppearIndex.values()) {
            return appearIndex;
        }
        return -1;
    }
    
    // 4_4と同じ解法
    // 条件判定の順番を変更
    // 4_4の方が読みやすいかなと思った
    // ifに入る条件が少なく、否定を使っていないから見やすそう
    public int firstUniqChar4_5(String s) {
        LinkedHashMap<Character, Integer> letterToFirstAppearIndex = new LinkedHashMap<>();
        Set<Character> duplicated = new HashSet<>();

        for (int index = 0; index < s.length(); index++) {
            char c = s.charAt(index);
            if (!letterToFirstAppearIndex.containsKey(c) && !duplicated.contains(c)) {
                letterToFirstAppearIndex.put(c, index);
                duplicated.add(c);
                continue;
            }
            letterToFirstAppearIndex.remove(c);
        }

        for (int appearIndex : letterToFirstAppearIndex.values()) {
            return appearIndex;
        }
        return -1;
    }
    
}
