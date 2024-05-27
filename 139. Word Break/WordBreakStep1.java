public class WordBreakStep1 {
    
    // 40分くらい書いた後、答えを見てデバッグと調整をした １時間くらいかかった
    // 時間計算量：O(wordDict.len * 1単語の長さ + s.len * wordDict.len * 1単語の長さ * 1単語の長さ) = O(s.len * wordDict.len * 1単語の長さ ^ 2)
    // 空間計算量：O(wordDict.len * 1単語の長さ + s.len)
    public boolean wordBreak(String s, List<String> wordDict) {
        HashMap<Character, ArrayList<String>> firstLetterToWords = new HashMap<>();
        for (String word : wordDict) {
            char firstChar = word.charAt(0);
            if (!firstLetterToWords.containsKey(firstChar)) {
                firstLetterToWords.put(firstChar, new ArrayList<>());
            }
            firstLetterToWords.get(firstChar).add(word);
        }

        LinkedList<Integer> searchIndexes = new LinkedList<>();
        searchIndexes.add(0);
        boolean[] isEachMatched = new boolean[s.length()];
        while (!searchIndexes.isEmpty()) {
            int searchIndex = searchIndexes.pop();
            char searchChar = s.charAt(searchIndex);
            if (!firstLetterToWords.containsKey(searchChar)) {
                continue;
            }
            if (isEachMatched[searchIndex]) {
                continue;
            }
            ArrayList<String> words = firstLetterToWords.get(searchChar);
            for (String word : words) {
                if (searchIndex + word.length() > s.length()) {
                    continue;
                }

                boolean isMatched = true;
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) != s.charAt(searchIndex + i)) {
                        isMatched = false;
                        break;
                    }
                }
                if (!isMatched) {
                    continue;
                }

                if (searchIndex + word.length() == s.length()) {
                    return true;
                }

                isEachMatched[searchIndex] = true;
                searchIndexes.push(searchIndex + word.length());
            }
        }
        return false;
    }
    
    /*
     * WA 25分
     * 同じキーを２回見れない。s="applepenapple", wordDict=["apple","pen"]
     * また、同じ頭文字の単語が2つあっても見れない。 s="leetcode", wordDict=["let","code","leet"]
     */
    public boolean wordBreak_WA(String s, List<String> wordDict) {
        HashMap<Character, ArrayList<String>> firstLetterToWords = new HashMap<>();
        for (String word : wordDict) {
            char firstChar = word.charAt(0);
            if (!firstLetterToWords.containsKey(firstChar)) {
                firstLetterToWords.put(firstChar, new ArrayList<>());
            }
            firstLetterToWords.get(firstChar).add(word);
        }

        return isWordBreak(s, firstLetterToWords, 0);
    }

    private boolean isWordBreak(String s, HashMap<Character, ArrayList<String>> firstLetterToWords, int searchIndex) {
        if (searchIndex == s.length()) {
            return true;
        }
        if (searchIndex > s.length()) {
            return false;
        }

        char searchChar = s.charAt(searchIndex);
        if (!firstLetterToWords.containsKey(searchChar)) {
            return false;
        }

        for (char firstChar : firstLetterToWords.keySet()) {
            ArrayList<String> words = firstLetterToWords.get(firstChar);
            for (String word : words) {
                if (!isWordBreak(s, firstLetterToWords, searchIndex + word.length())) {
                    return false;
                }
            }
        }
        return true;
    }
    
}
