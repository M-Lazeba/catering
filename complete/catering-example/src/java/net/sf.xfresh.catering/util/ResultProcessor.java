package net.sf.xfresh.catering.util;

import net.sf.xfresh.catering.model.Position;
import net.sf.xfresh.catering.model.Request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/20/11
 * Time: 10:27 PM
 *
 * @author Anton Ohitin
 */
public abstract class ResultProcessor {

    private static final ComparatorByPrice COMPARATOR_BY_PRICE = new ComparatorByPrice();
    private static final ReversedComparatorByPrice REVERSED_COMPARATOR_BY_PRICE = new ReversedComparatorByPrice();


    public static void sort(List<Position> list, String order) {
        List<Position> l = new ArrayList<Position>();

        if (order.equals("price")) {
            Collections.sort(list, COMPARATOR_BY_PRICE);
        } else if (order.equals("-price")) {
            Collections.sort(list, REVERSED_COMPARATOR_BY_PRICE);
        }
    }

    public static List<Position> filterByPrice(List<Position> list, int min, int max) {
        ArrayList<Position> result = new ArrayList<Position>();
        for (Position i : list) {
            if (i.getPrice() >= min && i.getPrice() <= max) {
                result.add(i);
            }
        }
        return result;
    }

    public static Request prepareRequest(List<Position> list, Request request) {
        if (list.size() > 0) {
            if (request.getMax() < request.getMin()) {
                request.setMax(Collections.max(list, COMPARATOR_BY_PRICE).getPrice());
                request.setMin(Collections.min(list, COMPARATOR_BY_PRICE).getPrice());
            }
            if (request.getMax() == 0) {
                request.setMax(Collections.max(list, COMPARATOR_BY_PRICE).getPrice());
            }
            if (request.getMin() == 0) {
                request.setMin(Collections.min(list, COMPARATOR_BY_PRICE).getPrice());

            }
        }
        return request;
    }

    public static List<Position> prepareResultList(List<Position> list, Request request) {

        if (request.getMax() != 0) {
            list = filterByPrice(list, request.getMin(), request.getMax());
        } else if (request.getMin() != 0) {
            list = filterByPrice(list, request.getMin(), Integer.MAX_VALUE);
        }
        sort(list, request.getOrder());
        return list;
    }
}
