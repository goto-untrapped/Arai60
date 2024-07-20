public class CapacityToShipPackagesWithinDDaysStep3 {
    // 5min / 4min / 4m50s
    public int shipWithinDays(int[] weights, int days) {
        int maxLoad = 0;
        int totalLoad = Arrays.stream(weights).sum();
        while (maxLoad < totalLoad) {
            int candidateLoad = (maxLoad + totalLoad) / 2;
            if (canShipWithinDays(weights, days, candidateLoad)) {
                totalLoad = candidateLoad;
            } else {
                maxLoad = candidateLoad + 1;
            }
        }
        return maxLoad;
    }

    private boolean canShipWithinDays(int[] weights, int days, int maxLoad) {
        int pastDays = 1;
        int currentLoad = 0;
        for (int weight : weights) {
            if (weight > maxLoad) {
                return false;
            }
            currentLoad += weight;
            if (currentLoad > maxLoad) {
                currentLoad = weight;
                pastDays++;
            }
            if (pastDays > days) {
                return false;
            }
        }
        return true;
    }
    
    /*
     * 思ったこと
     * ・canShipWithinDays() は true/false で返した方が処理回数を最適化できていると思い、条件を分けた。
     * 余裕がない場合、return pastDays <= days でやると思う。 
     * ・まずは基本の考え方を身に付けるのが大事だと思い、maxLoad = 0; で初期化した。
     * ・canShipWithinDays() 内の currentLoad += weight; からの数行は、個人的に分かりやすくて、
     * でも下記の書き方もいいと思う。ただその場合、if (pastDays > days)を入れるか、読みやすさを重視するか際どくなる気がする。
     * for (int weight : weights) {
            if (weight > maxLoad) {
                return false;
            }
            if (currentLoad + weight > maxLoad) {
                currentLoad = weight;
                pastDays++;
                continue;
            }
            currentLoad += weight;
        }
        return pastDays <= days;
        
        早く返せるように true/false を入れようとする、たぶん↓になる
        for (int weight : weights) {
            if (weight > maxLoad) {
                return false;
            }
            if (currentLoad + weight > maxLoad) {
                currentLoad = weight;
                pastDays++;
            } else {
                currentLoad += weight;
            }
            if (pastDays > days) {
                return false;
            }
        }
        return true;
     */
}
