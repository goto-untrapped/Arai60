/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class LinkedListCycleIIStep3 {
    public ListNode detectCycle(ListNode head) {
        HashSet<ListNode> existed = new HashSet<>();
        ListNode node = head;

        while (node != null) {
            if (existed.contains(node)) {
                return node;
            }
            existed.add(node);
            node = node.next;
        }
        return null;
    }
}
