package com.johnuckele.puzzle.crossword;

import org.junit.Assert;
import org.junit.Test;

public class SymmetryEnforcementTest {
	private static Word DONKEY = new Word("DONKEY", 10);
	private static Word MONKEY = new Word("MONKEY", 12);
	private static Word SPOON = new Word("SPOON", 4);
	private static Word DEEDS = new Word("DEEDS", 5);

	@Test
	public void test2FoldSymmetryEnforcement() {
		boolean canPlace;
		CrosswordPuzzle puzzle = new CrosswordPuzzle(12);

		canPlace = puzzle.canPlaceWord(DONKEY, 9, 6, Direction.HORIZONTAL);
		Assert.assertTrue("Placement should be possible", canPlace);
		puzzle.placeWord(DONKEY, 9, 6, Direction.HORIZONTAL);

		canPlace = puzzle.canPlaceWord(DEEDS, 7, 10, Direction.VERTICAL);
		Assert.assertTrue("Placement should be possible", canPlace);
		puzzle.placeWord(DEEDS, 7, 10, Direction.VERTICAL);

		canPlace = puzzle.canPlaceWord(MONKEY, 2, 0, Direction.HORIZONTAL);
		Assert.assertTrue("Placement should be possible", canPlace);
		puzzle.placeWord(MONKEY, 2, 0, Direction.HORIZONTAL);

		canPlace = puzzle.canPlaceWord(SPOON, 0, 1, Direction.VERTICAL);
		Assert.assertTrue("Placement should be possible", canPlace);
		puzzle.placeWord(SPOON, 0, 1, Direction.VERTICAL);
		System.out.println(canPlace);

		System.out.println(puzzle);
	}

}
