package com.johnuckele.puzzle.crossword;

import java.util.HashMap;

public class CrosswordPuzzle {
	public static final char BLOCKED = ' ';
	public static final char CLEAR = 0;
	public static final char MUST_USE = '*';
	private int _size;
	private char[][] _letterGrid;
	private WordList _answers;
	private int _verticalWords;
	private int _horizontalWords;

	public CrosswordPuzzle(int size) {
		_size = size;
		_letterGrid = new char[_size][_size];
		_answers = new WordList();
	}

	public boolean canPlaceWord(Word word, int row, int col, Direction direction) {
		String string = word.getWord();
		int rowOffset = 0;
		int colOffset = 0;

		// check bounds
		if (row < 0 || col < 0 || row >= _size || col >= _size
				|| (direction == Direction.VERTICAL && row + string.length() > _size)
				|| (direction == Direction.HORIZONTAL && col + string.length() > _size)) {
			return false;
		}

		// check for letter conflicts
		for (int i = 0; i < string.length(); i++) {
			char currentLetter = _letterGrid[row + rowOffset][col + colOffset];
			if (currentLetter != CLEAR && currentLetter != MUST_USE && currentLetter != string.charAt(i)) {
				return false;
			}
			if (direction == Direction.VERTICAL) {
				rowOffset++;
			} else {
				colOffset++;
			}
		}

		// check to abutment conflicts
		if (direction == Direction.VERTICAL) {
			if (row > 0) {
				if (_letterGrid[row - 1][col] != BLOCKED && _letterGrid[row - 1][col] != CLEAR) {
					return false;
				}
			}
			if (row + rowOffset < _size) {
				if (_letterGrid[row + rowOffset][col] != BLOCKED && _letterGrid[row + rowOffset][col] != CLEAR) {
					return false;
				}
			}
		} else {
			if (col > 0) {
				if (_letterGrid[row][col - 1] != BLOCKED && _letterGrid[row][col - 1] != CLEAR) {
					return false;
				}
			}
			if (col + colOffset < _size) {
				if (_letterGrid[row][col + colOffset] != BLOCKED && _letterGrid[row][col + colOffset] != CLEAR) {
					return false;
				}
			}
		}

		// no conflicts, it fits!
		return true;
	}

	public void placeWord(Word word, int row, int col, Direction direction) {
		if (!canPlaceWord(word, row, col, direction)) {
			throw new IllegalStateException("Word cannot be placed");
		}

		String string = word.getWord();
		int rowOffset = 0;
		int colOffset = 0;

		// write the word into the grid
		for (int i = 0; i < string.length(); i++) {
			_letterGrid[row + rowOffset][col + colOffset] = string.charAt(i);
			if (direction == Direction.VERTICAL) {
				rowOffset++;
			} else {
				colOffset++;
			}
		}

		// block space before and after word
		if (direction == Direction.VERTICAL) {
			if (row > 0) {
				_letterGrid[row - 1][col] = BLOCKED;
			}
			if (row + rowOffset < _size) {
				_letterGrid[row + rowOffset][col] = BLOCKED;
			}
		} else {
			if (col > 0) {
				_letterGrid[row][col - 1] = BLOCKED;
			}
			if (col + colOffset < _size) {
				_letterGrid[row][col + colOffset] = BLOCKED;
			}
		}

		// Add this to our list of answers
		_answers.add(new PlacedWord(word, row, col, direction));
		if (direction == Direction.VERTICAL) {
			_verticalWords++;
		} else {
			_horizontalWords++;
		}
	}

	public void blockOpenSpaces() {
		for (int i = 0; i < _size; i++) {
			for (int j = 0; j < _size; j++) {
				if (_letterGrid[i][j] == CLEAR) {
					_letterGrid[i][j] = BLOCKED;
				}
			}
		}
	}

	public SymmetryDescription getSymmetryDescription() {
		HashMap<Symmetry, Boolean> symmetries = new HashMap<Symmetry, Boolean>();
		symmetries.put(Symmetry.HORIZONTAL, isHorizontallySymmetric());
		symmetries.put(Symmetry.VERTICAL, isVerticallySymmetric());
		symmetries.put(Symmetry.TWO_FOLD_ROTATIONAL, isTwoFoldRotationallySymmetric());
		return new SymmetryDescription(symmetries);
	}

	private boolean isVerticallySymmetric() {
		for (int i = 0; i < _size; i++) {
			for (int j = 0; j < _size; j++) {
				char c = _letterGrid[i][j];
				char mirror = _letterGrid[i][_size - j - 1];
				if (c == BLOCKED && mirror != BLOCKED) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isHorizontallySymmetric() {
		for (int i = 0; i < _size; i++) {
			for (int j = 0; j < _size; j++) {
				char c = _letterGrid[i][j];
				char mirror = _letterGrid[_size - i - 1][j];
				if (c == BLOCKED && mirror != BLOCKED) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean isTwoFoldRotationallySymmetric() {
		for (int i = 0; i < _size; i++) {
			for (int j = 0; j < _size; j++) {
				char c = _letterGrid[i][j];
				char mirror = _letterGrid[_size - i - 1][_size - j - 1];
				if (c == BLOCKED && mirror != BLOCKED) {
					return false;
				}
			}
		}
		return true;
	}

	public String toString() {
		return this.toString(false);
	}

	public String toString(boolean verbose) {
		StringBuilder sb = new StringBuilder();
		if (verbose) {
			sb.append("CrosswordPuzzle: ");
			sb.append(_answers.size()).append(" words");
			// TODO: Readd the capacity to list counts of vertical and
			// horizontal answers
			sb.append('\n');
		}
		for (int i = 0; i < _size; i++) {
			sb.append('.');
			for (int j = 0; j < _size; j++) {
				if (_letterGrid[i][j] == CLEAR) {
					sb.append('-');
				} else {
					sb.append(_letterGrid[i][j]);
				}
				sb.append('.');
			}
			sb.append('\n');
		}
		if (verbose) {
			sb.append("Total score is ");
			sb.append(_answers.getTotalScore()).append('\n');
			sb.append(this.getSymmetryDescription()).append('\n');
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		CrosswordPuzzle puzzle = new CrosswordPuzzle(15);
		puzzle.placeWord(new Word("donkey", 5), 2, 3, Direction.VERTICAL);
		puzzle.placeWord(new Word("monkey", 5), 3, 2, Direction.HORIZONTAL);
		System.out.println(puzzle.toString(true));
	}

	public int getVerticalWords() {
		return _verticalWords;
	}

	public int getHorizontalWords() {
		return _horizontalWords;
	}

}
