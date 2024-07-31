public class RemoveDuplicatesFromSortedListIIStep1 {
    // 38min 方針立て20分
    // 2回デバッグ:
    // ・HashSet<ListNode> で contains 重複検知できず
    // ・newHead.next の != null 確認忘れ 
    public ListNode deleteDuplicates(ListNode head) {
        HashSet<Integer> all = new HashSet<>();
        HashSet<Integer> duplicates = new HashSet<>();
        ListNode node = head;
        while (node != null) {
            if (all.contains(node.val)) {
                duplicates.add(node.val);
            }
            all.add(node.val);
            node = node.next;
        }

        ListNode newHead = head;
        while (newHead != null && duplicates.contains(newHead.val)) {
            newHead = newHead.next;
        }

        node = newHead;
        while (node != null) {
            ListNode nextNode = node.next;
            while (nextNode != null && duplicates.contains(nextNode.val)) {
                nextNode = nextNode.next;
            }
            node.next = nextNode;
            node = nextNode;
        }
        return newHead;
    }
    
    /*
     * 思ったこと
     * ・解き方、問題文の意図と違ったな。どう繋ぎ変えるかという話か。
     */
}
