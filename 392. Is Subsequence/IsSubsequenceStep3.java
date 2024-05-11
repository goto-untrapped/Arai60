public class IsSubsequenceStep3 {
    
    public boolean isSubsequence(String s, String t) {
        int searchIndex = 0;
        int targetIndex = 0;
        while (searchIndex < s.length() && targetIndex < t.length()) {
            if (s.charAt(searchIndex) != t.charAt(targetIndex)) {
                targetIndex++;
                continue;
            }
            searchIndex++;
            targetIndex++;
        }

        return searchIndex == s.length();
    }
    
}