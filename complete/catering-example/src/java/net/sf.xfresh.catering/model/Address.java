package net.sf.xfresh.catering.model;

import net.sf.xfresh.core.xml.Tagable;
import net.sf.xfresh.core.xml.Xmler;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/26/11
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Address implements Tagable {
    private int id;
    private String coord;
    private int type;
    private String addr;

    public Address(int id, int type, String coord, String addr) {
        this.id = id;
        this.type = type;
        this.coord = coord;
        this.addr = addr;
    }

    public int getId() {
        return id;
    }

    public String getCoord() {
        return coord;
    }

    public String getAddr() {
        return addr;
    }

    public Integer getType() {
        return type;
    }

    public Xmler.Tag asTag() {
        String stringType = "";
        if (type == 1) {
            stringType = "Ресторан";
        }
        if (type == 2) {
            stringType = "Бар";
        }
        if (type == 3) {
            stringType = "Кафе";
        }
        return Xmler.tag("address", Arrays.asList(
                Xmler.tag("id", ((Integer) id).toString()),
                Xmler.tag("coord", coord),
                Xmler.tag("addr", addr),
                Xmler.tag("type", stringType)
        ));
    }
}
