public class MeetingRoomsIIStep1 {
    /*
     * ・56minかかったけど通せた(過去に取り組んだことあり)
     * ・時間計算量：O(n^2)
     * ・空間計算量：O(n)
     */
    class Solution {
        public int minMeetingRooms(int[][] intervals) {
            int[][] ascStartTimeIntervals = Arrays.copyOf(intervals, intervals.length);
            Arrays.sort(ascStartTimeIntervals, (a, b) -> (a[0] - b[0]));
            List<List<int[]>> rooms = new ArrayList<>();
            rooms.add(new ArrayList<>());
            rooms.get(rooms.size() - 1).add(ascStartTimeIntervals[0]);
            for (int i = 1; i < ascStartTimeIntervals.length; i++) {
                if (isNotEnoughRooms(rooms, ascStartTimeIntervals[i])) {
                    rooms.add(new ArrayList<>());
                }
                addInterval(rooms, ascStartTimeIntervals[i]);
            }
            return rooms.size();
        }

        private boolean isNotEnoughRooms(List<List<int[]>> rooms, int[] interval) {
            boolean isNotEnough = true;
            for (int i = 0; i < rooms.size(); i++) {
                if (rooms.get(i).get(rooms.get(i).size() - 1)[1] <= interval[0]) {
                    isNotEnough = false;
                }
            }
            return isNotEnough;
        }

        private void addInterval(List<List<int[]>> rooms, int[] interval) {
            int i = 0;
            while (i < rooms.size()) {
                while (i < rooms.size() && rooms.get(i).size() > 0 && rooms.get(i).get(rooms.get(i).size() - 1)[1] > interval[0]) {
                    i++;
                }
                rooms.get(i).add(interval);
                return;
            }
        }
    }    
}
