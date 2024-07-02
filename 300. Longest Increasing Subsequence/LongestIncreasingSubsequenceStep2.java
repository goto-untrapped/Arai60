public class LongestIncreasingSubsequenceStep2 {
    
    /*
     * https://github.com/YukiMichishita/LeetCode/pull/7
     *   https://discord.com/channels/1084280443945353267/1192736784354918470/1227973636275703863
     *   
     * https://github.com/shining-ai/leetcode/pull/31#discussion_r1536809775
     *   https://discord.com/channels/1084280443945353267/1201211204547383386/1221404304158752868
     * ⇒セグメントツリーと座標圧縮の単語だけ知ったぐらい（ぎりぎり常識外だったらやるとしても後ででいいのかな）
     * 
     * https://github.com/hayashi-ay/leetcode/pull/27
     *   https://discord.com/channels/1084280443945353267/1200089668901937312/1209534429593210932
     * 
     * -
     *   https://discord.com/channels/1084280443945353267/1196472827457589338/1196473359588929536
     */
    
    // 大きいものが来たら入れ替え
    // 入ってるものが実際の増加している要素配列と違うので、やり方として採用しない方がいい気がしている
    // もし要素配列も見たいと思った時に、中身が想定と違うため
    // でも計算量がより少ないので、基本的にはこっちで書くとよいのかな
    public int lengthOfLIS2_1(int[] nums) {
        List<Integer> increasingNums = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (increasingNums.size() == 0) {
                increasingNums.add(nums[i]);
                continue;
            }

            if (increasingNums.get(increasingNums.size() - 1) < nums[i]) {
                increasingNums.add(nums[i]);
                continue;
            }

            for (int j = 0; j < increasingNums.size(); j++) {
                // ライナーや二分探索で書くとき、同じ数字の時も見つけたとして代入している
                // やっても変わらないのならやらないほうが分かりやすいのでは？と思った
                if (increasingNums.get(j) == nums[i]) {
                    break;
                }

                if (increasingNums.get(j) > nums[i]) {
                    increasingNums.set(j, nums[i]);
                    break;
                }
            }
        }

        return increasingNums.size();
    }
    
    // インデックスを増やしながら入れ替え位置を探す
    public int lengthOfLIS2_2(int[] nums) {
        ArrayList<Integer> increasingNums = new ArrayList<>();

        for (int num : nums) {
            if (increasingNums.size() == 0) {
                increasingNums.add(num);
                continue;
            }

            if (increasingNums.get(increasingNums.size() - 1) < num) {
                increasingNums.add(num);
                continue;
            }

            int replaceIndex = 0;
            while (increasingNums.get(replaceIndex) < num) {
                replaceIndex++;
            }
            increasingNums.set(replaceIndex, num);
        }

        return increasingNums.size();
    }
    
    // 2分探索を使って入れ替え位置を探す
    public int lengthOfLIS2_3(int[] nums) {
        ArrayList<Integer> increasingNums = new ArrayList<>();

        for (int num : nums) {
            if (increasingNums.size() == 0) {
                increasingNums.add(num);
                continue;
            }

            if (increasingNums.get(increasingNums.size() - 1) < num) {
                increasingNums.add(num);
                continue;
            }

            int replaceIndex = search(increasingNums, num);
            increasingNums.set(replaceIndex, num);   
        }
        return increasingNums.size();
    }

    private int search(ArrayList<Integer> increasingNums, int target) {
        int left = 0;
        int right = increasingNums.size();

        while (left < right) {
            int middle = left + (right - left) / 2;

            if (increasingNums.get(middle) < target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }
        return left;
    }
    
    // 標準機能の二分探索を使う
    public int lengthOfLIS2_4(int[] nums) {
        ArrayList<Integer> increasingNums = new ArrayList<>();

        for (int num : nums) {
            if (increasingNums.size() == 0) {
                increasingNums.add(num);
                continue;
            }

            if (increasingNums.get(increasingNums.size() - 1) < num) {
                increasingNums.add(num);
                continue;
            }

            int replaceIndex = Collections.binarySearch(increasingNums, num);
            if (replaceIndex < 0) {
                // 仕様が 見つからなかった場合は -(挿入位置)-1 で、挿入位置は自分より大きい最初の値のインデックスなので、
                // [1 3 5] で2を探す場合、 -(1)-1 = -2になる。1110 => 0001 で1になるらしい。not付ければ0始まりになるイメージがあったので、受け入れられた 
                replaceIndex = ~replaceIndex;
            }
            increasingNums.set(replaceIndex, num);
        }

        return increasingNums.size();
    }
    
    // DPを関数化
    public int lengthOfLIS2_5(int[] nums) {
        int[] maxIncreasingCounts = new int[nums.length];
        Arrays.fill(maxIncreasingCounts, 1);

        for (int i = 1; i < nums.length; i++) {
            int increasingCount = compareAndGetMax(nums, i, maxIncreasingCounts);
            maxIncreasingCounts[i] = increasingCount + 1;
        }
        
        int maxCount = 1;
        for (int count : maxIncreasingCounts) {
            if (maxCount < count) {
                maxCount = count;
            }
        }
        return maxCount;
    }

    private int compareAndGetMax(int[] nums, int endIndex, int[] counts) {
        int length = 0;
        for (int i = 0; i < endIndex; i++) {
            if (nums[i] < nums[endIndex]) {
                length = Math.max(length, counts[i]);
            }
        }
        return length;
    }
    
}
