package com.johnuckele.puzzle.crossword;

import org.junit.Assert;
import org.junit.Test;

public class CrosswordSymmetryTest {

	@Test
	public void testEmptyPuzzle() {
		// Setup puzzle
		CrosswordPuzzle puzzle = new CrosswordPuzzle(15);
		System.out.println(puzzle.toString(true));
		puzzle.blockOpenSpaces();

		SymmetryDescription sym = puzzle.getSymmetryDescription();
		for (Symmetry s : Symmetry.values()) {
			Assert.assertTrue(sym.is(s));
			Assert.assertEquals(sym.get(s), 1, 0);
		}
	}

	@Test
	public void testUnsymmetricPuzzle() {
		// Setup puzzle
		CrosswordPuzzle puzzle = new CrosswordPuzzle(9);
		puzzle.placeWord(new Word("donkey", 5), 2, 3, Direction.VERTICAL);
		System.out.println(puzzle.toString(true));
		puzzle.blockOpenSpaces();

		// Test Symmetries
		SymmetryDescription sym = puzzle.getSymmetryDescription();
		for (Symmetry s : Symmetry.values()) {
			Assert.assertFalse(sym.is(s));
			Assert.assertTrue(sym.get(s) < 1);
		}
	}

	@Test
	public void testVerticallySymmetricPuzzle() {
		// Setup puzzle
		CrosswordPuzzle puzzle = new CrosswordPuzzle(7);
		puzzle.placeWord(new Word("donkey", 5), 0, 0, Direction.VERTICAL);
		puzzle.placeWord(new Word("monkey", 5), 0, 6, Direction.VERTICAL);
		System.out.println(puzzle.toString(true));
		puzzle.blockOpenSpaces();

		// Test Symmetries
		SymmetryDescription sym = puzzle.getSymmetryDescription();
		Assert.assertFalse(sym.is(Symmetry.HORIZONTAL));
		Assert.assertTrue(sym.get(Symmetry.HORIZONTAL) < 1);
		Assert.assertTrue(sym.is(Symmetry.VERTICAL));
		Assert.assertTrue(sym.get(Symmetry.VERTICAL) == 1);
		Assert.assertFalse(sym.is(Symmetry.TWO_FOLD_ROTATIONAL));
		Assert.assertTrue(sym.get(Symmetry.TWO_FOLD_ROTATIONAL) < 1);
	}

	@Test
	public void testHorizontallySymmetricPuzzle() {
		// Setup puzzle
		CrosswordPuzzle puzzle = new CrosswordPuzzle(7);
		puzzle.placeWord(new Word("donkey", 5), 2, 1, Direction.HORIZONTAL);
		puzzle.placeWord(new Word("monkey", 5), 4, 1, Direction.HORIZONTAL);
		System.out.println(puzzle.toString(true));
		puzzle.blockOpenSpaces();

		// Test Symmetries
		SymmetryDescription sym = puzzle.getSymmetryDescription();
		Assert.assertTrue(sym.is(Symmetry.HORIZONTAL));
		Assert.assertTrue(sym.get(Symmetry.HORIZONTAL) == 1);
		Assert.assertFalse(sym.is(Symmetry.VERTICAL));
		Assert.assertTrue(sym.get(Symmetry.VERTICAL) < 1);
		Assert.assertFalse(sym.is(Symmetry.TWO_FOLD_ROTATIONAL));
		Assert.assertTrue(sym.get(Symmetry.TWO_FOLD_ROTATIONAL) < 1);
	}

	@Test
	public void testTwoFoldRotionationallySymmetricPuzzle() {
		// Setup puzzle
		CrosswordPuzzle puzzle = new CrosswordPuzzle(11);
		puzzle.placeWord(new Word("donkey", 5), 1, 1, Direction.VERTICAL);
		puzzle.placeWord(new Word("monkey", 5), 4, 9, Direction.VERTICAL);
		System.out.println(puzzle.toString(true));
		puzzle.blockOpenSpaces();

		// Test Symmetries
		SymmetryDescription sym = puzzle.getSymmetryDescription();
		Assert.assertFalse(sym.is(Symmetry.HORIZONTAL));
		Assert.assertTrue(sym.get(Symmetry.HORIZONTAL) < 1);
		Assert.assertFalse(sym.is(Symmetry.VERTICAL));
		Assert.assertTrue(sym.get(Symmetry.VERTICAL) < 1);
		Assert.assertTrue(sym.is(Symmetry.TWO_FOLD_ROTATIONAL));
		Assert.assertTrue(sym.get(Symmetry.TWO_FOLD_ROTATIONAL) == 1);
	}
}
