package net.sf.xfresh.catering.yalets;

import net.sf.xfresh.catering.db.DBUtils;
import net.sf.xfresh.catering.db.DBUtilsImpl;
import net.sf.xfresh.catering.model.*;
import net.sf.xfresh.catering.util.ResultProcessor;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.db.AbstractDbYalet;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 10/2/11
 * Time: 8:02 PM
 *
 * @author Anton Ohitin
 */
public class ShowAllYalet extends AbstractDbYalet {

    private Request request;
    private List<Position> list;

    private void get() {

        DBUtils db = null;
        //db = new MyDBUtils("localhost/catering", "root", "toor");
        db = new DBUtilsImpl(jdbcTemplate);

        assert db != null;


        ArrayList<PositionTag> tags = new ArrayList<PositionTag>();
        tags.add(new PositionTag(0, "еда"));
        tags.add(new PositionTag(0, "мясо"));
        ArrayList<Address> addrs = new ArrayList<Address>();
        addrs.add(new Address(0, 1, "1.111; 1.222", "Вяземский"));
        addrs.add(new Address(0, 1, "1.111; 1.223", "Невский"));
        Place place = new Place(0, "У Гоши", 0, addrs);
        Position position = new Position(0, "Стейк", "Очень вкусно!", false, 200, 3, "http://ya.ru/ololol", tags, place);
        tags =  new ArrayList<PositionTag>();
        tags.add(new PositionTag(0, "еда"));
        tags.add(new PositionTag(0, "бухать"));
        Position position2 = new Position(0, "Водка", "Очень вкусно!", false, 1100, 3, "http://ya.ru/ololol", tags, place);

        db.uncheckedInsertPosition(position);
        db.uncheckedInsertPosition(position2);

        list = (List<Position>) db.getAllPositions();

        request = ResultProcessor.prepareRequest(list, request);

        list = ResultProcessor.prepareResultList(list, request);

    }

    public void process(InternalRequest req, InternalResponse res) {
        System.out.print("lol");
        request = new Request(req.getAllParameters());
        get();
        for (Position i : list) {
            res.add(i);
        }
        res.add(new Result(list.size(), request));
    }
}
