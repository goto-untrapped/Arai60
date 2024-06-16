public class FirstUniqueCharacterInAStringStep1 {
       
    // 20分くらいかかった。
    // 変数を２つ使うなど、無理やり通した感じがある。
    public int firstUniqChar(String s) {
        Map<Character, Integer> letterToFirstIndex = new LinkedHashMap<>();
        boolean[] isExisted = new boolean[26];

        int count = 0;
        for (char c : s.toCharArray()) {
            if (!letterToFirstIndex.containsKey(c) && !isExisted[c - 'a']) {
                letterToFirstIndex.put(c, count);
            } else {
                letterToFirstIndex.remove(c);
                isExisted[c - 'a'] = true;
            }
            count++;
        }

        for (char c : letterToFirstIndex.keySet()) {
            if (!isExisted[c - 'a']) {
                return letterToFirstIndex.get(c);
            }
        }

        return -1;
    }
    
    /*
     * 答えを見た後の反省：
     * Q なぜ答えの解き方ができなかったか？
     * ⇒どの情報を何に紐づければいいのかを整理しきれなかった
     * ・出現する順番、出現する回数、出現する文字列
     * ・変数の格納順、マップのキー、値？何かを操作して示す？
     * Q なぜ整理しきれなかったか？
     * ⇒頭の中が散らばってた。わざわざLinkedHashMap使ってたらそれで出現順番保持できるのに、
     * なぜかバリューに出現回数持たせてた。
     * Q なぜ散らばってたのか？
     * ⇒おそらく時間を気にしすぎた。これぐらい書けなきゃ、という気持ちが常にあった気がする。
     * Q どうすれば焦らずに済むのか？
     * ⇒手が止まってないのであれば、時間がかかっても一旦整理する。整理してから書く。速度は後から上げる。
     */
}
