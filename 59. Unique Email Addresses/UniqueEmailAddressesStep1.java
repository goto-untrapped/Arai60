public class UniqueEmailAddressesStep1 {
    
    // 38ms / 45.49MB 
    // 遅いしメモリ使いすぎてる
    // 時間計算量：O(n)× ⇒ O(n * m) 
    // replaceでO(m)かかる メールアドレスの平均長がmの場合 nはメールアドレスの数
    // 空間計算量：O(n)× ⇒ O(n * m) メールアドレスは長さを持っている。メールアドレスがすべてユニークの場合。
    public int numUniqueEmailsA(String[] emails) {
        Set<String> uniqueEmails = new HashSet<>();

        for (String email : emails) {
            String localName = email.split("@")[0];
            String domainName = email.split("@")[1];
            localName = localName.replaceAll("\\.", "");
            localName = localName.replaceAll("\\+.*", "");
            String processed = localName + "@" + domainName;
            uniqueEmails.add(processed);
        }

        return uniqueEmails.size();
    }
    
}
