package com.johnuckele.puzzle.crossword;

import org.junit.Assert;
import org.junit.Test;

import com.johnuckele.puzzle.crossword.CrosswordPuzzle;
import com.johnuckele.puzzle.crossword.Direction;
import com.johnuckele.puzzle.crossword.Symmetry;
import com.johnuckele.puzzle.crossword.SymmetryDescription;
import com.johnuckele.puzzle.crossword.Word;

public class CrosswordSymmetryTest {

    @Test
    public void testEmptyPuzzle() {
	System.out.println("test E");
	CrosswordPuzzle puzzle = new CrosswordPuzzle(15);
	SymmetryDescription sym = puzzle.getSymmetryDescription();
	for (Symmetry s : Symmetry.values()) {
	    Assert.assertTrue(sym.is(s));
	}
    }

    @Test
    public void testUnsymmetricPuzzle() {
	System.out.println("test U");
	CrosswordPuzzle puzzle = new CrosswordPuzzle(15);
	puzzle.placeWord(new Word("donkey", 5), 2, 3, Direction.VERTICAL);
	SymmetryDescription sym = puzzle.getSymmetryDescription();
	for (Symmetry s : Symmetry.values()) {
	    Assert.assertFalse(sym.is(s));
	}
    }

    @Test
    public void testHorizontallySymmetricPuzzle() {
	System.out.println("test H");
	CrosswordPuzzle puzzle = new CrosswordPuzzle(7);
	puzzle.placeWord(new Word("donkey", 5), 0, 0, Direction.VERTICAL);
	puzzle.placeWord(new Word("monkey", 5), 0, 6, Direction.VERTICAL);
	System.out.println(puzzle);
	SymmetryDescription sym = puzzle.getSymmetryDescription();
	Assert.assertTrue(sym.is(Symmetry.HORIZONTAL));
	Assert.assertFalse(sym.is(Symmetry.VERTICAL));
	Assert.assertFalse(sym.is(Symmetry.TWO_FOLD_ROTATIONAL));
    }

    @Test
    public void testVerticallySymmetricPuzzle() {
	System.out.println("test V");
	CrosswordPuzzle puzzle = new CrosswordPuzzle(7);
	puzzle.placeWord(new Word("donkey", 5), 2, 1, Direction.HORIZONTAL);
	puzzle.placeWord(new Word("monkey", 5), 4, 1, Direction.HORIZONTAL);
	System.out.println(puzzle);
	SymmetryDescription sym = puzzle.getSymmetryDescription();
	Assert.assertFalse(sym.is(Symmetry.HORIZONTAL));
	Assert.assertTrue(sym.is(Symmetry.VERTICAL));
	Assert.assertFalse(sym.is(Symmetry.TWO_FOLD_ROTATIONAL));
    }

    @Test
    public void testTwoFoldRotionationallySymmetricPuzzle() {
	System.out.println("test R");
	CrosswordPuzzle puzzle = new CrosswordPuzzle(15);
	puzzle.placeWord(new Word("donkey", 5), 1, 3, Direction.VERTICAL);
	puzzle.placeWord(new Word("monkey", 5), 3, 2, Direction.VERTICAL);
	System.out.println(puzzle);
	SymmetryDescription sym = puzzle.getSymmetryDescription();
	Assert.assertFalse(sym.is(Symmetry.HORIZONTAL));
	Assert.assertFalse(sym.is(Symmetry.VERTICAL));
	Assert.assertTrue(sym.is(Symmetry.TWO_FOLD_ROTATIONAL));
    }
}
