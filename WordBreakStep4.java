public class WordBreakStep4 {
    /*
     * ・startsWith()で、このsに含まれているか調べたい単語の長さが、sを越さないように判定してくれる。
     * ・early returnを使って、ネストを浅くできるよう意識する。
     * ・ループでiをプラスマイナスする場合、条件を分けて書かなくてもいいように、
     * 1つの配列を使ったり、early returnを使ったりして簡潔に表現できるように意識する。
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] canBeSegmented = new boolean[s.length() + 1];
        canBeSegmented[0] = true;

        for (int index = 0; index < s.length(); index++) {
            for (String word : wordDict) {
                if (!canBeSegmented[index]) {
                    continue;
                }

                if (s.startsWith(word, index)) {
                    canBeSegmented[index + word.length()] = true;
                }
            }
        }
        return canBeSegmented[s.length()];
    }
}
