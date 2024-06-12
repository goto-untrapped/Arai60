public class ReverseLinkedListStep3 {
    // 2min
    public ListNode reverseList(ListNode head) {
        ListNode node = head;
        ListNode prev = null;
        while (node != null) {
            ListNode originalNext = node.next;
            node.next = prev;
            prev = node;
            node = originalNext;
        }
        return prev;
    }
}
