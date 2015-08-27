package com.johnuckele.puzzle.crossword;

import java.util.ArrayList;
import java.util.Collections;

public class WordList {
	private ArrayList<Word> _words;

	public WordList() {
		_words = new ArrayList<Word>();
	}

	@SuppressWarnings("unchecked")
	public WordList(WordList wordList) {
		this._words = (ArrayList<Word>) wordList._words.clone();
	}

	public void add(Word e) {
		_words.add(e);
	}

	public void remove(Word e) {
		_words.remove(e);
	}

	public void add(WordList c) {
		_words.addAll(c._words);
	}

	public Word get(int i) {
		return _words.get(i);
	}

	public int size() {
		return _words.size();
	}

	public void sort() {
		Collections.sort(_words);
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
