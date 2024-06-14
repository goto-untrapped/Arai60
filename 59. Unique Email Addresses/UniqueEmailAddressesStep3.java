public class UniqueEmailAddressesStep3 {
    
    public int numUniqueEmails(String[] emails) {
        Set<String> uniqueEmails = new HashSet<>();

        for (String email : emails) {
            String[] parts = email.split("@");
            String localName = parts[0].split("\\+")[0];
            uniqueEmails.add(localName.replace(".","") + "@" + parts[1]);
        }

        return uniqueEmails.size();
    }
    
}
