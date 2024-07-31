public class RemoveDuplicatesFromSortedListIIStep2 {
    // 重複あったら重複でないノードを探しに行って、見つけたら繋ぐ(切断しておいて確定したら繋ぐ)
    public ListNode deleteDuplicates2_1(ListNode head) {
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode node = dummyHead;
        ListNode nextNode = node.next;
        while (nextNode != null) {
            if (nextNode.next == null || nextNode.next != null && nextNode.val != nextNode.next.val) {
                node.next = nextNode;
                node = node.next;
                nextNode = nextNode.next;
                continue;
            }
            while (nextNode.next != null && nextNode.val == nextNode.next.val) {
                nextNode = nextNode.next;
            }
            nextNode = nextNode.next;
        }
        node.next = nextNode;
        return dummyHead.next;
    }
    /*
     * 思ったこと
     * ・やりたいことがまとめてかかれていないし対称的にも書かれていないので、何やってるのか分からなくて読みにくそう。
     *   似ている流れを切り分けて処理できていない感じがする。
     * ・sentinel(番兵)という言葉になじみがなく、dummyHead の方が分かりやすかった。
     * 
     * 2_1を書き直し
     */
    public ListNode deleteDuplicates2_2(ListNode head) {
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode node = dummyHead;
        ListNode nextNode = head;
        int removingValue = -101;
        while (nextNode != null) {
            while (nextNode != null && nextNode.val == removingValue) {
                nextNode = nextNode.next;
            }
            if (nextNode != null && nextNode.next != null && nextNode.val == nextNode.next.val) {
                removingValue = nextNode.val;
                continue;
            }
            node.next = nextNode;
            node = node.next;
            if (nextNode == null) {
                break;
            }
            nextNode = nextNode.next;
        }
        return dummyHead.next;
    }
    /*
     * 思ったこと
     * ・次と比べずに値で比べれば同じノードはすべて飛ばせる
     * ・途中で null でないか確認する必要があるのはまだ流れをきれいにできる余地があるからだと思う
     * 
     * 2_2を書き直し
     */
    public ListNode deleteDuplicates2_3(ListNode head) {
        ListNode dummyHead = new ListNode();
        ListNode node = dummyHead;
        ListNode nextNode = head;
        int deletingValue = -101;
        while (nextNode != null) {
            if (nextNode.val == deletingValue) {
                nextNode = nextNode.next;
                continue;
            }
            if (nextNode.next != null && nextNode.val == nextNode.next.val) {
                deletingValue = nextNode.val;
                continue;
            }
            node.next = new ListNode(nextNode.val);
            node = node.next;
            nextNode = nextNode.next;
        }
        return dummyHead.next;
    }
    /*
     * 思ったこと
     * ・最終的に書きたかった形がこれだった。が、newでノード追加したり、reutrn dummy.next で[1,1,1]の場合に対応していたりと、
     * やっぱりパターン網羅するために気を遣う必要がある。
     */
    
    
    // 重複あったら初めて見るノードはとりあえず繋いで、重複でない場合は更新しながら繋ぐ(繋いでおいて確定したら切る)
    public ListNode deleteDuplicates2_4(ListNode head) {
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
     * ・ポインタ2つ動いている感じなのか。nextNodeとnode
     * ・処理が複雑な印象を持った。
     *   ・nodeには重複ないノードを入れる、node.nextは仮置き、nextNodeは重複が終わった後の最初のノードを入れつつ、次のノードとの重複確認のためにも再代入されている、たちが
     *     お互いに影響しながら動いている感じがする。Iを複雑にしたものだろうから、それくらいのまとまりはできるということなのかな。
     */
    
    
    // 1ターンやること1つ
    public ListNode deleteDuplicates2_5(ListNode head) {
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode previous = dummyHead;
        ListNode current = previous.next;
        boolean isDeleting = false;
        int deletingValue = -101;
        while (current != null) {
            if (isDeleting && current.val == deletingValue) {
                previous.next = current.next;
                current = current.next;
                continue;
            }
            isDeleting = false;
            if (current.next == null) {
                previous = current;
                current = current.next;
                continue;
            }
            if (current.val != current.next.val) {
                previous = current;
                current = current.next;
                continue;
            }
            isDeleting = true;
            deletingValue = current.val;
            previous.next = current.next;
            current = current.next;
        }
        return dummyHead.next;
    }
    /*
     * 2_5 を縮める
     */
    public ListNode deleteDuplicates2_6(ListNode head) {
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode previous = dummyHead;
        ListNode current = previous.next;
        boolean isDeleting = false;
        int deletingValue = -101;
        while (current != null) {
            boolean needDeleting = false;
            if (isDeleting && current.val == deletingValue) {
                needDeleting = true;
            } else if (current.next == null || current.val != current.next.val) {
                needDeleting = false;
            } else {
                deletingValue = current.val;
                needDeleting = true;
            }
            if (needDeleting) {
                isDeleting = true;
                previous.next = current.next;
                current = current.next;
                continue;
            }
            isDeleting = false;
            previous = current;
            current = current.next;
        }
        return dummyHead.next;
    }
    /*
     * 思ったこと
     * ・これぐらいが一番見やすい気がした。
     * または if (needDeleting) {} の処理を関数化して needDeleting フラグを除くとか。
     */
    
    
    // 再帰 
    // 自分のノードが削除対象か、自分と次のノードが同じ場合、もらったノードをそのまま返す。そうでなければもらったノードを自分の次に付けて返す。
    class Solution2_7 {
        public ListNode deleteDuplicates(ListNode head) {
            return deleteDuplicatesHelper(head, -101);   
        }

        private ListNode deleteDuplicatesHelper(ListNode node, int deletingValue) {
            if (node == null) {
                return null;
            }
            if (node.val == deletingValue) {
                return deleteDuplicatesHelper(node.next, deletingValue);
            } else if (node.next != null && node.val == node.next.val) {
                return deleteDuplicatesHelper(node.next, node.val);
            } else {
                node.next = deleteDuplicatesHelper(node.next, -101);
                return node;
            }
        }
    }
    /*
     * 思ったこと
     * ・前と自分と後をやっぱり見ないといけないか。
     */
    
}
/*
 * kuzuharaさん
 * https://discord.com/channels/1084280443945353267/1183683738635346001/1183691370683174973
 * ・while２重で全ノードを回しながら、今のノードの中で、次のノードになる場所を探して今のノードに付ける
 * ・while１重でforループをして、重複でないノードを次のノードとして指す
 * 
 * hardjuiceさん
 * https://discord.com/channels/1084280443945353267/1195700948786491403/1196681161498447982
 * ・重複したら取り除いて、取り除いき途中を分かるようにすれば、そこで作業を終わらせて次のループでやることができる
 * ・重複を見つけたら、すべて取り除くまでループする(while２重)
 * 
 * YukiMichishitaさん
 * https://github.com/YukiMichishita/LeetCode/pull/1
 * ・次のノードが、取り除いている途中の値と一致するか、取り除いている途中の値とは一致しないけど
 * 次の次のノードと一致する場合は取り除いている途中の値を更新して、次のノードを見る、ようにして次に付けるノードを決める
 * 
 * ahayashiさん
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1206627040241918003
 *   https://github.com/hayashi-ay/leetcode/pull/23
 * ・while２重 色んな流れ、再帰
 * 
 * ryu886833さん
 * https://discord.com/channels/1084280443945353267/1227073733844406343/1228549570842329138
 *   https://github.com/sakupan102/arai60-practice/pull/5
 * ・> 次、次のやつになる候補は、切断しておいて確定したら繋ぐ方法と、繋いでおいて確定したら切る方法があるでしょう。
 * 
 * torusさん
 * https://discord.com/channels/1084280443945353267/1231966485610758196/1236735853812908103
 *   https://github.com/TORUS0818/leetcode/pull/6
 * ・重複があるかないか両方を同時に進める
 * 
 * https://discord.com/channels/1084280443945353267/1226508154833993788/1236545958595006506
 *   https://github.com/nittoco/leetcode/pull/9
 * https://discord.com/channels/1084280443945353267/1235829049511903273/1236872447702073364
 *   https://github.com/fhiyo/leetcode/pull/4
 * https://discord.com/channels/1084280443945353267/1245404801177616394/1249267174091132929
 *   https://github.com/seal-azarashi/leetcode/pull/4
 */
