package com.johnuckele.puzzle.crossword;

/**
 * A PlacedWord object represents the physical location of a Word in a
 * CrosswordPuzzle.
 */
public class PlacedWord extends Word implements Comparable<Word> {
	private final int _row;
	private final int _col;
	private final Direction _direction;

	public PlacedWord(Word word, int row, int col, Direction direction) {
		this(word.toString(), word.getScore(), row, col, direction);
	}

	public PlacedWord(String word, int score, int row, int col, Direction direction) {
		super(word, score);
		_row = row;
		_col = col;
		_direction = direction;
	}

	public int getRow() {
		return _row;
	}

	public int getCol() {
		return _col;
	}

	public Direction getDirection() {
		return _direction;
	}

	@Override
	public int compareTo(Word untypedThat) {
		if (untypedThat instanceof PlacedWord) {
			PlacedWord that = (PlacedWord) untypedThat;
			if (this._row < that._row) {
				return -3;
			} else if (this._row > that._row) {
				return 3;
			} else if (this._col < that._col) {
				return -2;
			} else if (this._col > that._col) {
				return 2;
			} else if (this._direction != that._direction) {
				if (this._direction == Direction.HORIZONTAL) {
					return -1;
				} else {
					return 1;
				}
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}
}
