package com.johnuckele.puzzle.crossword;

public class CrosswordPuzzle {
	public static final char BLOCKED = ' ';
	public static final char CLEAR = 0;
	public static final char MUST_USE = '*';
	private int _size;
	private char[][] _letterGrid;
	private WordList _answers;
	private int _verticalWords;
	private int _horizontalWords;
	private boolean _enforceSymmetry;

	public CrosswordPuzzle(int size) {
		_size = size;
		_letterGrid = new char[_size][_size];
		_answers = new WordList();
	}

	public int getVerticalWords() {
		return _verticalWords;
	}

	public int getHorizontalWords() {
		return _horizontalWords;
	}

	public boolean canPlaceWord(Word word, int row, int col, Direction direction) {
		// @formatter:off
		return
				checkBounds(word, row, col, direction)
				&& lettersAreConflictFree(word, row, col, direction)
				&& hasAbutmentClearance(word, row, col, direction)
				&& checkBoundsForEnforcedSymmetry(word, row, col, direction)
				&& lettersAreConflictFreeForEnforcedSymmetry(word, row, col, direction)
				&& hasAbutmentClearanceForEnforcedSymmetry(word, row, col, direction);
		// @formatter:on
	}

	private boolean checkBounds(Word word, int row, int col, Direction direction) {
		if (row < 0 || col < 0 || row >= _size || col >= _size
				|| (direction == Direction.VERTICAL && row + word.getLength() > _size)
				|| (direction == Direction.HORIZONTAL && col + word.getLength() > _size)) {
			return false;
		}
		return true;
	}

	private boolean checkBoundsForEnforcedSymmetry(Word word, int row, int col, Direction direction) {
		if (!_enforceSymmetry) {
			return true;
		}
		int symmetricRow = _size - row - 1;
		int symmetricCol = _size - col - 1;
		int rowOffset = direction == Direction.VERTICAL ? word.getLength() : 0;
		int colOffset = direction == Direction.HORIZONTAL ? word.getLength() : 0;
		if (row <= _size / 2 && col <= _size / 2 && row + rowOffset >= _size / 2 && col + colOffset >= _size / 2) {
			return row + rowOffset == symmetricRow && col + colOffset == symmetricCol;
		}
		return true;
	}

