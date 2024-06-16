public class FirstUniqueCharacterInAStringStep2 {
    
    /*
     * https://github.com/hayashi-ay/leetcode/pull/28/files
     *   mapの変数名はchar_to_first_appear_indexはの方が分かりやすいな。
     */
    
    // Step1をもっとシンプルにしたもの
    public int firstUniqChar2_1(String s) {
        Map<Character, Integer> letterToCount = new LinkedHashMap<>();

        for (char c : s.toCharArray()) {
            letterToCount.put(c, letterToCount.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < s.length(); i++) {
            if (letterToCount.get(s.charAt(i)) == 1) {
                return i;
            }
        }

        return -1;
    }
    
    // これでできた
    public int firstUniqChar2_2(String s) {
        int[] counts = new int[26];

        for (char c : s.toCharArray()) {
            counts[c - 'a']++;
        }

        for (int i = 0; i < s.length(); i++) {
            if (counts[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
    
}
