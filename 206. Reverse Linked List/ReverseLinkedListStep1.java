public class ReverseLinkedListStep1 {
    /*
     * 解き直し、30分かけて書いた後、デバッグしてAC： return head; と書いて、WA
     * node.nextを直接変えた時、headも変化するが、
     * その後、nodeにはループ中で新しく定義したoriginalNextを代入するため、nodeは更新される
     * しかしnodeが再代入されたとき、headは再代入されない。
     * なので、headは捨てられたまま、nodeが作りたい動きを実現することになる。
     * 変数の命名時、nodeはheadの中身を変えるための一時要員として動かしたかったため、
     * 作りたい動きと実際の動きが異なってしまった。
     * 時間計算量はO(headの長さ)
     * 空間計算量はO(1)
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) return null;
        
        ListNode node = head;
        ListNode prev = null;

        while (node.next != null) {
            ListNode originalNext = node.next;
            node.next = prev;
            prev = node;
            node = originalNext;
        }
        node.next = prev;

        return node;
    }
}
