package com.johnuckele.puzzle.crossword;

import java.util.HashMap;
import java.util.Map;

public class SymmetryDescription {
    private Map<Symmetry, Boolean> symmetries;

    /**
     * generates a new SymmetryDescription with all symmetries initially set to
     * false
     */
    public SymmetryDescription() {
	this(false);
    }

    /**
     * generates a new SymmetryDescription with all symmetries initially set to
     * a specified value
     */
    public SymmetryDescription(boolean startingValues) {
	symmetries = new HashMap<Symmetry, Boolean>(Symmetry.values().length);
	for (Symmetry s : Symmetry.values()) {
	    symmetries.put(s, startingValues);
	}
    }

    /**
     * checks for a particular kind of symmetry in the symmetry description.
     * 
     * @param s
     *            the symmetry to check for
     * @return boolean
     */
    public boolean is(Symmetry s) {
	return symmetries.get(s);
    }

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("SymmetryDescription: {");
	String previous = "";
	for (Symmetry s : Symmetry.values()) {
	    sb.append(previous).append(s).append(":").append(symmetries.get(s));
	    previous = ", ";
	}
	sb.append("}");
	return sb.toString();
    }

    /*
     * Demos the SymmetryDescription toString
     */
    public static void main(String[] args) {
	System.out.println(new SymmetryDescription());
    }
}
