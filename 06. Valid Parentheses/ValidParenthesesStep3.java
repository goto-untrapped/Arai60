public class ValidParenthesesStep3 {
    
    public boolean isValid(String s) {
        Map<Character, Character> closeToOpen = new HashMap<>();
        closeToOpen.put(')', '(');
        closeToOpen.put('}', '{');
        closeToOpen.put(']', '[');

        Stack<Character> openBrackets = new Stack<>();
        for (char c : s.toCharArray()) {
            if (closeToOpen.containsKey(c)) {
                char latestOpenBracket = openBrackets.isEmpty() ? '#' : openBrackets.pop();

                if (latestOpenBracket != closeToOpen.get(c)) {
                    return false;
                }

            } else {
                openBrackets.push(c);
            }
        }
        return openBrackets.isEmpty();
    }
    
}
