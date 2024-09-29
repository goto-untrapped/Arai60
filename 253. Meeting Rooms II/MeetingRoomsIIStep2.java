public class MeetingRoomsIIStep2 {
    
    // 公式1 開始時間で比較して、終了時間だけ早い順に保持する
    // そうすれば、最初より遅いものは最初の会議室を使えることになる
    class Solution2_1 {
        public int minMeetingRooms(int[][] intervals) {
            Arrays.sort(intervals, (a, b) -> (a[0] - b[0]));
            PriorityQueue<Integer> ascEndTimes = new PriorityQueue<>((a, b) -> (a - b));
            ascEndTimes.offer(intervals[0][1]);
            for (int i = 1; i < intervals.length; i++) {
                int nextStartTime = intervals[i][0];
                int earlistEndTime = ascEndTimes.peek();
                if (earlistEndTime <= nextStartTime) {
                    ascEndTimes.poll();
                }
                // end time
                ascEndTimes.offer(intervals[i][1]);
            }
            return ascEndTimes.size();
        }
    }
    

    // 公式2 開始時間と終了時間を分けて、終了時間より前の開始時間が
    // 来たら会議室を増やす
    class Solution2_2 {
        public int minMeetingRooms(int[][] intervals) {
            int[] startTimes = new int[intervals.length];
            int[] endTimes = new int[intervals.length];
            for (int i = 0; i < intervals.length; i++) {
                startTimes[i] = intervals[i][0];
                endTimes[i] = intervals[i][1];
            }
            Arrays.sort(startTimes);
            Arrays.sort(endTimes);

            int numRequiredRooms = 0;
            int startTimeIndex = 0;
            int endTimeIndex = 0;
            while (startTimeIndex < startTimes.length) {
                int startTime = startTimes[startTimeIndex];
                int endTime = endTimes[endTimeIndex];
                if (startTime < endTime) {
                    startTimeIndex++;
                    numRequiredRooms++;
                } else {
                    startTimeIndex++;
                    endTimeIndex++;
                }
            }
            return numRequiredRooms;
        }
    }
    /*
     * ・時系列に並べる意味の理解が曖昧で何回も書き直して何とか意味が分かった気がする。
     */
    

    // 始まりと終わりの時間を全部1列に並べればいい
    // https://github.com/hayashi-ay/leetcode/pull/62/files
    class Solution2_3 {
        enum TimeType { 
            Start(2), End(1);
            private final int value;

            TimeType(int value) {
                this.value = value;
            }

            private int getValue() {
                return value;
            }
        }
        record TimeTypeAndTime(TimeType timeType, int time) {}

        public int minMeetingRooms(int[][] intervals) {
            List<TimeTypeAndTime> timeTypeAndTimes = new ArrayList<>();
            for (int[] interval : intervals) {
                // can use an exist room when start time = end time
                timeTypeAndTimes.add(new TimeTypeAndTime(TimeType.End, interval[1]));
                timeTypeAndTimes.add(new TimeTypeAndTime(TimeType.Start, interval[0]));
            }
            Comparator<TimeTypeAndTime> ascTimeComparator = Comparator
                .comparing(TimeTypeAndTime::time);
            Comparator<TimeTypeAndTime> ascTimeTypeComparator = Comparator
                .comparing(timeTypeAndTime -> timeTypeAndTime.timeType().getValue());
            List<TimeTypeAndTime> sortedTimeTypeAndTimes = timeTypeAndTimes.stream()
                .sorted(ascTimeComparator.thenComparing(ascTimeTypeComparator))
                .collect(Collectors.toList());
            
            int numUsedRooms = 0;
            int numRequiredRooms = 0;
            for (TimeTypeAndTime timeTypeAndTime : sortedTimeTypeAndTimes) {
                if (timeTypeAndTime.timeType == TimeType.Start) {
                    numUsedRooms++;
                } else if (timeTypeAndTime.timeType == TimeType.End) {
                    numUsedRooms--;
                }
                numRequiredRooms = Math.max(numRequiredRooms, numUsedRooms);
            }
            return numRequiredRooms;
        }
    }
    /*
     * ・End、Startの順番に並び替えるのってこんなに大変なのかな。
     * 　・Comparatorでそれ以上複雑なことをしたくなかったので、
     * 　レコードの設定値をStart > End にしたけど、何でという感じにきっとなる。
     * 　　・ProirityQueueを使っても、やっぱりComparatorを指定する必要がありそう。
     */
    
}
/*
 * https://github.com/hayashi-ay/leetcode/pull/62
 * https://discord.com/channels/1084280443945353267/1201211204547383386/1231447174164774922
 *   https://github.com/shining-ai/leetcode/pull/56
 * https://github.com/Mike0121/LeetCode/pull/28
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/61
 */
