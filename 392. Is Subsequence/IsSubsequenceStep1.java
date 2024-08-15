public class IsSubsequenceStep1 {
    
    // 答えを見た後
    // 時間計算量：O(t) 
    // max(s, t)ではなかった。sよりtの方が長い時、最後まで見るため。
    // 空間計算量：O(1)
    public boolean isSubsequence(String s, String t) {
        if (s.length() == 0) return true;
        if (t.length() == 0) return false;

        int searchIndex = 0;
        int checkIndex = 0;
        while (searchIndex < s.length() && checkIndex < t.length()) {
            char searchChar = s.charAt(searchIndex);
            char checkChar = t.charAt(checkIndex);

            if (searchChar == checkChar) {
                searchIndex++;
                checkIndex++;
                continue;
            }
            checkIndex++;
        }
        return searchIndex == s.length();
    }
    
    // WA 17分かかった
    public boolean isSubsequenceWA(String s, String t) {
        if (s.length() == 0) return true;
        if (t.length() == 0) return false;

        Queue<Character> finds = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char searchChar = s.charAt(i);

            for (int j = i; j < t.length(); j++) {
                char originalChar = t.charAt(j);

                if (searchChar == originalChar) {
                    finds.add(searchChar);
                    break;
                }
            }
        }
        return finds.size() == s.length();
    }
    
}