public class FirstUniqueCharacterInAStringStep3 {
    
    // 英語小文字でない文字が入ってきたときもプログラムを書いて対応できそうだと思ったので、
    // mapを使うやり方を採用。
    // でも、26文字のchar配列を使う時と比べて、実行時間が5倍かかる。(mapを使うと30ms、char配列は5ms)
    // 使うケースによってはどれくらいの速さになるか確認して、書き分けるといいのかなと思った。
    public int firstUniqChar(String s) {
        Map<Character, Integer> letterToFirstAppearIndex = new HashMap<>();

        for (char c : s.toCharArray()) {
            int currentCount = letterToFirstAppearIndex.getOrDefault(c, 0);
            letterToFirstAppearIndex.put(c, currentCount + 1);
        }

        for (int index = 0; index < s.length(); index++) {
            if (letterToFirstAppearIndex.get(s.charAt(index)) == 1) {
                return index;
            }
        }
        return -1;
    }
    
}
