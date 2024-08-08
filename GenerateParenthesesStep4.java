public class GenerateParenthesesStep4 {
    
    /*
     * 修正点
     * ・全体的な処理の流れを、見る負荷が減るように修正
     */
    public List<String> generateParenthesis(int n) {
        List<String> validParentheses = new ArrayList<>();
        generateValidParentheses(validParentheses, new StringBuilder(), 0, 0, n);
        return validParentheses;
    }

    private void generateValidParentheses( List<String> validParentheses, 
                                           StringBuilder currentParenthesis, 
                                           int openCount, 
                                           int closeCount, 
                                           int limit) {
        
        if (currentParenthesis.length() == 2 * limit) {
            validParentheses.add(currentParenthesis.toString());
            // かっこの総数が上限なので、この後のifに入ることはない
            return;
        }

        if (openCount < limit) {
            currentParenthesis.append("(");
            generateValidParentheses(validParentheses, currentParenthesis, openCount + 1, closeCount, limit);
            currentParenthesis.deleteCharAt(currentParenthesis.length() - 1);
        }
        if (closeCount < openCount) {
            currentParenthesis.append(")");
            generateValidParentheses(validParentheses, currentParenthesis, openCount, closeCount + 1, limit);
            currentParenthesis.deleteCharAt(currentParenthesis.length() - 1);
        }
    }
    
}
