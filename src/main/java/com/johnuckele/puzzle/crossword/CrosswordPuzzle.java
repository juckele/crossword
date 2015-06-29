package com.johnuckele.puzzle.crossword;

import java.util.HashMap;

public class CrosswordPuzzle {
	public static final char BLOCKED = ' ';
	public static final char CLEAR = 0;
	public static final char MUST_USE = '*';
	private int _size;
	private char[][] _letterGrid;
	private WordList _horizontalAnswers;
	private WordList _verticalAnswers;

	public CrosswordPuzzle(int size) {
		_size = size;
		_letterGrid = new char[_size][_size];
		_horizontalAnswers = new WordList();
		_verticalAnswers = new WordList();
	}

	public boolean canPlaceWord(Word word, int row, int col, Direction direction) {
		String string = word.toString();
		// check bounds
		if (row < 0
				|| col < 0
				|| row >= _size
				|| col >= _size
				|| (direction == Direction.VERTICAL && row + string.length() > _size)
				|| (direction == Direction.HORIZONTAL && col + string.length() > _size)) {
			return false;
		}
		// check for letter conflicts
		for (int i = 0; i < string.length(); i++) {
			if (_letterGrid[row][col] != CLEAR
					&& _letterGrid[row][col] != MUST_USE
					&& _letterGrid[row][col] != string.charAt(i)) {
				return false;
			}
			if (direction == Direction.VERTICAL) {
				row++;
			} else {
				col++;
			}
		}
		// no conflicts, it fits!
		return true;
	}

	public void placeWord(Word word, int row, int col, Direction direction) {
		if (!canPlaceWord(word, row, col, direction)) {
			throw new IllegalStateException("Word cannot be placed");
		}
		String string = word.toString();
		for (int i = 0; i < string.length(); i++) {
			_letterGrid[row][col] = string.charAt(i);
			if (direction == Direction.VERTICAL) {
				row++;
			} else {
				col++;
			}
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
		// TODO: The below lines are obviously incorrect.
		symmetries.put(Symmetry.VERTICAL, isVerticallySymmetric());
		symmetries.put(Symmetry.TWO_FOLD_ROTATIONAL,
				isTwoFoldRotationallySymmetric());
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
			sb.append(_horizontalAnswers.size() + _verticalAnswers.size())
					.append(" words (");
			sb.append(_horizontalAnswers.size()).append(" horizontal & ");
			sb.append(_verticalAnswers.size()).append(" vertical)");

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
			sb.append(
					_horizontalAnswers.getTotalScore()
							+ _verticalAnswers.getTotalScore()).append('\n');
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

}
