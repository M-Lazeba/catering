package net.sf.xfresh.catering.yalets;

import net.sf.xfresh.catering.model.*;
import net.sf.xfresh.catering.util.ImgUtils;


import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

import net.sf.xfresh.core.xml.Xmler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;



/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 10/2/11
 * Time: 8:02 PM
 * @author Anton Ohitin
 */
public class ShowAllYalet implements Yalet {

    public void process(InternalRequest req, InternalResponse res) {
        Place place = new Place(12, "У Гоши", 1, "1.1");
        PositionTag tag = new PositionTag(1, "еда");
        ArrayList<PositionTag> tags = new ArrayList<PositionTag>();
        tags.add(tag);

        Result result = new Result(1, new Request(req.getAllParameters()));

        Position pos = new Position(24, "Стейк", "Очень вкусно", true, 800, 3, tags, place, "http://macdac.com");

        res.add(result);
        res.add(pos);
        System.out.println(pos.toXML());

    }
}
