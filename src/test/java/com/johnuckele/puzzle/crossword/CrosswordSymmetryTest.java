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

		Assert.assertTrue(puzzle.measureSymmetry() == 1);
	}

	@Test
	public void testUnsymmetricPuzzle() {
		// Setup puzzle
		CrosswordPuzzle puzzle = new CrosswordPuzzle(9);
		puzzle.placeWord(new Word("donkey", 5), 2, 3, Direction.VERTICAL);
		System.out.println(puzzle.toString(true));
		puzzle.blockOpenSpaces();

		// Test Symmetries
		Assert.assertTrue(puzzle.measureSymmetry() < 1);
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
		Assert.assertTrue(puzzle.measureSymmetry() < 1);
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
		Assert.assertTrue(puzzle.measureSymmetry() < 1);
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
		Assert.assertTrue(puzzle.measureSymmetry() == 1);
	}
}
