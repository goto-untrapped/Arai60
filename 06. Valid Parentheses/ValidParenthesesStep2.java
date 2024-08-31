public class ValidParenthesesStep2 {
    
    /*
     * 参照
     * https://github.com/Exzrgs/LeetCode/pull/5/files
     * https://github.com/sakupan102/arai60-practice/pull/7/files
     * https://github.com/Mike0121/LeetCode/pull/2/files
     */
    public boolean isValid2_1(String s) {
        Map<Character, Character> closeToOpen = new HashMap<>();
        closeToOpen.put(')', '(');
        closeToOpen.put('}', '{');
        closeToOpen.put(']', '[');

        Stack<Character> startBrackets = new Stack<>();
        for (char c : s.toCharArray()) {
            if (closeToOpen.containsKey(c)) {
                char latestOpenBracket = startBrackets.isEmpty() ? '#' : startBrackets.pop();

                if (latestOpenBracket != closeToOpen.get(c)) {
                    return false;
                }
            } else {
                startBrackets.push(c);
            }
        }
        return startBrackets.isEmpty();
    }
    
    // mapのキーをはじまりのかっこにしてみた
    // 1コマ多くなる感じがする
    // おそらく、map.get(key)のkeyがstackに入っている文字のため、
    // エラーにならないようにmapの空判定を先にする必要があるから
    public boolean isValid2_2(String s) {
        Map<Character, Character> openToClose = new HashMap<>();
        openToClose.put('(', ')');
        openToClose.put('{', '}');
        openToClose.put('[', ']');

        Stack<Character> openBrackets = new Stack<>();
        for (char c : s.toCharArray()) {
            if (openToClose.containsKey(c)) {
                openBrackets.push(c);

            } else if (openBrackets.isEmpty()) {
                return false;
                
            } else {
                if (c != openToClose.get(openBrackets.pop())) {
                    return false;
                }
            }
        }

        return openBrackets.isEmpty();
    }
    
}
