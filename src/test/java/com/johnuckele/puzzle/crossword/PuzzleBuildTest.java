package com.johnuckele.puzzle.crossword;

import org.junit.Assert;
import org.junit.Test;

public class PuzzleBuildTest {

	private static Word DOLPHIN = new Word("DOLPHIN", 20);
	private static Word DONKEY = new Word("DONKEY", 10);
	private static Word MONKEY = new Word("MONKEY", 12);
	private static Word MONKEYS = new Word("MONKEYS", 14);
	private static Word HE = new Word("HE", 0);
	private static Word SHE = new Word("SHE", 1);

	@Test
	public void canAddWords() {
		boolean canPlace;
		CrosswordPuzzle puzzle = new CrosswordPuzzle(15);

		// Place a word
		canPlace = puzzle.canPlaceWord(new Word("Dolphin", 20), 1, 1, Direction.VERTICAL);
		Assert.assertTrue("Placement should be possible", canPlace);
		puzzle.placeWord(DOLPHIN, 1, 1, Direction.VERTICAL);
	}

	@Test
	public void canCrossWords() {
		boolean canPlace;
		CrosswordPuzzle puzzle = new CrosswordPuzzle(15);

		// Place the first word
		canPlace = puzzle.canPlaceWord(DOLPHIN, 1, 2, Direction.VERTICAL);
		Assert.assertTrue("Placement should be possible", canPlace);
		puzzle.placeWord(DOLPHIN, 1, 2, Direction.VERTICAL);

		// Place a second word that crosses the first
		canPlace = puzzle.canPlaceWord(DONKEY, 2, 1, Direction.HORIZONTAL);
		Assert.assertTrue("Placement should be possible", canPlace);
		puzzle.placeWord(DONKEY, 2, 1, Direction.HORIZONTAL);
	}

	@Test
	public void cannotOverlapWords() {
		boolean canPlace;
		CrosswordPuzzle puzzle = new CrosswordPuzzle(15);

		// Place the first word
		canPlace = puzzle.canPlaceWord(MONKEY, 3, 3, Direction.HORIZONTAL);
		Assert.assertTrue("Placement should be possible", canPlace);
		puzzle.placeWord(MONKEY, 3, 3, Direction.HORIZONTAL);

		// Place a second word that overlaps the first
		canPlace = puzzle.canPlaceWord(MONKEYS, 3, 3, Direction.HORIZONTAL);
		Assert.assertFalse("Placement should not be possible", canPlace);
		try {
			puzzle.placeWord(MONKEYS, 3, 3, Direction.HORIZONTAL);
			Assert.fail("Placing an overlapping word should fail");
		} catch (IllegalStateException e) {
			Assert.assertNotNull("Error expected", e);
		}

		// Place the first word
		canPlace = puzzle.canPlaceWord(HE, 5, 1, Direction.VERTICAL);
		Assert.assertTrue("Placement should be possible", canPlace);
		puzzle.placeWord(HE, 5, 1, Direction.VERTICAL);

		// Place a second word that overlaps the first
		canPlace = puzzle.canPlaceWord(SHE, 4, 1, Direction.VERTICAL);
		Assert.assertFalse("Placement should not be possible", canPlace);
		try {
			puzzle.placeWord(SHE, 4, 1, Direction.VERTICAL);
			Assert.fail("Placing an overlapping word should fail");
		} catch (IllegalStateException e) {
			Assert.assertNotNull("Error expected", e);
		}
	}

	@Test
	public void cannotAbutWords() {
		boolean canPlace;
		CrosswordPuzzle puzzle = new CrosswordPuzzle(15);

		// Place the first word
		canPlace = puzzle.canPlaceWord(MONKEY, 3, 3, Direction.HORIZONTAL);
		Assert.assertTrue("Placement should be possible", canPlace);
		puzzle.placeWord(MONKEY, 3, 3, Direction.HORIZONTAL);

		// Place a second word that abuts the first
		canPlace = puzzle.canPlaceWord(MONKEYS, 4, 4, Direction.VERTICAL);
		Assert.assertFalse("Placement should not be possible", canPlace);
		try {
			puzzle.placeWord(MONKEYS, 4, 4, Direction.VERTICAL);
			Assert.fail("Placing an overlapping word should fail");
		} catch (IllegalStateException e) {
			Assert.assertNotNull("Error expected", e);
		}
	}

	@Test
	public void cannotPlaceWordInBlockedSpaces() {
		boolean canPlace;
		CrosswordPuzzle puzzle = new CrosswordPuzzle(15);

		// Block all spaces
		puzzle.blockOpenSpaces();

		// Place a word in blocked spaces
		canPlace = puzzle.canPlaceWord(DOLPHIN, 1, 1, Direction.VERTICAL);
		Assert.assertFalse("Placement should not be possible", canPlace);
		try {
			puzzle.placeWord(DOLPHIN, 1, 1, Direction.VERTICAL);
			Assert.fail("Placing a word in blocked spaces should fail");
		} catch (IllegalStateException e) {
			Assert.assertNotNull("Error expected", e);
		}
	}
}
