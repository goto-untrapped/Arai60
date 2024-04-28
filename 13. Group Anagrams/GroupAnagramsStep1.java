public class GroupAnagramsStep1 {

    /*
     * 時間計算量：O(n * mlogm) 
     * nは単語数、mは1単語あたりの平均の長さ
     * 1単語を並び替えているから、mlogm × n単語分
     * 空間計算量：O(n)
     * 一番サイズが大きいのはMap。合わせると、キーの個数 ＋ n単語。
     * キーの個数は最大でもn単語分だから、n
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> dictToWords = new HashMap<>();
        List<List<String>> anagrams = new ArrayList<>();
        
        for (String str : strs) {
            /*
             * はじめ、下記のように書いてコンパイルエラー。
             * String sorted = String.valueOf(Arrays.sort(str.toCharArray()));
             * 推測した原因：
             *   Arrays.sort()は返り値を持たず、sort()の中身を直接変更する。
             *   なので、String.valueOf()の中身がnullになるのと同じになり、エラー。
             * また、今、中身であるstr.toCharArray()は保持していないので、
             * 変数を定義しておかないと、辞書順にソートした単語をプログラムで使えない。
             */
            char[] toSort = str.toCharArray();
            Arrays.sort(toSort);
            String sorted = String.valueOf(toSort);
            if (!dictToWords.containsKey(sorted)) {
                dictToWords.put(sorted, new ArrayList<>());
            }
            dictToWords.get(sorted).add(str);
        }

        for (String key : dictToWords.keySet()) {
            anagrams.add(dictToWords.get(key));
        }

        return anagrams;
    }
    
}
