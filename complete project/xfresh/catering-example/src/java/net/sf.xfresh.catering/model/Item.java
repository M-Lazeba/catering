package net.sf.xfresh.catering.model;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 10/2/11
 * Time: 7:55 PM
 */
public class Item {

    private final int uid;

    public Item(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item that = (Item) o;

        if (uid != that.uid) return false;

        return true;
    }

    public int hashCode() {
        return uid;
    }
}
