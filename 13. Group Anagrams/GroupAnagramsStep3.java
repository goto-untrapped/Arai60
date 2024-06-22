public class GroupAnagramsStep3 {

    /*
     * 気を付けた箇所
     * 1. 辞書順に並び替えてキーとして使う、sortedArrayの変数名に悩んだ。
     * Arrays.sort()がvoid型なので、L15のsortしていない時点でsortedArrayと名付けるのは
     * よくないと思ったが、数行しか使わないのに変数を別で定義する必要はないと思ったため、
     * 目的が分かることを優先して名前を付けた。
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) return new ArrayList<>();

        Map<String, List<String>> anagrams = new HashMap<>();
        for (String str : strs) {
            char[] sortedArray = str.toCharArray();
            Arrays.sort(sortedArray);
            String sorted = String.valueOf(sortedArray);

            if (!anagrams.containsKey(sorted)) {
                anagrams.put(sorted, new ArrayList<>());
            }
            anagrams.get(sorted).add(str);
        }

        return new ArrayList<>(anagrams.values());
    }
    
}
