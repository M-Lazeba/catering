package net.sf.xfresh.catering.model;

import net.sf.xfresh.core.xml.Tagable;
import net.sf.xfresh.core.xml.Xmler;

import java.io.StringWriter;
import java.util.Arrays;

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

    public Place(int id, String name, int type, String coord) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.coord = coord;
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
        return Xmler.tag("place", Arrays.asList(
                Xmler.tag("id", ((Integer) id).toString()),
                Xmler.tag("name", name),
                Xmler.tag("coord", coord),
                Xmler.tag("type", stringType)
                ));
    }

    public String getName() {
        return name;
    }

    public String getCoord() {
        return coord;
    }
}
