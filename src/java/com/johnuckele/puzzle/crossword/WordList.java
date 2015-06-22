package com.johnuckele.puzzle.crossword;

import java.util.ArrayList;
import java.util.List;

public class WordList {
	private List<Word> _words;

	public WordList() {
		_words = new ArrayList<Word>();
	}

	// TODO Monica, turns out WordList is missing at least one pretty essential
	// method. Add what you need to complete the other tasks.

	public void add(Word e) {
		_words.add(e);
	}

	public Word get(int i) {
		return _words.get(i);
	}

	public int size() {
		return _words.size();
	}

	public int getTotalScore() {
		// TODO Monica, have you noticed that this is doing the wrong thing yet?
		// Please fix it.
		int score = 0;

		for (int i = 0; i < _words.size(); i++) {
			Word w = _words.get(i);
			int wScore = w.getScore();
			score += wScore;
		}

		return score;
	}
}
