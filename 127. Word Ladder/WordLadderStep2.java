public class WordLadderStep2 {
    // アルファベットを1文字順に置き換えてみる
    class Solution2_1 {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Set<String> wordSet = new HashSet<>(wordList);
            List<String> searchWords = new ArrayList<>();
            searchWords.add(beginWord);
            Set<String> usedWords = new HashSet<>();
            usedWords.add(beginWord);
            int count = 1;
            while (searchWords.size() > 0) {
                List<String> nextSearchWords = new ArrayList<>();
                for (String searchWord : searchWords) {
                    if (searchWord.equals(endWord)) {
                        return count;
                    }
                    for (int i = 0; i < searchWord.length(); i++) {
                        for (int j = 'a'; j <= 'z'; j++) {
                            String tryWord = searchWord.substring(0, i) + (char) j + searchWord.substring(i + 1, searchWord.length());
                            if (usedWords.contains(tryWord)) {
                                continue;
                            }
                            if (wordSet.contains(tryWord)) {
                                nextSearchWords.add(tryWord);
                                usedWords.add(tryWord);
                            }
                        }
                    }
                }
                count++;
                searchWords = nextSearchWords;
            }
            return 0;
        }
    }
    /*
     * ・1行目のwordSetを入れずにwordListのままでcontains()するとTLE
     * 　・O(n)かかるからかな。
     * ・ネストが深く、substring()もしているのでwhileも入れたら5重でループしている
     * 　・1文字置き換えのやり方を変えてみる
     * 　　・関数化も考えたけど、ネストを浅くするのであればループごと関数にすることになると思うが、
     * 　　そうするとメインで使う変数を知らない所で操作していることになって、ただ見る場所が遠くなるだけな気がした。
     * ・添え字が分かりにくかったな。jはintLetterとかなのかな。
     */
    
    // Solution2_1を変更
    class Solution2_2 {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Set<String> wordSet = new HashSet<>(wordList);
            List<String> searchWords = new ArrayList<>();
            searchWords.add(beginWord);
            Set<String> usedWords = new HashSet<>();
            usedWords.add(beginWord);
            int count = 1;
            while (!searchWords.isEmpty()) {
                List<String> nextSearchWords = new ArrayList<>();
                for (String searchWord : searchWords) {
                    if (searchWord.equals(endWord)) {
                        return count;
                    }
                    char[] searchChars = searchWord.toCharArray();
                    for (int i = 0; i < searchChars.length; i++) {
                        char originalLetter = searchChars[i];
                        for (char letter = 'a'; letter <= 'z'; letter++) {
                            searchChars[i] = letter;
                            String tryWord = String.valueOf(searchChars);
                            if (usedWords.contains(tryWord)) {
                                continue;
                            }
                            if (wordSet.contains(tryWord)) {
                                nextSearchWords.add(tryWord);
                                usedWords.add(tryWord);
                            }
                        }
                        searchChars[i] = originalLetter;
                    }
                }
                count++;
                searchWords = nextSearchWords;
            }
            return 0;
        }
    }
    /*
     * ・Solution2_1と比べて速くなった。228ms -> 103ms 
     */
    
    
    // 隣接リスト
    class Solution2_3_TLE {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Map<String, List<String>> wordToAdjacentWords = new HashMap<>();
            for (String word : wordList) {
                if (wordToAdjacentWords.get(word) == null) {
                    wordToAdjacentWords.put(word, new ArrayList<>());
                }
                collectAdjacentWords(wordToAdjacentWords, wordList, word);
            }

            Queue<String> searchWords = new LinkedList<>();
            searchWords.offer(beginWord);
            Set<String> usedWords = new HashSet<>();
            usedWords.add(beginWord);
            int count = 1;
            while (!searchWords.isEmpty()) {
                Queue<String> nextSearchWords = new LinkedList<>();
                while (!searchWords.isEmpty()) {
                    String searchWord = searchWords.poll();
                    if (searchWord.equals(endWord)) {
                        return count;
                    }
                    if (wordToAdjacentWords.get(searchWord) == null) {
                        continue;
                    }
                    for (String adjacentWord : wordToAdjacentWords.get(searchWord)) {
                        if (usedWords.contains(adjacentWord)) {
                            continue;
                        }
                        nextSearchWords.offer(adjacentWord);
                        usedWords.add(adjacentWord);
                    }
                }
                count++;
                searchWords = nextSearchWords;
            }
            return 0;
        }

        private void collectAdjacentWords( Map<String, List<String>> wordToAdjacentWords, 
                                           List<String> wordList, String targetWord) {
            
            for (String word : wordList) {
                if (targetWord.equals(word)) {
                    continue;
                }
                char[] targetChars = targetWord.toCharArray();
                char[] wordChars = word.toCharArray();
                int distance = 0;
                for (int i = 0; i < targetChars.length; i++) {
                    if (targetChars[i] != wordChars[i]) {
                        distance++;
                    }
                }
                if (distance == 1) {
                    wordToAdjacentWords.get(targetWord).add(word);
                }
            }
        }
    }
    
    // Solution2_3_TLEを変更 見る元の作り方を変更
    class Solution2_3 {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            List<String> newWordList = new ArrayList<>();
            newWordList.addAll(wordList);
            newWordList.add(beginWord);
            Map<String, List<String>> wordToAdjacentWords = new HashMap<>();
            for (String word : newWordList) {
                wordToAdjacentWords.put(word, new ArrayList<>());
            }
            for (int i = 0; i < newWordList.size(); i++) {
                for (int j = i + 1; j < newWordList.size(); j++) {
                    String word = newWordList.get(i);
                    String anotherWord = newWordList.get(j);
                    if (isAdjacent(word, anotherWord)) {
                        wordToAdjacentWords.get(word).add(anotherWord);
                        wordToAdjacentWords.get(anotherWord).add(word);
                    }
                }
            }

            Queue<String> searchWords = new LinkedList<>();
            searchWords.offer(beginWord);
            Set<String> usedWords = new HashSet<>();
            usedWords.add(beginWord);
            int count = 1;
            while (!searchWords.isEmpty()) {
                Queue<String> nextSearchWords = new LinkedList<>();
                while (!searchWords.isEmpty()) {
                    String searchWord = searchWords.poll();
                    if (searchWord.equals(endWord)) {
                        return count;
                    }
                    if (wordToAdjacentWords.get(searchWord) == null) {
                        continue;
                    }
                    for (String adjacentWord : wordToAdjacentWords.get(searchWord)) {
                        if (usedWords.contains(adjacentWord)) {
                            continue;
                        }
                        nextSearchWords.offer(adjacentWord);
                        usedWords.add(adjacentWord);
                    }
                }
                count++;
                searchWords = nextSearchWords;
            }
            return 0;
        }

        private boolean isAdjacent(String targetWord, String word) {
            if (targetWord.equals(word)) {
                return false;
            }
            char[] targetChars = targetWord.toCharArray();
            char[] wordChars = word.toCharArray();
            int distance = 0;
            for (int i = 0; i < targetChars.length; i++) {
                if (targetChars[i] != wordChars[i]) {
                    distance++;
                }
                if (distance > 1) {
                    break;
                }
            }
            return distance == 1;
        }
    }
    /*
     * ・Solution2_3_TLEもSolution2_3も、隣接リスト自体は作れている気がする。
     * 　・TLEの方が、自分とも比較する分、wordListの長さ分余計に時間がかかるけど、そこまで大きい差でもない気がする。同じ3重ループ。
     * 　　・何でTLEか？。。。
     */
    
    
    /*
     * ダイクストラ法 TLE
     * 下記のアルゴリズムだったと思い、それに忠実になるように実装してみる
     * 　初期値∞として辿れる場所のうち最小コストの場所からコストを決定していく
     * 　https://github.com/kazukiii/leetcode/pull/21
     */
    class Solution2_4_TLE {
        private final int INITIALIZE_VALUE = Integer.MAX_VALUE;
        record WordAndDistance(String word, int distance) {}

        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Map<String, Integer> wordToDistance = new HashMap<>();
            for (String word : wordList) {
                wordToDistance.put(word, INITIALIZE_VALUE);
            }
            wordToDistance.put(beginWord, 1);
            
            PriorityQueue<WordAndDistance> wordAndDistances = new PriorityQueue<>((a, b) -> (a.distance() - b.distance()));
            wordAndDistances.offer(new WordAndDistance(beginWord, 1));
            while (!wordAndDistances.isEmpty()) {
                WordAndDistance wordAndDistance = wordAndDistances.poll();
                String word = wordAndDistance.word();
                int distance = wordAndDistance.distance();

                char[] wordChars = word.toCharArray();
                for (String searchWord : wordList) {
                    for (int i = 0; i < wordChars.length; i++) {
                        char originalLetter = wordChars[i];
                        for (char letter = 'a'; letter <= 'z'; letter++) {
                            wordChars[i] = letter;
                            if (!searchWord.equals(String.valueOf(wordChars))) {
                                continue;
                            }
                            if (distance + 1 > wordToDistance.get(searchWord)) {
                                continue;
                            }
                            wordToDistance.put(searchWord, distance + 1);
                            wordAndDistances.offer(new WordAndDistance(searchWord, distance + 1));
                        }
                        wordChars[i] = originalLetter;
                    }
                }
            }
            if (wordToDistance.get(endWord) == null || wordToDistance.get(endWord) == INITIALIZE_VALUE) {
                return 0;
            }
            return wordToDistance.get(endWord);
        }
    }
    /*
     * ・間に合わない。辿る単語すべてで単語リストとの一致を探しているからかなと思った。
     * 今回のケースは見つかったものから最小距離なので、見つかったらその単語を消す、のようにすると間に合うかもしれない。
     * （と思って書いたのがSolution2_4_TLE2で、結局TLE）
     */
    
    // Solution2_4_TLEを変更したが、TLE
    class Solution2_4_TLE2 {
        private final int INITIALIZE_VALUE = Integer.MAX_VALUE;
        record WordAndDistance(String word, int distance) {}

        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Map<String, Integer> wordToDistance = new HashMap<>();
            for (String word : wordList) {
                wordToDistance.put(word, INITIALIZE_VALUE);
            }
            wordToDistance.put(beginWord, 1);
            Set<String> wordSet = new HashSet<>(wordList);
            
            PriorityQueue<WordAndDistance> wordAndDistances = new PriorityQueue<>((a, b) -> (a.distance() - b.distance()));
            wordAndDistances.offer(new WordAndDistance(beginWord, 1));
            while (!wordAndDistances.isEmpty()) {
                WordAndDistance wordAndDistance = wordAndDistances.poll();
                String word = wordAndDistance.word();
                int distance = wordAndDistance.distance();

                char[] wordChars = word.toCharArray();
                String[] words = Arrays.copyOf(wordSet.toArray(), wordSet.size(), String[].class);
                for (String searchWord : words) {
                    for (int i = 0; i < wordChars.length; i++) {
                        char originalLetter = wordChars[i];
                        for (char letter = 'a'; letter <= 'z'; letter++) {
                            wordChars[i] = letter;
                            if (!searchWord.equals(String.valueOf(wordChars))) {
                                continue;
                            }
                            if (distance + 1 > wordToDistance.get(searchWord)) {
                                continue;
                            }
                            wordToDistance.put(searchWord, distance + 1);
                            wordAndDistances.offer(new WordAndDistance(searchWord, distance + 1));
                            wordSet.remove(searchWord);
                        }
                        wordChars[i] = originalLetter;
                    }
                }
            }
            if (wordToDistance.get(endWord) == null || wordToDistance.get(endWord) == INITIALIZE_VALUE) {
                return 0;
            }
            return wordToDistance.get(endWord);
        }
    }
    /*
     * ・距離を更新した単語は次に走査しないように削除したけど、間に合わなかった。
     */
    
    
    // 双方向BFSも書いてみる
    class Solution2_5 {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            Map<String, List<String>> wordToAdjacentWords = new HashMap<>();
            List<String> allWordList = new ArrayList<>();
            allWordList.addAll(wordList);
            allWordList.add(beginWord);
            for (String word : allWordList) {
                wordToAdjacentWords.put(word, new ArrayList<>());
            }
            for (int i = 0; i < allWordList.size(); i++) {
                String word1 = allWordList.get(i);
                for (int j = i + 1; j < allWordList.size(); j++) {
                    String word2 = allWordList.get(j);
                    if (isOneLetterDifferent(word1, word2)) {
                        wordToAdjacentWords.get(word1).add(word2);
                        wordToAdjacentWords.get(word2).add(word1);
                    }
                }
            }

            Queue<String> fromBeginWords = new LinkedList<>();
            fromBeginWords.offer(beginWord);
            Set<String> fromBeginUsedWords = new HashSet<>();
            fromBeginUsedWords.add(beginWord);
            Queue<String> fromEndWords = new LinkedList<>();
            fromEndWords.offer(endWord);
            Set<String> fromEndUsedWords = new HashSet<>();
            fromEndUsedWords.add(endWord);
            // guaranteed beginWord != endWord
            int count = 2;
            while (!fromBeginWords.isEmpty() && !fromEndWords.isEmpty()) {
                Queue<String> words;
                Set<String> usedWords;
                Set<String> oppositeUsedWords;
                if (fromBeginWords.size() <= fromEndWords.size()) {
                    words = fromBeginWords;
                    usedWords = fromBeginUsedWords;
                    oppositeUsedWords = fromEndUsedWords;
                } else {
                    words = fromEndWords;
                    usedWords = fromEndUsedWords;
                    oppositeUsedWords = fromBeginUsedWords;
                }
                int size = words.size();
                for (int i = 0; i < size; i++) {
                    String word = words.poll();
                    if (wordToAdjacentWords.get(word) == null) {
                        continue;
                    }
                    for (String adjacentWord : wordToAdjacentWords.get(word)) {
                        if (usedWords.contains(adjacentWord)) {
                            continue;
                        }
                        if (oppositeUsedWords.contains(adjacentWord)) {
                            return count;
                        }
                        words.offer(adjacentWord);
                        usedWords.add(adjacentWord);
                    }
                }
                count++;
            }
            return 0;
        }

        private boolean isOneLetterDifferent(String word1, String word2) {
            // guaranteed word1 != word2, same length
            char[] chars1 = word1.toCharArray();
            char[] chars2 = word2.toCharArray();
            int charLength = chars1.length;
            boolean isAlreadyDifferent = false;
            for (int i = 0; i < charLength; i++) {
                if (chars1[i] == chars2[i]) {
                    continue;
                }
                if (isAlreadyDifferent) {
                    return false;
                }
                isAlreadyDifferent = true;
            }
            return true;
        }
    }
    /*
     * ・endWordがwordListにない場合、map.get(null)エラーをするため、回避が必要。
     * ・同じ階層のqueueをループして次の隣接単語を追加する箇所で、次のレベルのqueueを用意したかったが、
     * queueに代入しても、queueの元の参照先まで変更されない。関数に分けると元の変数の参照をそのまま使えるので、
     * 参照の中身を更新したら元の変数も更新できて、やりたいことができそう。
     */
    
    // Solution2_5を変更
    class Solution2_6 {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            List<String> allWords = new ArrayList<>();
            allWords.addAll(wordList);
            allWords.add(beginWord);
            Map<String, List<String>> wordToAdjacentWords = new HashMap<>();
            for (String word : allWords) {
                wordToAdjacentWords.put(word, new ArrayList<>());
            }
            for (int i = 0; i < allWords.size(); i++) {
                String word1 = allWords.get(i);
                for (int j = i + 1; j < allWords.size(); j++) {
                    String word2 = allWords.get(j);
                    if (isOneLetterDifferent(word1, word2)) {
                        wordToAdjacentWords.get(word1).add(word2);
                        wordToAdjacentWords.get(word2).add(word1);
                    }
                }
            }

            Queue<String> fromBeginWords = new LinkedList<>();
            fromBeginWords.offer(beginWord);
            Set<String> fromBeginUsedWords = new HashSet<>();
            fromBeginUsedWords.add(beginWord);
            Queue<String> fromEndWords = new LinkedList<>();
            fromEndWords.offer(endWord);
            Set<String> fromEndUsedWords = new HashSet<>();
            fromEndUsedWords.add(endWord);
            int count = 2;
            while (!fromBeginWords.isEmpty() && !fromEndWords.isEmpty()) {
                Queue<String> words = fromBeginWords;
                Set<String> usedWords = fromBeginUsedWords;
                Set<String> oppositeUsedWords = fromEndUsedWords;
                if (fromBeginWords.size() > fromEndWords.size()) {
                    words = fromEndWords;
                    usedWords = fromEndUsedWords;
                    oppositeUsedWords = fromBeginUsedWords;
                }
                if (isTraversedWordLadder(wordToAdjacentWords, words, usedWords, oppositeUsedWords, endWord)) {
                    return count;
                }
                count++;
            }
            return 0;
        }

        private boolean isTraversedWordLadder(Map<String, List<String>> wordToAdjacentWords, Queue<String> words, 
                                              Set<String> usedWords, Set<String> oppositeUsedWords, String endWord) {

            Queue<String> nextWords = new LinkedList<>();
            while (!words.isEmpty()) {
                String word = words.poll();
                if (wordToAdjacentWords.get(word) == null) {
                    continue;
                }
                for (String adjacentWord : wordToAdjacentWords.get(word)) {
                    if (usedWords.contains(adjacentWord)) {
                        continue;
                    }
                    if (oppositeUsedWords.contains(adjacentWord)) {
                        return true;
                    }
                    nextWords.offer(adjacentWord);
                    usedWords.add(adjacentWord);
                }
            }
            while (!nextWords.isEmpty()) {
                words.offer(nextWords.poll());
            }
            return false;
        }

        private boolean isOneLetterDifferent(String word1, String word2) {
            char[] chars1 = word1.toCharArray();
            char[] chars2 = word2.toCharArray();
            int charLength = chars1.length;
            boolean isAlreadyDifferent = false;
            for (int i = 0; i < charLength; i++) {
                if (chars1[i] == chars2[i]) {
                    continue;
                }
                if (isAlreadyDifferent) {
                    return false;
                }
                isAlreadyDifferent = true;
            }
            return true;
        }
    }
    /*
     * ・isTraversedWordLadder()の中で words = nextWords; をすると変更を呼び出し元に
     * 戻せなかったが、中身を入れ替えてあげると戻すことができた。
     * 　・なのでSolution2_5も同じようにできた。
     */
    
}
/*
 * https://discord.com/channels/1084280443945353267/1183683738635346001/1199015686404571176
 * 　アルファベットを順に1文字変更して見て、1文字違いがあればladderとしてqueueしてもできる
 * https://github.com/hayashi-ay/leetcode/pull/42
 * 　隣接リストをMapにする。こっちの方が汎用性はきっとある。
 * https://github.com/sakupan102/arai60-practice/pull/20
 * https://github.com/fhiyo/leetcode/pull/22
 * 　Mapを作っておかなくても、走査するたびに違っている距離が１だけかどうか見れる
 * https://github.com/kazukiii/leetcode/pull/21
 * 　Step2 ダイクストラ　理解できるようにする
 * https://github.com/Yoshiki-Iwasa/Arai60/pull/22
 * https://github.com/TORUS0818/leetcode/pull/22
 * https://github.com/Ryotaro25/leetcode_first60/pull/22
 * https://github.com/seal-azarashi/leetcode/pull/19
 * 
 * ・1文字違いだと識別する方法
 * 　・ワイルドカード
 * 　・アルファベット全検索
 * 　・ハミング距離
 * 　・隣接リスト
 * 　・ダイクストラ(どれかと同類かも)
 * ・パフォーマンスの上げ方
 * 　・キャッシュ(どの議論で か分かっていない)
 * 　・双方向BFS
 * 　・遅延評価(最初に全マッピング作っても使わないかもなので走査時に判定する)
 */
