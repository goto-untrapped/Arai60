public class ZigzagConversionStep2 {
	
	// 他の解法 LeetCodeの回答を参照
	
	// ジグザグの動きをstepで解決している
	// Step1でやりたかったことの最終進化系だと思った
	// AC: 5ms / 44.48MB
	public String convert2_1(String s, int numRows) {
		// 模範解答では numRows >= s.length()も入れている。
		// numRows = 14 でデバッグしたところ、ACだった
		// 確かに、1文字ずつしか1列にいなかったら、上から順に出力すればいいので、
		// ロジックを流す必要がない。パフォーマンス改善できるだけだと気付きたい。
        if (numRows == 1) return s;

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }

        int step = 1;
        int currRow = 0;
        for (char c : s.toCharArray()) {
            rows.get(currRow).append(c);
            currRow += step;

            // numRows - 1になったら、次のstepの加算に備える
            // max - 1のインデックスに値を入れたいため、最初から-1を入れる
            // 0になった場合も、次には+1のインデックスに値を入れたいため、最初から1を入れる
            if (currRow == 0 || currRow == numRows - 1) {
                step = -step;
            }
        }

        StringBuilder concat = new StringBuilder();
        for (StringBuilder rowStr : rows) {
            concat.append(rowStr);
        }
        return concat.toString();
    }
	
	// AC: 35ms / 46.62MB
	// 最初に行数と列数を計算して、決まった場所に値を入れていくというやり方
	// 自分が境界値の計算が得意ではないため、使うは消極的
	// ⇒for文の第2条件のインデックスが < なのか <= なのか、よく間違えるため。
	//   条件が切り替わる場合のそれぞれの値を逐次追いかけるのが下手？
	//   訓練不足なのかな・・・。
	public String convert2_2(String s, int numRows) {
		if (numRows == 1) return s;

        int n = s.length();
        int section = (int) Math.ceil(n / (2.0 * (numRows - 1)));
        int numCols = section * (numRows - 1);

        int currRow = 0;
        int currCol = 0;
        int currStrIndex = 0;
        char[][] board = new char[numRows][numCols];
        // charにnullが入ると思ったら、c != null も !c.isEmpty() も弾かれるため、初期値を代入
        // 空文字''を入れてもランタイムエラーのため、空白' 'を入れる
        for (char[] chars : board) {
            Arrays.fill(chars, ' ');
        }

        while (currStrIndex < n) {
            while (currRow < numRows && currStrIndex < n) {
                board[currRow][currCol] = s.charAt(currStrIndex);
                currRow++;
                currStrIndex++;
            }

            currRow -= 2;
            currCol++;

            // 模範解答は、currCol < numColsも条件に入っていたが、
            // currCol < numCols のみfalseになるケースが思いつかなかったため、書かなかった。
            // 最後のsectionに文字列が余っていたら、currRow > 0 && currStrIndex < n で判定できると思った
            while (currRow > 0 && currStrIndex < n) {
                board[currRow][currCol] = s.charAt(currStrIndex);
                currRow--;
                currCol++;
                currStrIndex++;
            }
        }

        StringBuilder concat = new StringBuilder();
        for (char[] chars : board) {
            for (char c : chars) {
                if (c != ' ') {
                    concat.append(c);
                }
            }
        }
        return concat.toString();
    }
	
	// AC: 2ms / 44.17MB
	// いい感じに次のインデックスを計算する。確かに計算ができれば、格納するだけでいいのでとても楽。
	// 計算方法を自力で分かるのが難しいため、採用しないと思う。
	// また、この解き方をしたところ、頭を使う感覚がいつもと違った。
	// 時系列で処理をせずに、それぞれの役割を持った変数をある順番で変化させることで、
	// 結果的に正しいという、そのピースから広げながら組み立てるのではなく、決まった間隔の位置に
	// 正しいと思われるピースをはめていくような感覚だった。
	// 例えばindexはジグザグの真っ直ぐ列の部分の文字列をはめるために使うし、
	// ０行目とn-1行目以外の行からオフセットを計算するためにも使う。
	// indexを変化させるタイミングも工夫して、最初の列に代入⇒範囲をチェック⇒オフセットで使う⇒次の列に代入⇒...
	// という異なる役割を同じ手順で機能させている。
	// でも、慣れない感覚なので、いざという時は使わないと思う。
	public String convert2_3(String s, int numRows) {
        if (numRows == 1) return s;
        
        int n = s.length();
        int charsInSection = 2 * (numRows - 1);
        StringBuilder ans = new StringBuilder();

        for (int currRow = 0; currRow < numRows; currRow++) {
            int index = currRow;

            while (index < n) {
            	// 縦一列は必ず入れられる
                ans.append(s.charAt(index));

                if (currRow != 0 && currRow != numRows - 1) {
                    int offset = charsInSection - 2 * currRow;
                    int secondIndex = index + offset;
                    if (secondIndex < n) {
                        ans.append(s.charAt(secondIndex));
                    }
                }

                index += charsInSection;
            }
        }
        return ans.toString();
    }
}
