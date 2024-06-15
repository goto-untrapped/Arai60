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
public class Solution {
    // setで判別する
    public boolean hasCycle(ListNode head) {
        HashSet<ListNode> found = new HashSet<ListNode>();
        ListNode node = head;

        while (node != null) {
            if (found.contains(node)) {
                return true;
            }
            found.add(node);
            node = node.next;
        }
        return false;
    }
}
