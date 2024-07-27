public class RemoveDuplicatesFromSortedListStep2 {
    /*
     * もっとシンプルにに付け替える
     * 再帰
     *   行く時、自分と自分の次を見比べて、重複なくして次の人に渡す
     *   戻ってきた時、戻ってきたノードと自分を見比べて、重複なくして前の人に渡す  ができそう？
     * Stack（ループ）
     */
    // もっとシンプルに付け替える
    public ListNode deleteDuplicates2_1(ListNode head) {
        ListNode node = head;
        while (node != null) {
            while (node.next != null && node.val == node.next.val) {
                node.next = node.next.next;
            }
            node = node.next;
        }
        return head;
    }
    
    
    // 再帰 行く時、自分と自分の前を見比べて、重複なくして次の人に渡す
    class Solution2_2 {
        public ListNode deleteDuplicates(ListNode head) {
            deleteDuplicatesHelper(head);
            return head;
        }

        private void deleteDuplicatesHelper(ListNode bossNode) {
            if (bossNode == null) {
                return;
            }
            ListNode myNode = bossNode.next;
            if (myNode == null) {
                return;
            }
            if (bossNode.val == myNode.val) {
                bossNode.next = bossNode.next.next;
                deleteDuplicatesHelper(bossNode);
            } else {
                deleteDuplicatesHelper(myNode);
            }
        }
    }
    /*
     * 思ったこと
     * ・はじめ、自分と自分の次を比べて重複をなくすように書いていたが、
     * [1,1,1]のように、自分の前と比べないと重複をなくせない場合があることにWAして気付いた。
     * ・bossNode, myNode という名前はイメージに偏りがあると思ったけど、
     * 自分の中で流れを整理するためにはそう書かないと分からなさそうだった。
     * プロダクションであれば直す。bossNode -> node, myNode -> currentNode とか。
     * ・書く前は、渡した後に戻ってこれるように書けると思ったけど、行ったきりになってしまった。
     * 
     * 末尾再帰について
     * ・https://discord.com/channels/1084280443945353267/1235829049511903273/1236286599910653993
     */
    
     
    // 再帰 戻ってきた時、戻ってきたノードと自分を見比べて、重複なくして前の人に渡す
    class Solution2_3 {
        public ListNode deleteDuplicates(ListNode head) {
            return deleteDuplicatesHelper(head);
        }

        private ListNode deleteDuplicatesHelper(ListNode node) {
            if (node == null) {
                return node;
            }
            ListNode followingNode = deleteDuplicatesHelper(node.next);
            if (followingNode == null) {
                return node;
            }
            if (node.val == followingNode.val) {
                node.next = node.next.next;
            } 
            return node;
        }
    }
    /*
     * 思ったこと
     * ・2_1 より時間がかかった(8min -> 15min)のは、
     * 戻ってくる時の処理を込みで考える 考え方だったからだと思ってる。
     * ・followingNode という変数名微妙だけど、まだイメージできた。returnedNodeとか。
     */
    
    
    // Stack（ループ）
    public ListNode deleteDuplicates2_4(ListNode head) {
        Stack<ListNode> nodes = new Stack<>();
        nodes.push(head);
        while (!nodes.isEmpty()) {
            ListNode node = nodes.pop();
            if (node == null || node.next == null) {
                continue;
            }
            ListNode nextNode = node.next;
            while (node.val == nextNode.val) {
                nextNode = nextNode.next;
                if (nextNode == null) {
                    break;
                }
            }
            node.next = nextNode;
            nodes.push(nextNode);
        }
        return head;
    }
    
}
/*
 * 参考
 * https://discord.com/channels/1084280443945353267/1195700948786491403/1196388760275910747
 * ・もう while (node.next) で回せば中身だけ見ればいいよね
 *   https://discord.com/channels/1084280443945353267/1195700948786491403/1196399353116499970
 *   ・ while () の条件に中身の値の判定も入れられるよね
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1206126591277670450
 * https://discord.com/channels/1084280443945353267/1227073733844406343/1227803864372219955
 * ・確かに再帰で書けそう
 * https://discord.com/channels/1084280443945353267/1227073733844406343/1227803864372219955
 * https://discord.com/channels/1084280443945353267/1227073733844406343/1228331716620582943
 * https://discord.com/channels/1084280443945353267/1231966485610758196/1234171248934785055
 * https://discord.com/channels/1084280443945353267/1262688866326941718/1263475874871906334
 * https://discord.com/channels/1084280443945353267/1251551866081509520/1263117677149294703
 * ・再帰は違う値がぶら下がっていればそのまま先頭を返せばいい、同じ値なら永遠にその中の末尾のノードを戻し続ける
 * https://discord.com/channels/1084280443945353267/1196472827457589338/1263062119893696615
 * https://discord.com/channels/1084280443945353267/1261259843671687188/1262997002891821056
 * ・再帰の考え方について
 * https://discord.com/channels/1084280443945353267/1206101582861697046/1258063569577054319
 * ・再帰、行きがてら
 * https://discord.com/channels/1084280443945353267/1247673286503039020/1249760751204434024
 * https://discord.com/channels/1084280443945353267/1245404801177616394/1249229725314842675
 * ・Javaでのループ、再帰、参考になる
 * https://discord.com/channels/1084280443945353267/1239148130679783424/1242507874119454731
 * https://discord.com/channels/1084280443945353267/1239148130679783424/1242402162034868234
 */
