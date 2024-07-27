public class RemoveDuplicatesFromSortedListStep1 {
    /*
     * 13min []を考慮せず1回WA
     * 時間計算量：O(n)
     * 空間計算量：O(n)
     */
    public ListNode deleteDuplicates1_1(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode unique = new ListNode(head.val);
        ListNode node = unique;
        ListNode nextNode = head.next;
        while (nextNode != null) {
            if (nextNode.val == node.val) {
                nextNode = nextNode.next;
                continue;
            }
            node.next = new ListNode(nextNode.val);
            node = node.next;
            nextNode = nextNode.next;
        }
        return unique;
    }
    /*
     * 思ったこと
     * ・head を付け替えてもいけそうだけど、間違いを減らすため、頭が楽な方にしようと思った。
     * head をそのまま使えば、空間計算量がO(1)になる。(1_2で書き直し)
     */
    
    
    // 7min 空間計算量：O(1)
    public ListNode deleteDuplicates1_2(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode node = head;
        while (node != null) {
            if (node.next == null) {
                break;
            }
            ListNode nextNode = node.next;
            if (node.val != nextNode.val) {
                node = nextNode;
                continue;
            }
            node.next = node.next.next;
        }
        return head;
    }
    /*
     * 思ったこと
     * ・head をそのまま使った方が頭が楽だった気がする。
     * 新しく定義した方が、ほしい関係だけ追加できて間違いが減ると思っていたが、
     * 返す用のnode と動かす方の node を定義する必要があることに書き始めてから気付いて、思ったのと違った。
     * （方針立て切ってない）
     */
}
