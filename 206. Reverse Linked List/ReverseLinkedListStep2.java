import java.util.Stack;

class ReverseLinkedListStep2 {
    /*
     * Step1をもっと簡潔に書く(Iterative)
     * ループはnextを見ずに、nodeで直接判定できる。
     * ループ内でnode.nextを使うため、そのまま判定で使えばよい
     */
    public ListNode reverseList2_1(ListNode head) {
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

    /*
     * Recursive
     * head: 1->2->null とする
     * head.next.next = head; とした場合、
     *   head: 1->2->1->2->1->...
     *   p:       2->1->2->1->...
     * head.next = null; とした場合、
     *   head: 1->null->1->null->...
     *   p:          2->1->null->...
     * なぜはじめの2がnullにならないか、下記のように考えた。
     * 循環させた時、すべての1は次のnodeに2を持つ
     * head.next = null としたとき、すべての1の次のnodeはnullになる
     * 2を指す元がなくなるので、ないに等しくなる
     * ただし、pはheadのnextから持ち始めるので、最初の2を指す元が存在しないため、nullにならない。
     * 循環させた時にできた関係である 1->2->null の部分のみが影響を受けて、1->null になる。
     * そのため、2->1->nullになることができる。
     * あーこう動くからいけそうだね、とトレースするの、難しい。
     */
    public ListNode reverseList2_2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = reverseList2_2(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
    
    // https://github.com/nittoco/leetcode/pull/10/files
    public ListNode reverseList2_3(ListNode head) {
        return getReversedList(null, head);
    }
    private ListNode getReversedList(ListNode reversedNode, ListNode currentNode) {
        if (currentNode == null) {
            return reversedNode;
        }
        ListNode nextNode = currentNode.next;
        currentNode.next = reversedNode;
        return getReversedList(currentNode, nextNode);
    }
    
    /*
     * スタックを使う
     * https://discord.com/channels/1084280443945353267/1195700948786491403/1198919632908730488
     * 1h
     * 下記のように書いて、どっちみち動かなかったが、ロジックの誤り以前に思ったように動かなかった
       ListNode tail = new ListNode(-1);
       ListNode reversed = tail;
       while (fromHead.size() > 0) {
           ListNode poppedTail = fromHead.pop();
           tail = poppedTail;
           tail = tail.next;
       }
       return reversed;
     * tail = poppedTailしたら、reversedもpoppedTailに更新されると思っていた
     * tail.next や tail.valでreversedも変わるのは、reversedが持つのはtailと同じメモリを指す値だからだった
     * なので、tailの持つメモリの値自体が更新される時、reversedは変わらない
     * int a = 1; int b = a; a = 1; をしてもbは変わらない・・・
     */
    public ListNode reverseList2_4(ListNode head) {
        ListNode node = head;
        Stack<ListNode> fromHead = new Stack<>();
        while (node != null) {
            fromHead.push(node);
            node = node.next;
        }

        ListNode tail = new ListNode();
        ListNode reversed = tail;
        while (fromHead.size() > 0) {
            ListNode poppedTail = fromHead.pop();
            tail.next = poppedTail;
            tail = poppedTail;
            /*
             * この行を書かないとFound cycle error
             * Python3だとサイクルを含んでいても問題ないようだ
             * LeetCode側でJavaとPythonでエラーチェックが違うのかもしれない
             * L138-L155 https://github.com/hayashi-ay/leetcode/pull/13/files
             */
            poppedTail.next = null;
        }
        return reversed.next;
    }
    
    /*
     * 定義してあるコンストラクタを使う
     * 15min
     * https://github.com/sakupan102/arai60-practice/pull/8/files
     * 定義してあることに気付かなかった。ちゃんと読もう。
     * */
    public ListNode reverseList2_5(ListNode head) {
        ListNode node = head;
        ListNode reversed = null;
        while (node != null) {
            reversed = new ListNode(node.val, reversed);
            node = node.next;
        }
        return reversed;
    }
    
    /*
     * https://github.com/hayashi-ay/leetcode/pull/13/files
     * https://github.com/cheeseNA/leetcode/pull/11
     * https://github.com/sakupan102/arai60-practice/pull/8
     * https://github.com/nittoco/leetcode/pull/10/files
     * https://github.com/TORUS0818/leetcode/pull/9/files
     */
}

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
