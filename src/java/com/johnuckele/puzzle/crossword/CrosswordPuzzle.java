package com.johnuckele.puzzle.crossword;

public class CrosswordPuzzle {
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

    private SymmetryDescription getSymmetryDescription() {
	return new SymmetryDescription();
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
		    _horizontalAnswers.getScore() + _verticalAnswers.getScore())
		    .append('\n');
	    sb.append(this.getSymmetryDescription()).append('\n');
	}
	return sb.toString();
    }

    public static void main(String[] args) {
	System.out.println(new CrosswordPuzzle(15).toString(true));
    }
}
