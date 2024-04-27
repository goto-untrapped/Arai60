	
	// ジグザグの動きを愚直に表現することしか思いつかなかった。
	// テストケースが通らず、curRowやcountの初期値を設定し直したり、else句を追加したり、
	// インクリメントのタイミングを調整している内に、複雑な処理になってしまった。
	// 時間計算量：O(n)
	// nはsの文字数。sのインデックスとリスト追加のインデックスを同時に進めていて、しかも、
	// インデックスのサイズが同じのため。
	// 空間計算量：O(n)
	// 振り分けたリストの文字数を合計したら、入力のsと一致するため。
	public String convert1E(String s, int numRows) {
		if (numRows == 1) return s;
		List<List<Character>> lists = new ArrayList<>();
		for (int i = 0; i < numRows; i++) {
			lists.add(new ArrayList<>());
		}

		int curRow = -1;
		int count = 0;
		while (count < s.length()) {
			while (curRow < numRows - 1) {
				if (count < s.length()) {
					curRow++;
					lists.get(curRow).add(s.charAt(count));
					count++;
				} else {
					break;
				}
			}
			while (curRow > 0) {
				if (count < s.length()) {
					curRow--;
					lists.get(curRow).add(s.charAt(count));
					count++;
				} else {
					break;
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		for (List<Character> list : lists) {
			for (Character c : list) {
				sb.append(c);
			}
		}
		return sb.toString();
	}
