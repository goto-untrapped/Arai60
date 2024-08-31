public class ValidParenthesesStep4 {
    
    /*
     * 修正点
     * ・全体的な処理の流れを、見る負荷が減るように修正
     */
    public boolean isValid(String s) {
        Map<Character, Character> closeToOpen = new HashMap<>();
        closeToOpen.put(')', '(');
        closeToOpen.put('}', '{');
        closeToOpen.put(']', '[');

        Stack<Character> openBrackets = new Stack<>();
        for (char c : s.toCharArray()) {
            // 始まりのかっこは判定するため、スタックに格納
            if (!closeToOpen.containsKey(c)) {
                openBrackets.push(c);
                // このターンでやれることは終わったため、次のターンに移ってよい
                continue;
            }
            // 終わりのかっこが来ているが、始まりのかっこがないのであれば、その時点でfalse
            if (openBrackets.isEmpty()) {
                return false;
            }
            // 取り出したかっこのペアが異なっている場合、その時点でfalse
            if (openBrackets.pop() != closeToOpen.get(c)) {
                return false;
            }
        }
        // 始まりのかっこが入ったままのケースが想定できるため
        return openBrackets.isEmpty();
    }
    
}
