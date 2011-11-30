package net.sf.xfresh.catering.model;

import net.sf.xfresh.core.xml.Tagable;
import net.sf.xfresh.core.xml.Xmler;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/17/11
 * Time: 4:52 PM
 *
 * @author Anton Ohitin
 */
public class Place implements Tagable {
    public int id;
    private String name;
    private int type;
    private String coord;
    private String addr;
    List<Address> addrs;


    public Place(int id, String name, int type, List<Address> addrs) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.addrs = addrs;
        if(addrs != null) {
            if(addrs.size() > 0) {
                setAddrAndCoord(0);
            }
        }
    }

    public void setAddrs(List<Address> addrs) {
        this.addrs = addrs;
    }

    public void setAddrAndCoord(int i) {
        if (addrs.size() > i) {
            addr = addrs.get(i).getAddr();
            coord = addrs.get(i).getCoord();
            type = addrs.get(i).getType();
        }
    }

    @Deprecated
    public String toXML() {
        StringWriter writer = new StringWriter();
        writer.write("<place>");

        writer.write("<id>");
        writer.write(((Integer) id).toString());
        writer.write("</id>");

        writer.write("<name>");
        writer.write(name);
        writer.write("</name>");

        writer.write("<type>");
        if (type == 1) {
            writer.write("Ресторан");
        }
        if (type == 2) {
            writer.write("Бар");
        }
        if (type == 3) {
            writer.write("Кафе");
        }
        writer.write("</type>");

        writer.write("<coord>");
        writer.write(coord);
        writer.write("</coord>");

        writer.write("</place>");

        return writer.toString();

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

        List<Xmler.Tag> addrsRepr = new LinkedList<Xmler.Tag>();
        for (Address addr : addrs) {
            addrsRepr.add(addr.asTag());
        }


        return Xmler.tag("place", Arrays.asList(
                Xmler.tag("id", ((Integer) id).toString()),
                Xmler.tag("name", name),
                Xmler.tag("coord", coord),
                Xmler.tag("addr", addr),
                Xmler.tag("type", stringType),
                Xmler.tag("addresses", addrsRepr)
        ));

    }

    public String getName() {
        return name;
    }

    public String getCoord() {
        return coord;
    }

    public List<Address> getAddrs() {
        return addrs;
    }
}
