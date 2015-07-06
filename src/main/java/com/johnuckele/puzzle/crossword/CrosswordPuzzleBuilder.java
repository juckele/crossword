package com.johnuckele.puzzle.crossword;

public class CrosswordPuzzleBuilder {
	private static int _size;
	private WordList _words;
	private int _subList;

	public CrosswordPuzzleBuilder() {

	}

	public void setSize(int size) {
		_size = size;
	}

	public void addWordList(WordList words) {
		_words = words;
		_subList = _words.size();
	}

	public Word getRandomWord() {

		int randomFactor = (int) (Math.random() * _subList);
		Word randomWord = _words.get(randomFactor);
		swapRandomWordLastWord(randomFactor);
		_subList--;
		return randomWord;
	}

	public void swapRandomWordLastWord(int index) {
		Word storeWord = _words.get(index);

		int indexLastElement = _words.size() - 1;
		Word lastWord = _words.get(indexLastElement);
		_words.set(index, lastWord);
		_words.set(indexLastElement, storeWord);
	}

	public Direction getRandomDirection() {
		Direction direction;
		int directionFactor = (int) (Math.random() * 2);

		if (directionFactor == 0) {
			direction = Direction.VERTICAL;
		} else {
			direction = Direction.HORIZONTAL;
		}
		return direction;

	}

	public void placeRandom(CrosswordPuzzle puzzle, Word word,
			Direction direction) {
		// pick random spot
		int randomFactor = _size - word.getLength();

		if (direction == Direction.HORIZONTAL) {
			int row = (int) (Math.random() * _size);
			int col = (int) (Math.random() * randomFactor);

			puzzle.placeWord(word, row, col, direction);
		} else {
			int row = (int) (Math.random() * randomFactor);
			int col = (int) (Math.random() * _size);

			puzzle.placeWord(word, row, col, direction);
		}
	}

	public void addWord(CrosswordPuzzle puzzle) {
		Word word = getRandomWord();
		Direction direction = getRandomDirection();
		placeRandom(puzzle, word, direction);
	}

	public CrosswordPuzzle build() {
		CrosswordPuzzle p = new CrosswordPuzzle(_size);
		addWord(p);
		addWord(p);

		return p;
	}

	public static void main(String[] args) {
		WordList words = new JSONLoader()
				.loadWordListFromFilename("src/main/resources/simple.json");
		CrosswordPuzzleBuilder builder = new CrosswordPuzzleBuilder();

		builder.setSize(15);
		builder.addWordList(words);

		CrosswordPuzzle testPuzzle1 = builder.build();
		System.out.println(testPuzzle1.toString(true));

		CrosswordPuzzle testPuzzle2 = builder.build();
		System.out.println(testPuzzle2.toString(true));

		// builder.setSize(11);
		// CrosswordPuzzle testPuzzle3 = builder.build();
		// System.out.println(testPuzzle3.toString(true));
	}

}
