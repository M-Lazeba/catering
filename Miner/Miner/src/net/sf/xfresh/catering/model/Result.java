package net.sf.xfresh.catering.model;

//import net.sf.xfresh.core.xml.Tagable;
//import net.sf.xfresh.core.xml.Xmler;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/19/11
 * Time: 6:48 PM
 *
 * @author Anton Ohitin
 */
public class Result {

    private int count;
    private Request request;

    public Result(int count, Request request) {
        this.count = count;
        this.request = request;
    }

//    public Xmler.Tag asTag() {
//        return Xmler.tag("result", Arrays.asList(
//                Xmler.tag("count", ((Integer) count).toString()),
//                Xmler.tag("order", request.getOrder()),
//                Xmler.tag("request", request.getReq()),
//                Xmler.tag("type", request.getType()),
//                Xmler.tag("min", ((Integer) request.getMin()).toString()),
//                Xmler.tag("max", ((Integer) request.getMax()).toString())
//        ));
//    }
}
