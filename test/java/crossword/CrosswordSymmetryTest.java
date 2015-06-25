package crossword;

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
	CrosswordPuzzle puzzle = new CrosswordPuzzle(15);
	SymmetryDescription sym = puzzle.getSymmetryDescription();
	for (Symmetry s : Symmetry.values()) {
	    Assert.assertTrue(sym.is(s));
	}
    }

    @Test
    public void testUnsymmetricPuzzle() {
	CrosswordPuzzle puzzle = new CrosswordPuzzle(15);
	puzzle.placeWord(new Word("donkey", 5), 2, 3, Direction.VERTICAL);
	SymmetryDescription sym = puzzle.getSymmetryDescription();
	for (Symmetry s : Symmetry.values()) {
	    Assert.assertFalse(sym.is(s));
	}
    }

    @Test
    public void testDiagonalNWtoSE() {
	CrosswordPuzzle puzzle = new CrosswordPuzzle(15);
	puzzle.placeWord(new Word("donkey", 5), 2, 3, Direction.VERTICAL);
	puzzle.placeWord(new Word("monkey", 5), 3, 2, Direction.HORIZONTAL);
	SymmetryDescription sym = puzzle.getSymmetryDescription();
	Assert.assertFalse(sym.is(Symmetry.VERTICAL));
    }
}
