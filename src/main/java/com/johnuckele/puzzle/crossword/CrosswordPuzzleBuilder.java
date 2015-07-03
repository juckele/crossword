package com.johnuckele.puzzle.crossword;

public class CrosswordPuzzleBuilder {
	CrosswordPuzzle puzzle;
	private static int _size;

	public CrosswordPuzzleBuilder(int size) {
		_size = size;
		puzzle = new CrosswordPuzzle(size);
	}

	public static CrosswordPuzzle build(CrosswordPuzzleBuilder build, Word w) {
		CrosswordPuzzle p = build.puzzle;
		Word word = w;

		// pick random direction
		Direction direction;
		int directionFactor = (int) (Math.random() * 2);

		if (directionFactor == 0) {
			direction = Direction.VERTICAL;
		} else {
			direction = Direction.HORIZONTAL;
		}

		// pick random spot
		int randomFactor = _size - word.getLength();

		if (direction == Direction.HORIZONTAL) {
			int row = (int) (Math.random() * _size);
			int col = (int) (Math.random() * randomFactor);

			p.placeWord(word, row, col, direction);
		} else {
			int row = (int) (Math.random() * randomFactor);
			int col = (int) (Math.random() * _size);

			p.placeWord(word, row, col, direction);
		}

		return p;
	}

	public static Word getRandomWord(WordList wl) {
		WordList words = wl;

		int randomFactor = (int) (Math.random() * words.size());
		Word randomWord = words.get(randomFactor);
		return randomWord;
	}

	public static void main(String[] args) {
		WordList words = new JSONLoader().loadWordListFromFilename("src/main/resources/simple.json");
		Word w = getRandomWord(words);

		CrosswordPuzzleBuilder build = new CrosswordPuzzleBuilder(15);
		CrosswordPuzzle testPuzzle = build(build, w);
		System.out.println(testPuzzle.toString(true));

	}

}
