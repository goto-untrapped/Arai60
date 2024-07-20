public class CapacityToShipPackagesWithinDDaysStep2 {
    
    /*
     * 思ったこと
     * ・JavaのbinarySearch()は、要素が見つからなかった場合に-(挿入できるインデックス-1)で返してくるので、
     * 分岐をする必要があり、使いづらい。
     * ・積むのにかかる日にちを返す方が、共通関数として便利そう。でもこの問題だけだったら、ほしいのは最大積載量なので、
     * 積めるかどうかまで関数にした方が分け方としてはきれいそう。
     * ・発想としてしやすいのは、もう0から最大積載量として見ていく。
     * ・二分探索の最小、最大が最後に同じ値を意味するため、こういう時は用途で名前を付けた方が確かによさそう。
     */
}
/*
 * 参考
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1220683396213116979
 *   https://github.com/hayashi-ay/leetcode/pull/55
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1226562256234614855
 *   https://github.com/shining-ai/leetcode/pull/44/files
 * https://discord.com/channels/1084280443945353267/1192736784354918470/1236998475707584593
 *   https://github.com/YukiMichishita/LeetCode/pull/10
 * https://discord.com/channels/1084280443945353267/1225849404037009609/1248632267946070066
 *   https://github.com/SuperHotDogCat/coding-interview/pull/27
 *   ・日数超過するか() のループは、最後に日数が超過しているか見れば、return true/false で返せる
 * https://discord.com/channels/1084280443945353267/1227073733844406343/1255120831701323840
 *   https://github.com/sakupan102/arai60-practice/pull/45
 *   > 一応確認ですが、下の二分探索は境界を探しているんですよね。days = 0 がくるのは考慮しなくていいですかねー。 
 *     ・days = 0 の場合、常にcan_be_shipped()はfalseになり、min_capacityはmax_capacityになると思う。
 *       せめて答えるとしたらその値になるのかなと思うけど、ふつうに見つからなかったとして-1を返すとかした方がよさそう。
 * https://discord.com/channels/1084280443945353267/1235829049511903273/1263398304352567296
 *   https://github.com/fhiyo/leetcode/pull/45
 * ・一見分かりにくい最適化はコメントで残すとよい
 */
