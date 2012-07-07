package net.sf.xfresh.catering.yalets;

import net.sf.xfresh.catering.model.Place;
import net.sf.xfresh.catering.model.Position;
import net.sf.xfresh.catering.model.Request;
import net.sf.xfresh.catering.model.Result;
import net.sf.xfresh.catering.util.ResultProcessor;
import net.sf.xfresh.catering.util.TagsMaker;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 10/2/11
 * Time: 8:02 PM
 *
 * @author Anton Ohitin
 */
public class ShowAllYalet extends AbstractCateringYalet {

    private Request request;
    private List<Position> list;


    private void get() {


        if (request.getReq() != null && !request.getReq().equals("")) {
            list = (List<Position>) dbUtils.getByPositionIds(searchResponser.search(request.getReq()));
        } else {
            list = (List<Position>) dbUtils.getByPositionIds(searchResponser.search("мясо"));
        }

        request = ResultProcessor.prepareRequest(list, request);

        list = ResultProcessor.prepareResultList(list, request);

    }

    public void process(InternalRequest req, InternalResponse res) {
        request = new Request(req.getAllParameters());
        get();
        Set<Place> places = new HashSet<Place>();
        //places.add(list.get(0).getPlace());
        //places.add(list.get(7).getPlace());
        for (Position i : list) {
            places.add(i.getPlace());
            res.add(TagsMaker.makeTag(i));
        }
        System.out.println(places.size());
        res.add(TagsMaker.makeTag(places));
        res.add(new Result(list.size(), request));
    }
}
