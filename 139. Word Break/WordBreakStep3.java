public class WordBreakStep3 {
    
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] areWords = new boolean[s.length()];

        for (int index = 0; index < s.length(); index++) {
            for (String word : wordDict) {
                if (index == 0 || areWords[index - 1]) {
                    if (index + word.length() <= s.length()
                        && s.substring(index, index + word.length()).equals(word)) {

                        areWords[index + word.length() - 1] = true;
                    }
                }
            }
        }

        return areWords[s.length() - 1];
    }
}
