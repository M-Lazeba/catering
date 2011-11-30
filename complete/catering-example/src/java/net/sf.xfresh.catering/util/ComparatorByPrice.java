package net.sf.xfresh.catering.util;

import net.sf.xfresh.catering.model.Position;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/20/11
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */

public class ComparatorByPrice implements Comparator<Position> {
    public int compare(Position a, Position b) {
        return new Integer(a.getPrice()).compareTo(new Integer(b.getPrice()));
    }
}

