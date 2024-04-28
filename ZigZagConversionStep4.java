public class ZigZagConversionStep4 {
    /*
     * Step3からの変更箇所：
     * 1. List<Builder>の変数名をsbListではなく、rowsに修正
     * 2. 現在行を表す変数名をcurrRowではなく、currentRowに修正
     * 3. 空白文字にタブとスペースが混在しないように、スペースで統一
     */
    public String convert(String s, int numRows) {
        if (numRows == 1) return s;

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }

        int currentRow = 0;
        int step = 1;
        for (char c : s.toCharArray()) {
            rows.get(currentRow).append(c);
            currentRow += step;

            if (currentRow == 0 || currentRow == numRows - 1) {
                step = -step;
            }
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder row : rows) {
            result.append(row);
        }
        return result.toString();
    }
}
