public class MaximumSubArrayStep2 {
    /*
     * 覚えるというより、やってて、あ、知ってるくらいでいいもの
     * > いや、私、何を言っているかというと、これ、Kadane's algorithm といって、名前がついている上に常識に入っていない程度のものなので、
     *   いきなりこれを書いたら、Kadane 程度の天才か、知っていたんだろうなって思って、この問題は採点外にして、次の問題を出します。
     * https://discord.com/channels/1084280443945353267/1206101582861697046/1207405733667410051
     *
     * 素直に考えて、最適化していくとKadane のアルゴリズムになるよと解釈（別にそれにする必要はない）
     * https://discord.com/channels/1084280443945353267/1206101582861697046/1207380634952011796
     * https://discord.com/channels/1084280443945353267/1225849404037009609/1236015228190326924 
     *
     * 素直に問題文を読んでみる
     * 配列の中で、連続する配列の和が一番大きい時の最大値はいくつか
     * ふつうに考えて思いつくのは、1つから全部の要素まで、1回ずつ計算していく
     */
    public int maxSubArray_TLE1(int[] nums) {
        int maxSum = nums[0];
        for (int i = 0; i < nums.length; i++) {
            int currentSum = 0;
            for (int j = i; j < nums.length; j++) {
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }
        return maxSum;
    }
    /*
     * 2重ループしながら配列の和を考えるのが難しかった
     * 関数として出して後から中身を埋めたら書きやすかった
     */
    public int maxSubArray_TLE2(int[] nums) {
        int maxSubarray = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                int currentSubarray = calcTotal(nums, i, j);
                maxSubarray = Math.max(maxSubarray, currentSubarray);
            }
        }
        return maxSubarray;
    }
    private int calcTotal(int[] nums, int start, int end) {
        int total = 0;
        for (int i = start; i <= end; i++) {
            total += nums[i];
        }
        return total;
    }
    /*
     * TLEするため、最適化を考える
     * 同じ計算を何回もしている。一回計算した数を、計算せずにもう一度使いまわせないか。行き詰まる。
     * 覚える：それまでの和を計算した場合、和同士の差ははじめの要素より後、終わりの要素より前の要素の合計
     * なので、累積和同士の差を求めると、その間の要素の和が分かる
     */
    public int maxSubArray_TLE3(int[] nums) {
        int[] prefixSumArray = new int[nums.length + 1];
        prefixSumArray[0] = 0;
        for (int i = 1; i < prefixSumArray.length; i++) {
            prefixSumArray[i] = prefixSumArray[i - 1] + nums[i - 1];
        }

        int maxSum = nums[0];
        for (int i = 0; i < prefixSumArray.length; i++) {
            for (int j = i + 1; j < prefixSumArray.length; j++) {
                int subArraySum = getSum(prefixSumArray, i, j);
                maxSum = Math.max(maxSum, subArraySum);
            }
        }
        return maxSum;
    }
    private int getSum(int[] prefixSumArray, int start, int end) {
        return prefixSumArray[end] - prefixSumArray[start];
    }
    /*
     * 行き詰まったのでログを確認。累積和の最小値を求めれば最大値を計算できる ことが、理解できなかった
     * 分かってなかった：[A min B 候補C] の時に、minを引いたらA+B中抜けになってうまくいかないんじゃないかと思った
     * minはそれまでの累積和なので、引いたらminより後から候補までの和になって中抜けになることはない（累積和を配列で持ったらそういうことにならない）
     * また、minがマイナスだった場合、実はそれを引かない方が大きい値になるけど、これは候補 - minでちょうど（と思ってしまう）大きくできる
     */
    public int maxSubArray_TLE4(int[] nums) {
        int[] prefixSumArray = new int[nums.length + 1];
        prefixSumArray[0] = 0;
        for (int i = 1; i < prefixSumArray.length; i++) {
            prefixSumArray[i] = prefixSumArray[i - 1] + nums[i - 1];
        }

        int maxSum = nums[0];
        for (int i = 1; i < prefixSumArray.length; i++) {
            for (int j = 0; j < i; j++) {
                int minSubArraySum = getMinSubArraySum(prefixSumArray, j, i);
                maxSum = Math.max(maxSum, prefixSumArray[i] - minSubArraySum);
            }
        }
        return maxSum;
    }
    private int getMinSubArraySum(int[] prefixSumArray, int start, int end) {
        int minSubArraySum = prefixSumArray[start];
        for (int i = start + 1; i < end; i++) {
            minSubArraySum = Math.min(minSubArraySum, prefixSumArray[i]);
        }
        return minSubArraySum;
    }
    /*
     * 1重ループにできるんだ・・・。
     * Runしてインデックス微調整しない。
     * 考える時、複雑になりそうだなと思ったら関数として一旦置いておくとかして整理する、まずは全部一気に考えない
     */
    public int maxSubArray2_1(int[] nums) {
        int[] prefixSumArray = new int[nums.length + 1];
        prefixSumArray[0] = 0;
        for (int i = 1; i < prefixSumArray.length; i++) {
            prefixSumArray[i] = prefixSumArray[i - 1] + nums[i - 1];
        }

        int minSubArraySum = prefixSumArray[0];
        int maxSubArraySum = prefixSumArray[1];
        for (int i = 1; i < prefixSumArray.length - 1; i++) {
            minSubArraySum = Math.min(minSubArraySum, prefixSumArray[i]);
            maxSubArraySum = Math.max(maxSubArraySum, prefixSumArray[i + 1] - minSubArraySum);
        }
        return maxSubArraySum;
    }
    
    /*
     * > まずこんな感じでDPで解けますよね。
     *   https://discord.com/channels/1084280443945353267/1225849404037009609/1236015228190326924
     */
    public int maxSubArray2_2(int[] nums) {
        int[] maxPrefixSums = new int[nums.length];
        maxPrefixSums[0] = nums[0];
        for (int i = 1; i < maxPrefixSums.length; i++) {
            maxPrefixSums[i] = Math.max(maxPrefixSums[i - 1] + nums[i], nums[i]);
        }

        int maxPrefixSum = maxPrefixSums[0];
        for (int i = 1; i < maxPrefixSums.length; i++) {
            if (maxPrefixSum < maxPrefixSums[i]) {
                maxPrefixSum = maxPrefixSums[i];
            }
        }
        return maxPrefixSum;
    }

    // 1つ前のdpだけ分かればよい。自分自身か大きい方がmax
    public int maxSubArray2_3(int[] nums) {
        int[] maxPrefixSums = new int[nums.length];
        maxPrefixSums[0] = nums[0];
        int maxPrefixSum = maxPrefixSums[0];
        for (int i = 1; i < maxPrefixSums.length; i++) {
            maxPrefixSums[i] = Math.max(maxPrefixSums[i - 1] + nums[i], nums[i]);
            maxPrefixSum = Math.max(maxPrefixSum, maxPrefixSums[i]);
        }

        return maxPrefixSum;
    }
    
    // 1つ前だけ分かればいいのであれば定数でいい
    public int maxSubArray2_4(int[] nums) {
        int maybeMaxPrefixSum = nums[0];
        int maxPrefixSum = maybeMaxPrefixSum;
        for (int i = 1; i < nums.length; i++) {
            maybeMaxPrefixSum = Math.max(maybeMaxPrefixSum + nums[i], nums[i]);
            maxPrefixSum = Math.max(maxPrefixSum, maybeMaxPrefixSum);
        }

        return maxPrefixSum;
    }
}
