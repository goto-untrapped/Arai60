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
public class LinkedListCycleIIStep1 {
  /**
   * 出会った後に、slowを最初から戻してslowとfastを1つずつ進めたら出会う場所がループの始まりになる理由：
   * ループの入り口までaの距離、ループの中で出会う点からループ入り口までの距離をbとし、またループ全体の距離をc、
   * slowとfastが出会うまでにfastがループをk周したとする。
   * slowとfastが1回目に出会う時、slowがa + b 進んでいるとすると、fast は a + b + kc 進んでいる。
   * slowの距離を2倍にしたらfastと同じになるため、 2(a + b) = a + b + kc となり、
   * kc = a + bとなる。...(1)
   * もしslowを最初に戻した時、a進んでループ入り口に着いたときの距離は、上式よりa = kc - b
   * slowがa進んだとき、fastが進んだ距離は、
   * a + b = kcより、(kc - b) + b = kc となる。
   * よってちょうど周回する地点にfastは辿り着き、この場所でaと合流するということは、ループの入り口になる。
   * もしくは、(1)の式が成り立つ時、片方をはじめに戻したら、a進んだときの距離と合流地点からループ入り口まで進んだときの
   * 距離bの和がそのまま周回する長さと同じ、かつ出会うことができるということは、ループの合流地点、と考えられそう。
   */
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (true) {
            if (fast == null || fast.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                break;
            }
        }

        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
} 
