package net.sf.xfresh.catering.model;

import net.sf.xfresh.catering.model.Item;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 10/2/11
 * Time: 7:58 PM
 */
public class ItemList {
    private List<Item> content = new LinkedList<Item>();
    private int count = 0;

    public void addItem(Item item) {
        content.add(new Item(item.getUid()));
        count++;
    }



}