	private boolean lettersAreConflictFree(Word word, int row, int col, Direction direction) {
		int rowOffset = 0;
		int colOffset = 0;
		String string = word.getString();
		for (int i = 0; i < word.getLength(); i++) {
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
		return true;
	}

	private boolean lettersAreConflictFreeForEnforcedSymmetry(Word word, int row, int col, Direction direction) {
		if (!_enforceSymmetry) {
			return true;
		}
		row = _size - row - 1;
		col = _size - col - 1;
		int rowOffset = 0;
		int colOffset = 0;
		for (int i = 0; i < word.getLength(); i++) {
			char currentLetter = _letterGrid[row + rowOffset][col + colOffset];
			if (currentLetter != CLEAR && currentLetter != MUST_USE && currentLetter == BLOCKED) {
				return false;
			}
			if (direction == Direction.VERTICAL) {
				rowOffset--;
			} else {
				colOffset--;
			}
		}
		return true;
	}

	private boolean hasAbutmentClearance(Word word, int row, int col, Direction direction) {
		int length = word.getLength();
		if (direction == Direction.VERTICAL) {
			if (row > 0) {
				if (_letterGrid[row - 1][col] != BLOCKED && _letterGrid[row - 1][col] != CLEAR) {
					return false;
				}
			}
			if (row + length < _size) {
				if (_letterGrid[row + length][col] != BLOCKED && _letterGrid[row + length][col] != CLEAR) {
					return false;
				}
			}
		} else {
			if (col > 0) {
				if (_letterGrid[row][col - 1] != BLOCKED && _letterGrid[row][col - 1] != CLEAR) {
					return false;
				}
			}
			if (col + length < _size) {
				if (_letterGrid[row][col + length] != BLOCKED && _letterGrid[row][col + length] != CLEAR) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean hasAbutmentClearanceForEnforcedSymmetry(Word word, int row, int col, Direction direction) {
		if (!_enforceSymmetry) {
			return true;
		}
		row = _size - row - 1;
		col = _size - col - 1;
		int length = word.getLength();
		if (direction == Direction.VERTICAL) {
			if (row < _size - 1) {
				if (_letterGrid[row + 1][col] != BLOCKED && _letterGrid[row + 1][col] != CLEAR) {
					return false;
				}
			}
			if (row - length >= 0) {
				if (_letterGrid[row - length][col] != BLOCKED && _letterGrid[row - length][col] != CLEAR) {
					return false;
				}
			}
		} else {
			if (col < _size - 1) {
				if (_letterGrid[row][col + 1] != BLOCKED && _letterGrid[row][col + 1] != CLEAR) {
					return false;
				}
			}
			if (col - length >= 0) {
				if (_letterGrid[row][col - length] != BLOCKED && _letterGrid[row][col - length] != CLEAR) {
					return false;
				}
			}
		}
		return true;
	}

	public void placeWord(Word word, int row, int col, Direction direction) {
		if (!canPlaceWord(word, row, col, direction)) {
			throw new IllegalStateException("Word cannot be placed");
		}
		addToLetterGrid(word, row, col, direction);
		blockEndsOfWord(word, row, col, direction);
		addToAnswerList(word, row, col, direction);
		addToLetterGridForEnforcedSymmetry(word, row, col, direction);
		blockEndsOfWordForEnforcedSymmetry(word, row, col, direction);
	}

	private void addToLetterGrid(Word word, int row, int col, Direction direction) {
		String string = word.getString();
		int rowOffset = 0;
		int colOffset = 0;
		for (int i = 0; i < string.length(); i++) {
			_letterGrid[row + rowOffset][col + colOffset] = string.charAt(i);
			if (direction == Direction.VERTICAL) {
				rowOffset++;
			} else {
				colOffset++;
			}
		}
	}

	private void addToLetterGridForEnforcedSymmetry(Word word, int row, int col, Direction direction) {
		if (!_enforceSymmetry) {
			return;
		}
		row = _size - row - 1;
		col = _size - col - 1;
		int rowOffset = 0;
		int colOffset = 0;
		int length = word.getLength();
		for (int i = 0; i < length; i++) {
			if (_letterGrid[row + rowOffset][col + colOffset] == CLEAR) {
				_letterGrid[row + rowOffset][col + colOffset] = MUST_USE;
			}
			if (direction == Direction.VERTICAL) {
				rowOffset--;
			} else {
				colOffset--;
			}
		}
	}

	private void blockEndsOfWord(Word word, int row, int col, Direction direction) {
		int length = word.getLength();
		if (direction == Direction.VERTICAL) {
			if (row > 0) {
				_letterGrid[row - 1][col] = BLOCKED;
			}
			if (row + length < _size) {
				_letterGrid[row + length][col] = BLOCKED;
			}
		} else {
			if (col > 0) {
				_letterGrid[row][col - 1] = BLOCKED;
			}
			if (col + length < _size) {
				_letterGrid[row][col + length] = BLOCKED;
			}
		}
	}

	private void blockEndsOfWordForEnforcedSymmetry(Word word, int row, int col, Direction direction) {
		if (!_enforceSymmetry) {
			return;
		}
		row = _size - row - 1;
		col = _size - col - 1;
		int length = word.getLength();
		if (direction == Direction.VERTICAL) {
			if (row < _size - 1) {
				_letterGrid[row + 1][col] = BLOCKED;
			}
			if (row - length >= 0) {
				_letterGrid[row - length][col] = BLOCKED;
			}
		} else {
			if (col < _size - 1) {
				_letterGrid[row][col + 1] = BLOCKED;
			}
			if (col - length >= 0) {
				_letterGrid[row][col - length] = BLOCKED;
			}
		}
	}

	private void addToAnswerList(Word word, int row, int col, Direction direction) {
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

	public void setEnforceSymmetry(boolean enforce) {
		_enforceSymmetry = enforce;
	}

	public double measureSymmetry() {
		double measure = 1.0;
		double perSquare = 1.0 / (this._size * this._size);
		for (int i = 0; i < _size; i++) {
			for (int j = 0; j < _size; j++) {
				char c = _letterGrid[i][j];
				char mirror = _letterGrid[_size - i - 1][_size - j - 1];
				if (c == BLOCKED && mirror != BLOCKED) {
					measure -= perSquare;
				}
			}
		}
		return measure;
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
			sb.append("Symmetry:");
			sb.append(this.measureSymmetry()).append('\n');
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
