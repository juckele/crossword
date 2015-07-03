package com.johnuckele.puzzle.crossword;

public class Word {
	private final String _word;
	private final int _score;

	public Word(String word, int score) {
		_word = word.toUpperCase();
		_score = score;
	}

	public int getScore() {
		return _score;
	}

	public String getWord() {
		return _word;
	}

	public String toString() {
		return _word + ":" + _score;
	}

	public int getLength() {
		return _word.length();
	}
}
