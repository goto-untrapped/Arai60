public class GenerateParenthesesStep1 {
    
    /*
     * 答えを見た
     * 時間計算量：O(4n / √n)
     * カタラン数というもの
     * 空間計算量：O(n)
     * 最大で、leftCountがnに達し、rightcountもnに達する時 = 2n
     */ 
    public List<String> generateParenthesis(int n) {
        List<String> answer = new ArrayList<>();
        backtracking(answer, new StringBuilder(), 0, 0, n);
        return answer;
    }

    private void backtracking(List<String> answer, StringBuilder currentStr, int leftCount, int rightCount, int limit) {
        if (currentStr.length() == 2 * limit) {
            answer.add(currentStr.toString());
        }

        if (leftCount < limit) {
            currentStr.append("(");
            backtracking(answer, currentStr, leftCount + 1, rightCount, limit);
            currentStr.deleteCharAt(currentStr.length() - 1);
        }

        if (rightCount < leftCount) {
            currentStr.append(")");
            backtracking(answer, currentStr, leftCount, rightCount + 1, limit);
            currentStr.deleteCharAt(currentStr.length() - 1);
        }
    }
}
