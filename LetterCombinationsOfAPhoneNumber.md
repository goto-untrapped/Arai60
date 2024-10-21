#### Step1 を整えた
```java
class Solution {
    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {
            return new ArrayList<String>();
        }
        List<String> combinations = new ArrayList<>();
        StringBuilder combination = new StringBuilder();
        Map<Character, String> digitToLetters = new HashMap<>();
        makeLettersMapping(digitToLetters);
        makeCombinations(digits, combinations, digitToLetters, combination, 0);
        return combinations;
    }

    private void makeLettersMapping(Map<Character, String> digitToLetters) {
        digitToLetters.put('2', "abc");
        digitToLetters.put('3', "def");
        digitToLetters.put('4', "ghi");
        digitToLetters.put('5', "jkl");
        digitToLetters.put('6', "mno");
        digitToLetters.put('7', "pqrs");
        digitToLetters.put('8', "tuv");
        digitToLetters.put('9', "wxyz");
    }
    
    private void makeCombinations( String digits, List<String> combinations,
      Map<Character, String> digitToLetters, StringBuilder combination, int index) {

        if (combination.length() == digits.length()) {
            combinations.add(combination.toString());
            return;
        }
        String letters = digitToLetters.get(digits.charAt(index));
        for (Character letter : letters.toCharArray()) {
            combination.append(letter);
            makeCombinations(digits, combinations, digitToLetters, combination, index + 1);
            combination.deleteCharAt(combination.length() - 1);
        }
    }
}
``` 
- 時間計算量：O(1ダイヤルあたりの文字列 ^ 文字数)
- 空間計算量：O(Math.min(1ダイヤルあたりの文字列 ^ 文字数, 8))

##### 所感
- 25分くらいかかってACした。
	- 4回くらいテスト実行した
		- はじめ、`Map<Character, List<String>> digitToLetters` としており、 charとintの変換がうまくいかなかった。
			- L21あたりがもっと冗長だった。
- L25の削除の計算量を確認したらO(n)かかってそう
	- [jdk/src/java.base/share/classes/java/lang/StringBuilder.java at master · openjdk/jdk · GitHub](https://github.com/openjdk/jdk/blob/master/src/java.base/share/classes/java/lang/StringBuilder.java#L298)
		- AbstracutStringBuilder L969 deleteCharAt()
			- AbstracutStringBuilder L1077 shift()
				- L1078 System.arraycopy();
				- [java - Time complexity of System.arraycopy(...)? - Stack Overflow](https://stackoverflow.com/questions/7165594/time-complexity-of-system-arraycopy)
				- いきなり + で結合しても新しいオブジェクトができているので、まだStringBuilderを使った方が型安全そう
					- 他にもGuavaのJoinnerとかStringJoinerとかあるけど、標準でこんな感じかぁという気持ち
- 再帰の回数は多くてn=4なので、メモリはかなり余裕がありそう。
- Guavaにpermutations()関数がいた。
	- [Collections2 (Guava: Google Core Libraries for Java HEAD-jre-SNAPSHOT API)](https://guava.dev/releases/snapshot-jre/api/docs/com/google/common/collect/Collections2.html#permutations(java.util.Collection))
- Map.of()で('2',"abc",'3',"def")みたいに一気に初期化できるけど、キーとバリューを見た目分かりやすくしたかった。
- `List<>` で宣言した方が、実装型よりは変更時に対応しやすそう。
