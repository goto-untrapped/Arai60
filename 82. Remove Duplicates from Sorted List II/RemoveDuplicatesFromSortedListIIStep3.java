public class RemoveDuplicatesFromSortedListIIStep3 {
    // 7min / 4m40s / 2min
    public ListNode deleteDuplicates(ListNode head) {
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode node = dummyHead;
        ListNode nextNode = head;
        while (nextNode != null) {
            if (nextNode.next != null && nextNode.val == nextNode.next.val) {
                while (nextNode.next != null && nextNode.val == nextNode.next.val) {
                    nextNode.next = nextNode.next.next;
                }
                node.next = nextNode.next;
            } else {
                node = nextNode;
            }
            nextNode = nextNode.next;
        }
        return dummyHead.next;
    }
    /*
     * 思ったこと
     * ・削除する値を持ち回った方が個人的に分かりやすかったが、流れをうまく切れる方が理解が深まると思い、こちらにした。
     * ・ else {} で node.next = nextNode; node = node.next; と初めにやってしまったが、
     * 既にnodeは更新されていて、そのままhead, dummyHead も更新しているから、node はそのまま nextNode を入れてあげて、nextNode から見始めればいい。
     */
}
