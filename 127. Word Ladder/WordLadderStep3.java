public class WordLadderStep3 {
    // 12min
    class Solution {
        record WordAndCount(String word, int count) {}

        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            List<String> searchWords = new ArrayList<>();
            searchWords.addAll(wordList);
            searchWords.add(beginWord);
            Map<String, List<String>> wordToAdjacentWords = new HashMap<>();
            for (String word : searchWords) {
                wordToAdjacentWords.put(word, new ArrayList<>());
            }
            for (int i = 0; i < searchWords.size(); i++) {
                String word1 = searchWords.get(i);
                for (int j = i + 1; j < searchWords.size(); j++) {
                    String word2 = searchWords.get(j);
                    if (isOneLetterDifferent(word1, word2)) {
                        wordToAdjacentWords.get(word1).add(word2);
                        wordToAdjacentWords.get(word2).add(word1);
                    }
                }
            }

            Queue<WordAndCount> wordAndCounts = new LinkedList<>();
            wordAndCounts.offer(new WordAndCount(beginWord, 1));
            Set<String> usedWords = new HashSet<>();
            usedWords.add(beginWord);
            while (!wordAndCounts.isEmpty()) {
                WordAndCount wordAndCount = wordAndCounts.poll();
                String word = wordAndCount.word();
                int count = wordAndCount.count();
                if (wordToAdjacentWords.get(word) == null) {
                    continue;
                }
                for (String adjacentWord : wordToAdjacentWords.get(word)) {
                    if (usedWords.contains(adjacentWord)) {
                        continue;
                    }
                    if (adjacentWord.equals(endWord)) {
                        return count + 1;
                    }
                    wordAndCounts.offer(new WordAndCount(adjacentWord, count + 1));
                    usedWords.add(adjacentWord);
                }
            }
            return 0;
        }

        private boolean isOneLetterDifferent(String word1, String word2) {
            // same length, word1 != word2
            char[] chars1 = word1.toCharArray();
            char[] chars2 = word2.toCharArray();
            int charLength = chars1.length;
            boolean isAlreadyDifferent = false;
            for (int i = 0; i < charLength; i++) {
                if (chars1[i] == chars2[i]) {
                    continue;
                }
                if (isAlreadyDifferent) {
                    return false;
                }
                isAlreadyDifferent = true;
            }
            return true;
        }
    }
    /*
     * ・一番実直で間違えにくいと思った方法を採用。
     */
}
