public class MeetingRoomsIIStep3 {
    /*
     * 4min
     * 時間計算量:O(nlogn)
     * 空間計算量:O(n)
     */
    class Solution {
        public int minMeetingRooms(int[][] intervals) {
            int[][] ascStartTimeIntervals = Arrays.copyOf(intervals, intervals.length);
            Arrays.sort(ascStartTimeIntervals, (a, b) -> (a[0] - b[0]));
            PriorityQueue<Integer> ascEndTimes = new PriorityQueue<>();
            ascEndTimes.offer(ascStartTimeIntervals[0][1]);
            for (int i = 1; i < ascStartTimeIntervals.length; i++) {
                int nextStartTime = ascStartTimeIntervals[i][0];
                int lastEndTime = ascEndTimes.peek();
                if (lastEndTime <= nextStartTime) {
                    ascEndTimes.poll();
                }
                // end time
                ascEndTimes.offer(ascStartTimeIntervals[i][1]);
            }
            return ascEndTimes.size();
        }
    }
    // 自分が一番分かりやすいと思った方法にした。
}
