public class IsSubsequenceStep2 {
    
    /*
     * https://github.com/shining-ai/leetcode/pull/57
     *   https://discord.com/channels/1084280443945353267/1201211204547383386/1231617397446803466
     *   
     * https://github.com/hayashi-ay/leetcode/pull/64
     *   https://discord.com/channels/1084280443945353267/1200089668901937312/1222146248056770610
     */
    
    // Step1をもっときれいに書く
    public boolean isSubsequence2_1(String s, String t) {
        int searchIndex = 0;
        int targetIndex = 0;
        while (searchIndex < s.length() && targetIndex < t.length()) {
            if (s.charAt(searchIndex) == t.charAt(targetIndex)) {
                searchIndex++;
                targetIndex++;
                continue;
            }
            targetIndex++;
        }
        return searchIndex == s.length();
    }
    
    // t はどっちみちプラスするよね
    public boolean isSubsequence2_2(String s, String t) {
        int searchIndex = 0;
        int targetIndex = 0;
        while (searchIndex < s.length() && targetIndex < t.length()) {
            if (s.charAt(searchIndex) == t.charAt(targetIndex)) {
                searchIndex++;
            }
            targetIndex++;
        }
        return searchIndex == s.length();
    }
    
    // t だけでループしてもいける
    // 本当は return true/false で分け切る方が分かりやすいと思ったけど、
    // sが長さ0の時を処理するために、return true を先に持ってきた影響で、最後を return false と書けなかった 
    public boolean isSubsequence2_3(String s, String t) {
        int searchIndex = 0;
        for (char target : t.toCharArray()) {
            if (searchIndex == s.length()) {
                return true;
            }
            if (s.charAt(searchIndex) == target) {
                searchIndex++;
            }
        }
        return searchIndex == s.length();
    }
    
    /*
     * 出現位置を記録して判定する
     * Step1でアイデアは思い浮かんだけど、使えないと思った
     * a=[0,3,...] とあったら、0始まりも3始まりも見ないといけないと思い、他の文字もそれで見るとなると総当たりくらいやる必要があると思った。
     * そうではなく、複数の出現場所が格納してあっても、調べる文字列を順に見た時にその文字の最初の出現回数より後の順番を探して、見つかれば次の文字、という風に見ればよかった。
     * あ、何通りあるか見る必要があるのなら、総当たり？になって、今は1通りでもあればいいので、それでいいということなのかな。
     * 複数の出現回数を数えておくのは、文字の順番が分からないからであって、直結で全部見ることにはならなかった・・・。
     */
    public boolean isSubsequence2_4(String s, String t) {
        HashMap<Character, ArrayList<Integer>> letterToAppearIndexes = new HashMap<>();
        for (int index = 0; index < t.length(); index++) {
            char c = t.charAt(index);
            if (!letterToAppearIndexes.containsKey(c)) {
                letterToAppearIndexes.put(c, new ArrayList<>());
            }
            letterToAppearIndexes.get(c).add(index);
        }

        int updatedStartIndex = -1;
        for (char searchChar : s.toCharArray()) {
            if (!letterToAppearIndexes.containsKey(searchChar)) {
                return false;
            }

            boolean isNoMatchIndex = true;
            for (int appearIndex : letterToAppearIndexes.get(searchChar)) {
                if (updatedStartIndex < appearIndex) {
                    updatedStartIndex = appearIndex;
                    isNoMatchIndex = false;
                    break;
                }
            }
            if (isNoMatchIndex) {
                return false;
            }
        }

        return true;       
    }
    
    /*
     * 一致した個数を数えながら判定する
     * 下記のように考えて、何とか受け入れてみる
     * 手札が一致したらポイントが増える。
     * もしくは、既に手札が一致していた場合、後続のカードはポイントが増えたと見なせる。
     * 手札が一致しない時、そのカードまでのパラレルワールドの成績と、自分の今までの成績のうち、いい方を使う。
     * 自分の成績も見るのは、手札が一致したボーナスがあるかもしれないから。
     * 一致する時は、すべてのパラレルワールドから来た成績に1を足す。
     * 自分の過去の成績を使うと、1つの次元で同じカードを2回足しているかもしれないので。
     */
    public boolean isSubsequence2_5(String s, String t) {
        int[][] matchCounts = new int[s.length() + 1][t.length() + 1];

        for (int targetIndex = 1; targetIndex < t.length() + 1; targetIndex++) {
            for (int searchIndex = 1; searchIndex < s.length() + 1; searchIndex++) {
                if (t.charAt(targetIndex - 1) == s.charAt(searchIndex - 1)) {
                    matchCounts[searchIndex][targetIndex] = matchCounts[searchIndex - 1][targetIndex - 1] + 1;
                    continue;
                } 
                matchCounts[searchIndex][targetIndex] = Math.max(matchCounts[searchIndex][targetIndex - 1], matchCounts[searchIndex - 1][targetIndex]);
            }
        }
        return matchCounts[s.length()][t.length()] == s.length();
    }
    
    // 見つけたらその先に行く
    public boolean isSubsequence2_6(String s, String t) {
        int updatedIndex = -1;
        for (char searchChar : s.toCharArray()) {
            updatedIndex = t.indexOf(searchChar, updatedIndex + 1);
            if (updatedIndex == -1) {
                return false;
            }
        }
        return true;
    }

    // TLE
    // 正規表現で判定する
    // Pythonと比べてどれくらい遅いんだろう
    // https://discord.com/channels/1084280443945353267/1201211204547383386/1232001549098680381
    //   (これは、本当はエスケープしないといけないし、セキュリティ的に危ないという感覚は持っておいてください。(s に "." が含まれるなど。))
    public boolean isSubsequence_TLE(String s, String t) {
        StringBuilder pattern = new StringBuilder();
        pattern.append(".*");
        for (char c : s.toCharArray()) {
            pattern.append(c).append(".*");
        }
        pattern.append(".*");
        return s.length() == 0 || t.matches(pattern.toString());
    }
    
}