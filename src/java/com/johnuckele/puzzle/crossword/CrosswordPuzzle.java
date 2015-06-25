package com.johnuckele.puzzle.crossword;

public class CrosswordPuzzle {
    public static final char BLOCKED = ' ';
    public static final char CLEAR = ' ';
    public static final char MUST_USE = ' ';
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
		|| (direction == Direction.VERTICAL && row + string.length() >= _size)
		|| (direction == Direction.HORIZONTAL && col + string.length() >= _size)) {
	    return false;
	}
	// check for letter conflicts
	for (int i = 0; i < string.length(); i++) {
	    if (_letterGrid[row][col] != 0
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

    public SymmetryDescription getSymmetryDescription() {
	return new SymmetryDescription(true);
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
	    for (int j = 0; j < _size; j++) {
		if (_letterGrid[i][j] == 0) {
		    sb.append(' ');
		} else {
		    sb.append(_letterGrid[i][j]);
		}
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
