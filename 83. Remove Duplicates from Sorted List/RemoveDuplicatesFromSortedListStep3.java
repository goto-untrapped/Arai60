public class RemoveDuplicatesFromSortedListStep3 {
    // 7m20s / 1m30s / 2m30s
    public ListNode deleteDuplicates(ListNode head) {
        ListNode node = head;
        while (node != null) {
            ListNode nextNode = node.next;
            while (nextNode != null && node.val == nextNode.val) {
                nextNode = nextNode.next;
            }
            node.next = nextNode;
            node = nextNode;
        }
        return head;
    }
}
