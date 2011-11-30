package net.sf.xfresh.catering.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.xml.Xmler;


/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/22/11
 * Time: 1:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class RatingUpdateYalet implements Yalet {
    public void process(InternalRequest req, InternalResponse res) {
        System.out.println(req.getAllParameters());
        //String jsonResponse = "{ \"msg\": \"Ok\", \"status\": \"OK\" }";
        res.add(Xmler.tag("msg", "Ok"));
        res.add(Xmler.tag("status", "Ok"));
    }
}
