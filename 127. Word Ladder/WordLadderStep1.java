public class WordLadderStep1 {
    
    /*
     * 累計2hくらい
     * nはwordListの長さ、mは1単語の長さ
     * 時間計算量:O(n * m * m + n * m * m) = O(n*m^2)
     * 空間計算量:O(n * m * m + n * m + n * m) = O(n*m^2)
     * 　中間パターンmのそれぞれにつきm長さの単語を値として追加する
     */
    class Solution {
        record WordAndLength(String word, int length) {}

        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Map<String, List<String>> allCombinations = new HashMap<>();
            for (String word : wordList) {
                for (int i = 0; i < word.length(); i++) {
                    String regexWord = word.substring(0, i) + "*" + word.substring(i + 1, word.length());
                    if (allCombinations.get(regexWord) == null) {
                        allCombinations.put(regexWord, new ArrayList<>());
                    }
                    allCombinations.get(regexWord).add(word);
                }
            }

            Queue<WordAndLength> wordAndLengths = new LinkedList<>();
            wordAndLengths.offer(new WordAndLength(beginWord, 1));
            Set<String> usedWords = new HashSet<>();
            while (!wordAndLengths.isEmpty()) {
                WordAndLength wordAndLength = wordAndLengths.poll();
                String word = wordAndLength.word();
                int length = wordAndLength.length();
                if (word.equals(endWord)) {
                    return length;
                }
                for (int i = 0; i < word.length(); i++) {
                    String searchRegexWord = word.substring(0, i) + "*" + word.substring(i + 1, word.length());
                    if (allCombinations.get(searchRegexWord) == null) {
                        continue;
                    }
                    for (String dictWord : allCombinations.get(searchRegexWord)) {
                        if (usedWords.contains(dictWord)) {
                            continue;
                        }
                        wordAndLengths.offer(new WordAndLength(dictWord, length + 1));
                        usedWords.add(dictWord);
                    }
                }
            }
            return 0;
        }
    }
    /*
     * ・正規表現ではなくてワイルドカードだった。
     * 正規表現：文字列の検索や置換をするためのパターンマッチングの書き方
     * ワイルドカード：任意の文字や文字列を表す特殊文字
     * https://envader.plus/course/11/scenario/1109
     */
    
    
    /*
     * 50min TLE
     * 時間計算量がO(n * n! * m)になると思うので、TLEになるとは思った
     * (nはwordListの長さ、mは1単語の長さ)
     * ・全部数打つ必要があると思ったので、DFSにした。(そのやり方しか思いつかなかった。)
     * 　・https://github.com/seal-azarashi/leetcode/pull/19/files#diff-99ae7db49484c085d3e2d3f6e3fbbb4e8b1afd20f2be7c140c5a349bce7bc97fR145
     * 　　⇒がんばろう。
     * ・配列というか、いい感じにマッピングして探し元で楽しないと時間切れになりそうと思ったけど、
     * 具体的な方法が思いつかなかった。
     * ・isMatch()実装しなくてよかった。(デフォルト実装あった)
     * ・前提条件はコメントを書いておきたい。
     */
    class Solution_TLE {
        record WordIndexAndCount(int wordIndex, int ladderCount, Set<String> existWords) {}

        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            wordList.add(0, beginWord);
            int minLadderCount = Integer.MAX_VALUE;
            Stack<WordIndexAndCount> ladderInfos = new Stack<>();
            Set<String> existWords = new HashSet<>();
            existWords.add(beginWord);
            ladderInfos.push(new WordIndexAndCount(0, 0, existWords));
            while (!ladderInfos.isEmpty()) {
                WordIndexAndCount ladderInfo = ladderInfos.pop();
                int wordIndex = ladderInfo.wordIndex();
                int ladderCount = ladderInfo.ladderCount();
                existWords = ladderInfo.existWords();
                String searchWord = wordList.get(wordIndex);
                if (isMatch(searchWord, endWord)) {
                    minLadderCount = Math.min(minLadderCount, ladderCount + 1); // add beginWord count
                    continue;
                }
                for (int i = 0; i < wordList.size(); i++) {
                    if (existWords.contains(wordList.get(i))) {
                        continue;
                    }
                    if (isOnlyOneLetterDifferent(searchWord, wordList.get(i))) {
                        Set<String> newExistWords = new HashSet<>();
                        newExistWords.addAll(existWords);
                        newExistWords.add(wordList.get(i));
                        ladderInfos.push(new WordIndexAndCount(i, ladderCount + 1, newExistWords));
                    }
                }
            }
            if (minLadderCount == Integer.MAX_VALUE) {
                return 0;
            }
            return minLadderCount;
        }

        private boolean isMatch(String searchWord, String word) {
            char[] searchChars = searchWord.toCharArray();
            char[] wordChars = word.toCharArray();
            for (int i = 0; i < searchChars.length; i++) {
                if (searchChars[i] != wordChars[i]) {
                    return false;
                }
            }
            return true;
        } 

        private boolean isOnlyOneLetterDifferent(String searchWord, String word) {
            char[] searchChars = searchWord.toCharArray();
            char[] wordChars = word.toCharArray();
            boolean isDifferent = false;
            for (int i = 0; i < searchChars.length; i++) {
                if (searchChars[i] != wordChars[i]) {
                    if (isDifferent) {
                        return false;
                    }
                    isDifferent = true;
                }
            }
            return true;
        }
    }
}
