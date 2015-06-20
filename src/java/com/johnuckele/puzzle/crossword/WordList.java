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

    public int size() {
	return _words.size();
    }

    public int getScore() {
	// TODO Monica, have you noticed that this is doing the wrong thing yet?
	// Please fix it.
	return _words.size();
    }
}
