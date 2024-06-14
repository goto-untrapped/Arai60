public class UniqueEmailAddressesStep2 {
    
    // https://github.com/nittoco/leetcode/pull/7/files
    // ⇒split()は2回しない
    // https://github.com/tshimosake/arai60/pull/2/files
    // ⇒@が2回来た時どう振る舞うとよいか？状況次第で決める。
    //   Exception を投げる。->実装元で判断する時？
    //   プログラムが停止する。->処理を続けたらまずい時？
    //   エラーを示す値を返す。->続けて処理したい時？
    
    // Step1のsplitを1回に減らしたら速くなるのか
    public int numUniqueEmails2_1(String[] emails) {
        Set<String> uniqueEmails = new HashSet<>();

        for (String email : emails) {
            String[] parts = email.split("@");
            String localName = parts[0].split("\\+")[0];
            // 単純な文字列置換であれば、replace()で全置換できる
            String processed = localName.replace(".", "") + "@" + parts[1];
            uniqueEmails.add(processed);
        }

        return uniqueEmails.size();
    }
    
    // 1文字ずつ走査
    public int numUniqueEmails(String[] emails) {
        Set<String> uniqueEmails = new HashSet<>();

        for (String email : emails) {
            String[] parts = email.split("@");

            var processed = new StringBuilder();
            for (char c : parts[0].toCharArray()) {
                if (c == '+') {
                    break;
                }
                if (c == '.') {
                    continue;
                }
                processed.append(c);
            }

            processed.append("@");

            for (char c : parts[1].toCharArray()) {
                processed.append(c);
            }
            
            uniqueEmails.add(processed.toString());
        }

        return uniqueEmails.size();
    }
    
}
