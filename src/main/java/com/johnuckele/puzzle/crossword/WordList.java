package com.johnuckele.puzzle.crossword;

import java.util.ArrayList;
import java.util.List;

public class WordList {
	private List<Word> _words;

	public WordList() {
		_words = new ArrayList<Word>();
	}

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
		int score = 0;

		for (int i = 0; i < _words.size(); i++) {
			Word w = _words.get(i);
			int wScore = w.getScore();
			score += wScore;
		}

		return score;
	}

	public int indexOf(Word e) {
		return _words.indexOf(e);
	}

	public void set(int index, Word e) {
		_words.set(index, e);
	}
}
