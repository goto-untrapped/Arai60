public ListNode deleteDuplicates(ListNode head) {
    ListNode dummyHead = new ListNode();
    dummyHead.next = head;
    ListNode node = dummyHead;
    while (node != null && node.next != null && node.next.next != null) {
        if (node.next.val != node.next.next.val) {
            node = node.next;
            continue;
        }
        int value = node.next.val;
        while (node.next != null && node.next.val == value) {
            node.next = node.next.next;
        }
    }
    return dummyHead.next;
}
