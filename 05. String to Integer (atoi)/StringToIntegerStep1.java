public class StringToIntegerStep1 {
    
    // 分からなかったので、答えを参考にして書いた
    // 2ms / 42.70MB
    // O(n) / O(1)
    public int myAtoi(String s) {
        int sign = 1;
        int result = 0;
        int index = 0;
        int n = s.length();

        while (index < n && s.charAt(index) == ' ') {
            index++;
            continue;
        }

        if (index < n && s.charAt(index) == '+') {
            sign = 1;
            index++;
        } else if (index < n && s.charAt(index) == '-') {
            sign = -1;
            index++;
        }

        while (index < n && Character.isDigit(s.charAt(index))) {
            String cStr = Character.toString(s.charAt(index));

            if (result > Integer.MAX_VALUE / 10 || 
                (result == Integer.MAX_VALUE / 10 && Integer.valueOf(cStr) > Integer.MAX_VALUE % 10)) {
                    return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                }

            result = result * 10 + Integer.valueOf(cStr);
            index++;
        }

        return sign * result;
    }
    
}
