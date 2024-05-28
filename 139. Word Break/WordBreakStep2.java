public class WordBreakStep2 {

    /*
     * https://github.com/hayashi-ay/leetcode/pull/61/files
     *   https://discord.com/channels/1084280443945353267/1200089668901937312/1221803544705761312
     *   
     * https://github.com/shining-ai/leetcode/pull/39
     *   https://discord.com/channels/1084280443945353267/1201211204547383386/1224731898622771371
     */

    // Step1をきれいにする(BFS)
    public boolean wordBreak2_1(String s, List<String> wordDict) {
        LinkedList<Integer> searchIndexes = new LinkedList<>();
        searchIndexes.push(0);

        while (!searchIndexes.isEmpty()) {
            int searchIndex = searchIndexes.pop();
            if (searchIndex == s.length()) {
                return true;
            }

            for (int start = 0; start < s.length(); start++) {
                for (int end = start + 1; end < s.length() + 1; end++) {
                    if (wordDict.contains(s.substring(start, end))) {
                        searchIndexes.push(start + 1);
                    }
                }
            }
        }
        return false;
    }

    /**
     * トップダウンDP 後ろから
     */
    public boolean wordBreak2_2(String s, List<String> wordDict) {
        int[] areWordBreak = new int[s.length()];
        Arrays.fill(areWordBreak, -1);
        return judgeWordBreak2_2(s, wordDict, areWordBreak, s.length() - 1);
    }

    private boolean judgeWordBreak2_2(String s, List<String> wordDict, int[] areWordBreak, int index) {
        if (index < 0) {
            return true;
        }

        if (areWordBreak[index] != -1) {
            return areWordBreak[index] == 1;
        }

        for (String word : wordDict) {
            if (index - word.length() + 1 < 0) {
                continue;
            }

            if (s.substring(index - word.length() + 1, index + 1).equals(word)
                    && judgeWordBreak2_2(s, wordDict, areWordBreak, index - word.length())) {
                areWordBreak[index] = 1;
                return true;
            }
        }
        areWordBreak[index] = 0;
        return false;
    }

    /**
     * トップダウンDP 前から
     */
    public boolean wordBreak2_3(String s, List<String> wordDict) {
        int[] areWordBreak = new int[s.length()];
        Arrays.fill(areWordBreak, -1);
        judgeWordBreak2_3(s, wordDict, areWordBreak, 0);
        return areWordBreak[s.length() - 1] == 1;
    }

    private boolean judgeWordBreak2_3(String s, List<String> wordDict, int[] areWordBreak, int index) {
        if (index >= s.length()) {
            return true;
        }

        if (areWordBreak[index] != -1) {
            return areWordBreak[index] == 1;
        }

        for (String word : wordDict) {
            if (index + word.length() > s.length()) {
                continue;
            }

            if (s.substring(index, index + word.length()).equals(word)
                    && judgeWordBreak2_3(s, wordDict, areWordBreak, index + word.length())) {
                areWordBreak[index + word.length() - 1] = 1;
                return true;
            }
        }
        areWordBreak[index] = 0;
        return false;
    }

    /**
     * ボトムアップDP 前から
     */
    public boolean wordBreak2_4(String s, List<String> wordDict) {
        boolean[] areMatchBreak = new boolean[s.length()];
        for (int start = 0; start < s.length(); start++) {
            for (String word : wordDict) {
                if (start + word.length() > s.length()) {
                    continue;
                }

                if (start == 0 || areMatchBreak[start - 1]) {
                    if (s.substring(start, start + word.length()).equals(word)) {
                        areMatchBreak[start + word.length() - 1] = true;
                    }
                }
            }
        }
        return areMatchBreak[s.length() - 1];
    }

    /**
     * Trie
     */
    public boolean wordBreak2_5(String s, List<String> wordDict) {
        boolean[] areMatchBreak = new boolean[s.length()];
        for (int start = 0; start < s.length(); start++) {
            Trie wordTrie = new Trie();
            for (String word : wordDict) {
                Trie trie = wordTrie;
                for (char c : word.toCharArray()) {
                    if (!trie.charToTrie.containsKey(c)) {
                        trie.charToTrie.put(c, new Trie());
                    }
                    trie = trie.charToTrie.get(c);
                }
                trie.isWord = true;
            }
            

            Trie trie = wordTrie;
            if (start == 0 || areMatchBreak[start - 1]) {
                for (int end = start; end < s.length(); end++) {
                    char c = s.charAt(end);
                    if (!trie.charToTrie.containsKey(c)) {
                        break;
                    }

                    trie = trie.charToTrie.get(c);
                    if (trie.isWord) {
                        areMatchBreak[end] = true;
                    }
                }
            }

        }
        return areMatchBreak[s.length() - 1];
    }

    private class Trie {
        private HashMap<Character, Trie> charToTrie;
        private boolean isWord = false;

        public Trie() {
            this.charToTrie = new HashMap<Character, Trie>();
        }
    }

}
