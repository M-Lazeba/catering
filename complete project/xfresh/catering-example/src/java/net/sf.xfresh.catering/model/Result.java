package net.sf.xfresh.catering.model;

import net.sf.xfresh.core.xml.Tagable;
import net.sf.xfresh.core.xml.Xmler;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/19/11
 * Time: 6:48 PM
 * @author Anton Ohitin
 */
public class Result implements Tagable{

    private int count;
    private Request request;

    public Result(int count, Request request) {
        this.count = count;
        this.request = request;
    }

    public Xmler.Tag asTag() {
        return Xmler.tag("result", Arrays.asList(
                Xmler.tag("count", ((Integer) count).toString()),
                Xmler.tag("request", request.getQuery()),
                Xmler.tag("type", request.getType())
        ));
    }
}
