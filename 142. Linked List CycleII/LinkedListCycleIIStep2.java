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
public class LinkedListCycleIIStep2 {
    /*
     * Setを使う。
     * 含んでいるか確認するだけだから、Listでもいいのかなと思って試したら、
     * 3msが310msになった。
     * 調べたところ、contains()がArrayListはHashSetに比べて遅いことが分かった。
     * 内部実装をGPTと確認し、下記の理由で速度に差があると理解した。
     * ・HashSetはハッシュテーブルを使ってkeyを探す。1つのハッシュキーにn集まる可能性は低いはずのため、O(1)で見つけられそう。
     * ・ArrayListはループを回して一致する要素を探す。なので遅い。元々配列を拡張できるように作ったデータ構造。
     * https://stackoverflow.com/questions/15070104/arraylist-and-hashset-insert-performance-test-result-confuse-me
     * https://www.baeldung.com/java-hashset-arraylist-contains-performance
     */
    public ListNode detectCycle(ListNode head) {
        HashSet<ListNode> found = new HashSet<>();
        ListNode node = head;

        while (node != null) {
            if (found.contains(node)) {
                return node;
            }
            found.add(node);
            node = node.next;
        }
        return null;
    }
}
