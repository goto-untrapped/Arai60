class Solution {
    public ListNode addTwoNumbersStep4(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode curr = dummyHead;
        int carry = 0;
        while (l1 != null || l2 != null || carry == 1) {
            int x = 0;
            if (l1 != null) {
                x = l1.val;
                l1 = l1.next;
            }

            int y = 0;
            if (l2 != null) {
                y = l2.val;
                l2 = l2.next;
            }

            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;

        }
        return dummyHead.next;
    }
}
