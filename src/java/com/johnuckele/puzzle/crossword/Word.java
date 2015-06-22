package com.johnuckele.puzzle.crossword;

public class Word {
	private final String _word;
	private final int _score;

	public Word(String word, int score) {
		_word = word;
		_score = score;

	}

	public int getScore() {
		// TODO Yup. This is wrong too. Fix it as well.
		return _score;
	}

	public String toString() {
		return _word;
	}
}
