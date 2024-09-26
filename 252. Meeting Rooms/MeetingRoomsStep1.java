public class MeetingRoomsStep1 {
    /*
     * 10m50s
     * 知っていた問題。配列コピーの書き方と2次元配列のソートの書き方は調べた。
     * 配列はn要素持つとして、
     * 時間計算量:O(nlogn)
     * 空間計算量:O(n)
     */
    class Solution {
        public boolean canAttendMeetings(int[][] intervals) {
            int[][] ascStartTimeIntervals = Arrays.copyOf(intervals, intervals.length);
            Arrays.sort(ascStartTimeIntervals, (a, b) -> (a[0] - b[0]));
            for (int i = 1; i < ascStartTimeIntervals.length; i++) {
                if (ascStartTimeIntervals[i - 1][1] <= ascStartTimeIntervals[i][0]) {
                    continue;
                }
                return false;
            }
            return true;
        }
    }
    /*
     * ・コピーは作らなくてもいいけど、入力を変えないことの方が優先度が高いのかなと思った。
     */
}
