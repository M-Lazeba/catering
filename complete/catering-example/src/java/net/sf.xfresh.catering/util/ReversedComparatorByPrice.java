package net.sf.xfresh.catering.util;

import net.sf.xfresh.catering.model.Position;

import java.util.Comparator;

public class ReversedComparatorByPrice implements Comparator<Position> {
    public int compare(Position a, Position b) {
        return -new Integer(a.getPrice()).compareTo(new Integer(b.getPrice()));
    }
}
