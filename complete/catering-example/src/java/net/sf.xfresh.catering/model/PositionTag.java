package net.sf.xfresh.catering.model;

import net.sf.xfresh.core.xml.Tagable;
import net.sf.xfresh.core.xml.Xmler;

import java.io.StringWriter;
import java.util.Arrays;


/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/17/11
 * Time: 4:51 PM
 *
 * @author Anton Ohitin
 */
public class PositionTag implements Tagable {
    public int id;
    private String value;

    public PositionTag(int id, String value) {
        this.id = id;
        this.value = value;
    }


    public Xmler.Tag asTag() {
        return Xmler.tag("tag", Arrays.asList(
                Xmler.tag("id", ((Integer) id).toString()),
                Xmler.tag("value", value)
        ));
    }

    public String getValue() {
        return value;
    }
}
