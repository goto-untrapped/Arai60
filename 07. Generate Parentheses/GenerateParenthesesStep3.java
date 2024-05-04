public class GenerateParenthesesStep3 {
    
    public List<String> generateParenthesis(int n) {
        List<String> validParentheses = new ArrayList<>();
        generateValidParentheses(validParentheses, new StringBuilder(), 0, 0, n);
        return validParentheses;
    }

    private void generateValidParentheses(List<String> validParentheses, StringBuilder current, int leftCount, int rightCount, int limit) {
        if (current.length() == 2 * limit) {
            validParentheses.add(current.toString());
        }

        if (leftCount < limit) {
            current.append("(");
            generateValidParentheses(validParentheses, current, leftCount + 1, rightCount, limit);
            current.deleteCharAt(current.length() - 1);
        }

        if (rightCount < leftCount) {
            current.append(")");
            generateValidParentheses(validParentheses, current, leftCount, rightCount + 1, limit);
            current.deleteCharAt(current.length() - 1);
        }
    }
    
}
