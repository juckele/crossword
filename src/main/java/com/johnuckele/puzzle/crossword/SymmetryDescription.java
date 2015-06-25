package com.johnuckele.puzzle.crossword;

import java.util.HashMap;
import java.util.Map;

public class SymmetryDescription {
    private final Map<Symmetry, Boolean> _symmetries;

    /**
     * Creates a new SymmetryDescription object corresponding to a HashMap of of
     * kinds of symmetry to boolean values (true indicating symmetry, false
     * indicating asymmetry)
     * 
     * @param symmetries
     *            A hash map of Symmetry types to Boolean
     */
    public SymmetryDescription(HashMap<Symmetry, Boolean> symmetries) {
	_symmetries = new HashMap<Symmetry, Boolean>(Symmetry.values().length);
	for (Symmetry s : Symmetry.values()) {
	    if (symmetries.get(s) != null) {
		_symmetries.put(s, symmetries.get(s));
	    } else {
		throw new IllegalStateException(
			"HashMap objects used to construct SymmetryDescription objects must "
				+ "contain keys for all Symmetry values");
	    }
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
	return _symmetries.get(s);
    }

    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("SymmetryDescription: {");
	String previous = "";
	for (Symmetry s : Symmetry.values()) {
	    sb.append(previous).append(s).append(":")
		    .append(_symmetries.get(s));
	    previous = ", ";
	}
	sb.append("}");
	return sb.toString();
    }
}
