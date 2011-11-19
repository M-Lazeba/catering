package net.sf.xfresh.catering.model;

import net.sf.xfresh.core.xml.Tagable;
import net.sf.xfresh.core.xml.Xmler;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/17/11
 * Time: 4:43 PM
 *
 * @author Anton Ohitin
 */
public class Position implements Tagable {
    public int id;
    private String title;
    private String description;
    private boolean hasPic;
    private int price;
    private float ratio;
    private ArrayList<PositionTag> tags;
    private Place place;
    String url;

    public Position(int id, String title, String description, boolean hasPic, int price, float ratio, ArrayList<PositionTag> tags, Place place, String url) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.hasPic = hasPic;
        this.price = price;
        this.ratio = ratio;
        this.tags = tags;
        this.place = place;
        this.url = url;
    }

    @Deprecated
    public String toXML() {
        StringWriter writer = new StringWriter();

        writer.write("<id>");
        writer.write(((Integer) id).toString());
        writer.write("</id>");

        writer.write("<title>");
        writer.write(title);
        writer.write("</title>");

        writer.write("<haspic>");
        writer.write(((Integer) (hasPic ? 1 : 0)).toString());
        writer.write("</haspic>");

        writer.write("<description>");
        writer.write(description);
        writer.write("</description>");

        writer.write("<price>");
        writer.write(((Integer) price).toString());
        writer.write("</price>");

        writer.write("<ratio>");
        writer.write(new Float(ratio).toString());
        writer.write("</ratio>");

        writer.write("<url>");
        writer.write(url);
        writer.write("</url>");


        writer.write(place.toXML());
        for (PositionTag i : tags) {
            writer.write(i.toXML());
        }


        return writer.toString();
    }


    public Xmler.Tag asTag() {
        List<Xmler.Tag> tagsRepr = new LinkedList<Xmler.Tag>();
        for (PositionTag tag : tags) {
            tagsRepr.add(tag.asTag());
        }
        List<Xmler.Tag> all = Arrays.asList(
                Xmler.tag("id", ((Integer) id).toString()),
                Xmler.tag("title", title),
                Xmler.tag("haspic", ((Integer) (hasPic ? 1 : 0)).toString()),
                Xmler.tag("description", description),
                Xmler.tag("price", ((Integer) price).toString()),
                Xmler.tag("ratio", ((Float) ratio).toString()),
                Xmler.tag("url", url),
                place.asTag(),
                Xmler.tag("tags", tagsRepr)
        );
        //all.addAll(tagsRepr);
        return Xmler.tag("item", all);

    }

    public Place getPlace() {
        return place;
    }

    public int getPrice() {
        return price;
    }

    public String getDesc() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<PositionTag> getTags() {
        return tags;
    }
}
