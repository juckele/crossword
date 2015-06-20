package com.johnuckele.puzzle.crossword;

public class Word {
    private final String _word;

    public Word(String word) {
	_word = word;
    }

    public int getScore() {
	return 1;
    }

    public String toString() {
	return _word;
    }
}
