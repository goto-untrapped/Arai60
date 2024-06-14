public class UniqueEmailAddressesStep4_2 {
    
    /*
     * 修正点
     * ・全体的な処理の流れを、見る負荷が減るように修正
     */
    public int numUniqueEmails(String[] emails) {
        Set<String> validEmails = new HashSet<>();

        for (String email : emails) {
            StringBuilder composed = new StringBuilder();
            boolean isLocalParts = true;
            boolean isIgnoreAlias = false;

            for (char c : email.toCharArray()) {
                if (!isLocalParts) {
                    composed.append(c);
                    continue;
                }
                if (c == '@') {
                    composed.append('@');
                    isLocalParts = false;
                    continue;
                }
                if (c == '+') {
                    isIgnoreAlias = true;
                    continue;
                }
                if (isIgnoreAlias || c == '.') {
                    continue;
                }
                composed.append(c);
            }
            validEmails.add(composed.toString());
        }
        
        return validEmails.size();
    }
    
    // 最初に書いたもの
    public int numUniqueEmails_v1(String[] emails) {
        Set<String> validEmails = new HashSet<>();

        for (String email : emails) {
            StringBuilder composed = new StringBuilder();
            boolean isLocalParts = true;
            boolean isIgnoreAlias = false;

            for (char c : email.toCharArray()) {
                if (c == '@') {
                    composed.append('@');
                    isLocalParts = false;
                    continue;
                }
                // ドメインパーツであれば代入するだけだから、こっちの方が頻度が高いわりに処理が軽いので、
                // @の切替処理より前に置いた方が見やすいということなのかな
                if (!isLocalParts) {
                    composed.append(c);
                    continue;
                }
                // if (isLocalParts)いらないな、ドメインパート入らなかったら勝手にここに辿り着くので
                // if (!isIgnoreAlias)もいらないな、2回目以降もtrueをtrueで更新すればいい、わざわざ書いて注意を促すほどじゃないってことかな
                if (isLocalParts && !isIgnoreAlias && c == '+') {
                    isIgnoreAlias = true;
                    continue;
                }
                // +より後は、条件が切り替わったと考える
                // そうすると、.が来た場合も、+より後の文字が来た場合も、同じく無視すればいいことが分かる
                // なので、同じ条件でまとめられる
                if (isLocalParts && c == '.') {
                    continue;
                }
                // ドメインのパーツでもなく、+より後の文字でもなく、.でもないので、ただ入れればよいとなる
                if (!isIgnoreAlias) {
                    composed.append(c);
                    continue;
                }
            }
            validEmails.add(composed.toString());
        }

        return validEmails.size();
    }
    
}