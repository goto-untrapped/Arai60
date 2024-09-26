public class MeetingRoomsStep2 {
    // 総当たり
    class Solution2_1 {
        public boolean canAttendMeetings(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> (Integer.compare(a[0], b[0])));
            for (int i = 0; i < intervals.length; i++) {
                for (int j = i + 1; j < intervals.length; j++) {
                    if (isTimeCrossing(intervals[i], intervals[j])) {
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean isTimeCrossing(int[] before, int[] after) {
            return after[0] < before[1];
        }
    }
    /*
     * ・間に合うのか。
     */
    
    
    // 総当たり。重なっている判定を見た目のまま判定
    class Solution2_2 {
        public boolean canAttendMeetings(int[][] intervals) {
            for (int i = 0; i < intervals.length; i++) {
                for (int j = i + 1; j < intervals.length; j++) {
                    if (isOverlap(intervals[i], intervals[j])) {
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean isOverlap(int[] interval1, int[] interval2) {
            return interval1[0] <= interval2[0] && interval2[0] < interval1[1]
                    || interval2[0] <= interval1[0] && interval1[0] < interval2[1];
        }
    }
    /*
     * ・これも間に合う。
     */
    
    
    // 予約のある時間を埋めていく
    // https://github.com/shining-ai/leetcode/pull/55
    class Solution2_3 {
        public boolean canAttendMeetings(int[][] intervals) {
            Set<Integer> attendTimes = new HashSet<>();
            for (int[] interval : intervals) {
                for (int time = interval[0]; time < interval[1]; time++) {
                    if (attendTimes.contains(time)) {
                        return false;
                    }
                    attendTimes.add(time);
                }
            }
            return true;
        }
    }
    
    
    // 1つ前の終了時間がと比べられればよい
    class Solution2_4 {
        public boolean canAttendMeetings(int[][] intervals) {
            if (intervals.length == 0) {
                return true;
            }
            Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));
            int lastEndTime = intervals[0][1];
            for (int i = 1; i < intervals.length; i++) {
                int[] interval = intervals[i];
                if (lastEndTime <= interval[0]) {
                    lastEndTime = interval[1];
                    continue;
                }
                return false;
            }
            return true;
        }
    }
    /*
     * ・書き方が回りくどかった。
     */
    // Solution2_4を修正
    // https://github.com/hayashi-ay/leetcode/pull/59/files
    class Solution2_5 {
        public boolean canAttendMeetings(int[][] intervals) {
            if (intervals.length == 0) {
                return true;
            }
            Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));
            int lastEndTime = -1;
            for (int[] interval : intervals) {
                if (interval[0] < lastEndTime) {
                    return false;
                }
                lastEndTime = interval[1];
            }
            return true;
        }
    }
    
    
    // heapを使って処理
    class Solution2_6 {
        public boolean canAttendMeetings(int[][] intervals) {
            PriorityQueue<int[]> intervalQueue = new PriorityQueue<>((a, b) -> (a[0] - b[0]));
            for (int[] interval : intervals) {
                intervalQueue.add(interval);
            }
            int lastEndTime = -1;
            while (!intervalQueue.isEmpty()) {
                int[] interval = intervalQueue.poll();
                if (interval[0] < lastEndTime) {
                    return false;
                }
                lastEndTime = interval[1];
            }
            return true;
        }
    }
    
    
    // インターバルを始まりと終わりで分ける
    class Solution2_7 {
        public boolean canAttendMeetings(int[][] intervals) {
            int[] startTimes = new int[intervals.length];
            int[] endTimes = new int[intervals.length];
            for (int i = 0; i < intervals.length; i++) {
                startTimes[i] = intervals[i][0];
                endTimes[i] = intervals[i][1];
            }
            Arrays.sort(startTimes);
            Arrays.sort(endTimes);
            for (int i = 1; i < intervals.length; i++) {
                if (endTimes[i - 1] > startTimes[i]) {
                    return false;
                }
            }
            return true;
        }
    }
}
/*
 * https://leetcode.com/problems/meeting-rooms/editorial/
 *   愚直に総当たり(1通り)
 * https://discord.com/channels/1084280443945353267/1200089668901937312/1221030682168659978
 *   https://github.com/hayashi-ay/leetcode/pull/59
 * https://github.com/shining-ai/leetcode/pull/55
 * https://github.com/Mike0121/LeetCode/pull/27
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/60
hayashiさん Step1
1つ前の終了時間だけ毎回比べられればいいよね(１通り)

hayashiさん Step2
heapに突っ込んで順番を整える(１通り)
途中で新しい要素を入れる必要があっても、追加時の計算量が
ソートだとO(nlogn)かかってしまうが、heapだとO(logn)でできるため、ソートより便利そう

shingingさん Step1
会議予定のある時間を記録していってかぶったら会議に間に合わない(１通り)

Yoshikiさん Step3 
Odaさん コメント
始まりと終わりの時間でそれぞれまとめてソートして比較するやり方もできる(１通り)
 */
