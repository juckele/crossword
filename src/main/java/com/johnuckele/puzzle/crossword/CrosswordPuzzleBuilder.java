package com.johnuckele.puzzle.crossword;

public class CrosswordPuzzleBuilder {
	private static int _size;
	private WordList _allWords;
	private WordList _unusedWords;

	public CrosswordPuzzleBuilder() {

	}

	public void setSize(int size) {
		_size = size;
	}

	public void addWordList(WordList words) {
		if (_allWords == null) {
			_allWords = words;
		} else {
			_allWords.add(words);
		}
	}

	public Word getRandomWord() {
		int randomIndex = (int) (Math.random() * _unusedWords.size());
		Word randomWord = _unusedWords.get(randomIndex);
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

	public boolean addWord(CrosswordPuzzle puzzle) {
		Word word = getRandomWord();
		boolean sucess = randomlyPlaceWord(puzzle, word);
		if (sucess) {
			_unusedWords.remove(word);
			return true;
		} else {
			return false;
		}
	}

	public CrosswordPuzzle build() {
		CrosswordPuzzle p = new CrosswordPuzzle(_size);
		_unusedWords = new WordList(_allWords);
		int wordsAdded = 0;
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < 10000 && wordsAdded < 9) {
			if (addWord(p)) {
				wordsAdded++;
			}
		}

		return p;
	}

	public static void main(String[] args) {
		WordList words = new JSONLoader().loadWordListFromFilename("src/main/resources/simple.json");

		// "src/main/resources/filler.json");
		CrosswordPuzzleBuilder builder = new CrosswordPuzzleBuilder();

		builder.setSize(15);
		builder.addWordList(words);

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
