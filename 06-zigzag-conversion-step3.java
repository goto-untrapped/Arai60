public class ZigzagConversionStep3 {
	
	// ListにいきなりStringBuilderを入れることで、速い処理ができると思い、採用した。
	// Step1で行ったやり方を、より少ない変数、よりシンプルな処理で書くことが、一番安心できたため、採用した。
	public String convert(String s, int numRows) {
        if (numRows == 1) return s;

        List<StringBuilder> sbList = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            sbList.add(new StringBuilder());
        }

        int currRow = 0;
        int step = 1;
        for (char c : s.toCharArray()) {
            sbList.get(currRow).append(c);
            currRow += step;

            if (currRow == 0 || currRow == numRows - 1) {
                step = -step;
            }
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : sbList) {
            result.append(sb);
        }
        return result.toString();
    }
	
}