package com.johnuckele.puzzle.crossword;

import java.util.ArrayList;
import java.util.List;

public class WordList {
    private List<Word> _words;

    public WordList() {
	_words = new ArrayList<Word>();

    }

    public int size() {
	return _words.size();
    }

    public int getScore() {
	return _words.size();
    }
}
