public class UniqueEmailAddressesStep4 {
    
    public int numUniqueEmails(String[] emails) {
        Set<String> uniqueEmails = new HashSet<>();

        for (String email : emails) {
            String[] parts = email.split("@", 2);
            String localIgnoreAlias = parts[0].split("\\+")[0];
            String localWithoutDots = localIgnoreAlias.replace(".", "");
            uniqueEmails.add(localWithoutDots + "@" + parts[1]);
        }

        return uniqueEmails.size();
    }
    
}
