package com.johnuckele.puzzle.crossword;

public class CrosswordPuzzleBuilder {
	private static int _size;
	private WordList _allPrimaryWords;
	private WordList _unusedPrimaryWords;
	private WordList _allFillerWords;
	private WordList _unusedFillerWords;

	public CrosswordPuzzleBuilder() {

	}

	public void setSize(int size) {
		_size = size;
	}

	private void addFillerWordList(WordList words) {
		if (_allFillerWords == null) {
			_allFillerWords = new WordList();
		}
		_allFillerWords.add(words);
	}

	private void addPrimaryWordList(WordList words) {
		if (_allPrimaryWords == null) {
			_allPrimaryWords = new WordList();
		}
		_allPrimaryWords.add(words);
	}

	public Word getRandomWord(boolean primary) {
		Word randomWord;
		if (primary) {
			int randomIndex = (int) (Math.random() * _unusedPrimaryWords.size());
			randomWord = _unusedPrimaryWords.get(randomIndex);
		} else {
			int randomIndex = (int) (Math.random() * _unusedFillerWords.size());
			randomWord = _unusedFillerWords.get(randomIndex);
		}
		return randomWord;
	}

	private Direction getRandomDirection(int existingVertical, int existingHorizontal) {
		double bias = Math.pow(2, -Math.abs(existingVertical - existingHorizontal) - 1);
		double verticalChance = existingVertical >= existingHorizontal ? bias : bias + 0.5;
		if (Math.random() < verticalChance) {
			return Direction.VERTICAL;
		} else {
			return Direction.HORIZONTAL;
		}
	}

	public boolean randomlyPlaceWord(CrosswordPuzzle puzzle, Word word) {
		// Find the wiggle magnitude based on word length and puzzle size to
		// determine theoretically valid positions for a word
		int wiggle = _size - word.getLength();
		int row, col;
		Direction direction = getRandomDirection(puzzle.getVerticalWords(), puzzle.getHorizontalWords());

		// Generate the position
		if (direction == Direction.HORIZONTAL) {
			row = (int) (Math.random() * _size);
			col = (int) (Math.random() * wiggle);
		} else {
			row = (int) (Math.random() * wiggle);
			col = (int) (Math.random() * _size);
		}

		// Place the word if possible
		if (puzzle.canPlaceWord(word, row, col, direction)) {
			puzzle.placeWord(word, row, col, direction);
			return true;
		} else {
			return false;
		}
	}

	public boolean addWord(CrosswordPuzzle puzzle, boolean primary) {
		Word word = getRandomWord(primary);
		boolean sucess = randomlyPlaceWord(puzzle, word);
		if (sucess) {
			if (primary) {
				_unusedPrimaryWords.remove(word);
			}
			_unusedFillerWords.remove(word);
			return true;
		} else {
			return false;
		}
	}

	public CrosswordPuzzle build() {
		CrosswordPuzzle p = new CrosswordPuzzle(_size);
		p.setEnforcedSymmetry(Symmetry.TWO_FOLD_ROTATIONAL);
		_unusedPrimaryWords = new WordList(_allPrimaryWords);
		_unusedFillerWords = new WordList(_allFillerWords);
		int wordsAdded = 0;
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < 1000 && wordsAdded < 9) {
			if (addWord(p, true)) {
				wordsAdded++;
			}
		}
		wordsAdded = 0;
		startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < 1000 && wordsAdded < 30) {
			if (addWord(p, false)) {
				wordsAdded++;
			}
		}

		return p;
	}

	public static void main(String[] args) {
		WordList primaryWords = new JSONLoader().loadWordListFromFilename("src/main/resources/simple.json");
		WordList fillerWords = new JSONLoader().loadWordListFromFilename("src/main/resources/filler.json");

		CrosswordPuzzleBuilder builder = new CrosswordPuzzleBuilder();

		builder.setSize(15);
		builder.addPrimaryWordList(primaryWords);
		builder.addFillerWordList(fillerWords);

		CrosswordPuzzle testPuzzle1 = builder.build();
		System.out.println(testPuzzle1.toString(true));

		CrosswordPuzzle testPuzzle2 = builder.build();
		testPuzzle2.blockOpenSpaces();
		System.out.println(testPuzzle2.toString(true));

		// builder.setSize(11);
		// CrosswordPuzzle testPuzzle3 = builder.build();
		// System.out.println(testPuzzle3.toString(true));
	}

}
