public class GenerateParenthesesStep2 {
    
    // 参照
    // https://github.com/Exzrgs/LeetCode/pull/6/files
    // https://github.com/SuperHotDogCat/coding-interview/pull/7/files
    
    // 全通りで試す
    public List<String> generateParenthesis(int n) {
        List<String> answer = new ArrayList<>();
        // (1) Arrays.asList()の返り値を直接代入すると変更不可
        //     var list = Arrays.asList();
        // (2) 返り値をコンストラクタに指定すれば変更可能
        //     var list = new ArrayList<>(Arrays.asList());
        Queue<String> composed = new LinkedList<>(Arrays.asList(""));

        while (!composed.isEmpty()) {
            String current = composed.poll();

            if (current.length() == 2 * n && isValid(current)) {
                answer.add(current);
            }

            if (current.length() < 2 * n) {
                composed.add(current + "(");
                composed.add(current + ")");
            }
        }
        return answer;
    }

    private boolean isValid(String str) {
        int leftCount = 0;

        for (char c : str.toCharArray()) {
            if (c == '(') {
                leftCount++;
            } else if (c == ')') {
                leftCount--;
            }

            if (leftCount < 0) {
                return false;
            }
        }

        return leftCount == 0;
    }
    
}
