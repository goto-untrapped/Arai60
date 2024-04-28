public class GroupAnagramsStep4 {
    
    /*
     * Step3からの変更点
     * 1. mapの変数名を sortedToAnagrams に変更。
     * 2. キー用に並び替えるchar配列の名前を sortedChars に変更。 
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> sortedToAnagrams = new HashMap<>();
        for (String str : strs) {
            char[] sortedChars = str.toCharArray();
            Arrays.sort(sortedChars);
            String sorted = String.valueOf(sortedChars);

            if (!sortedToAnagrams.containsKey(sorted)) {
                sortedToAnagrams.put(sorted, new ArrayList<>());
            }
            sortedToAnagrams.get(sorted).add(str);
        }

        return new ArrayList<>(sortedToAnagrams.values());
    }
    
}
