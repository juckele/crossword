package com.johnuckele.puzzle.crossword;

/**
 * A PlacedWord object represents the physical location of a Word in a
 * CrosswordPuzzle.
 */
public class PlacedWord extends Word implements Comparable<PlacedWord> {
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
	public int compareTo(PlacedWord that) {
		// TODO: Monica, please finish this method. I've provided explanation
		// for the motivation in comments.

		// We need to be able to sort word placements so that we can
		// correctly number the clues in the puzzle. Look at the numbering
		// scheme used in this website:
		// http://www.nytimes.com/crosswords/game/2007/10/30/daily/
		// If the numbering scheme is non-obvious, looking at some other
		// crosswords or guides will make it more obvious You can also try this
		// resource: http://www.wikihow.com/Make-Crossword-Puzzles

		// One thing we notice is that Horizontal and Vertical clues are
		// interleaved in the numbering scheme, so this will actually inform the
		// structure of how we store PlacedWord objects in the
		// CrosswordPuzzle. Instead of storing one list for Vertical Clues and
		// one list for Horizontal clues, we probably need to store one big list
		// that we keep sorted on insertion. This means that when we render the
		// vertical and horizontal clue lists, we would split them apart at that
		// point (instead of storing them apart). So... When implementing
		// compareTo, you should consider that Vertical and Horizontal words
		// will be in the same list.
		return 0;
	}
}
