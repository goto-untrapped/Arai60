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
    /*
     * 時間計算量：O(n)
     * 出会わなかったらO(n/2)=O(n)、
     * 出会う場合、サイクルに入るまで最大は、slowのO(n)、
     * 入ってから出会うまでが最大は、1ずつ縮まるのでO(n)、
     * なのでO(n)+O(n)=O(n)
     * 空間計算量：O(1)
     */
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
}
