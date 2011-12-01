package net.sf.xfresh.catering.model;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/17/11
 * Time: 4:51 PM
 *
 * @author Anton Ohitin
 */
public class PositionTag {
    public int id;
    private String value;

    public PositionTag(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
