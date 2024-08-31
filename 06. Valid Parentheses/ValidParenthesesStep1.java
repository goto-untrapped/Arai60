public class ValidParenthesesStep1 {
    
    // 解き直し
    // 条件を分けずに一括で解ける方法がありそうだと思ったが、
    // パターン漏れが怖かったので、細かく処理を分けてしまった。
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (stack.isEmpty()) {
                if (c == ')' || c == '}' || c ==']') {
                    return false;
                } 
                stack.push(c);
                continue;
            }

            if (c == '(' || c == '{' || c =='[') {
                stack.push(c);
                continue;
            }

            char output = stack.pop();
            if ((output == '(' && c == ')') 
                || (output == '{' && c == '}') 
                || (output == '[' && c == ']')) {

                continue;
            } 

            return false;
        }

        return stack.size() == 0 ? true : false;
    }
    
}
