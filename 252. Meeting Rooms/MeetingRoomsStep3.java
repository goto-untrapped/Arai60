public class MeetingRoomsStep3 {
    // 6min
    class Solution {
        public boolean canAttendMeetings(int[][] intervals) {
            int[][] ascStartTimeIntervals = Arrays.copyOf(intervals, intervals.length);
            Arrays.sort(ascStartTimeIntervals, (a, b) -> (a[0] - b[0]));
            for (int i = 1; i < ascStartTimeIntervals.length; i++) {
                if (!(ascStartTimeIntervals[i - 1][1] <= ascStartTimeIntervals[i][0])) {
                    return false;
                }
            }
            return true;
        }
    }
    /*
     * はじめ、下記のように書いてMemoryLimitExceededした。
     *   int[][] ascStartTimeIntervals = new int[intervals.length][intervals.length];
         ascStartTimeIntervals = Arrays.copyOf(intervals, intervals.length);
       ⇒１行目で配列を作って、２行目で別の配列を作っていた。
     */
}
