package net.sf.xfresh.catering.yalets;

import net.sf.xfresh.catering.model.*;
import net.sf.xfresh.catering.util.ResultProcessor;
import net.sf.xfresh.catering.util.TagsMaker;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

import java.util.ArrayList;
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


        ArrayList<PositionTag> tags = new ArrayList<PositionTag>();
        tags.add(new PositionTag(0, "еда"));
        tags.add(new PositionTag(0, "мясо"));
        ArrayList<Address> addrs = new ArrayList<Address>();
        addrs.add(new Address(0, 1, "30.4,59.89", "Достоевский"));
        addrs.add(new Address(0, 1, "1.111,1.223", "Невский"));
        Place place = new Place(0, "Кафе сережи говноеда", 0, addrs);
        Position position = new Position(0, "Стейк", "Очень вкусно!", false, 200, 3, "http://ya.ru/ololol", tags, place);
        tags = new ArrayList<PositionTag>();
        tags.add(new PositionTag(0, "еда"));
        tags.add(new PositionTag(0, "бухать"));
        Position position2 = new Position(0, "Водка", "Очень вкусно!", false, 1100, 3, "http://ya.ru/ololol", tags, place);

        //dbUtils.uncheckedInsertPosition(position);
        //dbUtils.uncheckedInsertPosition(position2);

        list = (List<Position>) dbUtils.getAllPositions();

        request = ResultProcessor.prepareRequest(list, request);

        list = ResultProcessor.prepareResultList(list, request);

    }

    public void process(InternalRequest req, InternalResponse res) {
        System.out.print("lol");
        request = new Request(req.getAllParameters());
        get();
        Set<Place> places = new HashSet<Place>();
        places.add(list.get(0).getPlace());
        places.add(list.get(34).getPlace());
        for (Position i : list) {
            //places.add(i.getPlace());
            res.add(TagsMaker.makeTag(i));
        }
        res.add(TagsMaker.makeTag(places));
        res.add(new Result(list.size(), request));
    }
}
