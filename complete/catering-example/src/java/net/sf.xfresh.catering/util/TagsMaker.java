package net.sf.xfresh.catering.util;

import net.sf.xfresh.catering.model.Address;
import net.sf.xfresh.catering.model.Place;
import net.sf.xfresh.catering.model.Position;
import net.sf.xfresh.catering.model.PositionTag;
import net.sf.xfresh.core.xml.Xmler;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/26/11
 * Time: 2:31 PM
 *
 * @author Anton Ohitin
 */
public class TagsMaker {

    public static Xmler.Tag makeTag(Address addr) {

        return Xmler.tag("address", Arrays.asList(
                Xmler.tag("id", ((Integer) addr.id).toString()),
                Xmler.tag("coord", addr.getCoord()),
                Xmler.tag("addr", addr.getAddr()),
                Xmler.tag("type", addr.getType().toString())
        ));
    }

    public static Xmler.Tag makeTag(Place place) {

        List<Xmler.Tag> addrsRepr = new LinkedList<Xmler.Tag>();
        //for (Address addr : place.getAddrs()) {
        //    addrsRepr.add(makeTag(addr));
        //}


        return Xmler.tag("place", Arrays.asList(
                Xmler.tag("id", ((Integer) place.id).toString()),
                Xmler.tag("name", place.getName()),
                //Xmler.tag("coord", place.getCoord()),
                //Xmler.tag("addr", place.getAddr()),
                Xmler.tag("url", place.getUrl())
                //Xmler.tag("type", Integer.valueOf(place.getType()).toString()),
                //Xmler.tag("addresses", addrsRepr)
        ));

    }


    public static Xmler.Tag makeTag(PositionTag tag) {
        return Xmler.tag("tag", Arrays.asList(
                Xmler.tag("id", ((Integer) tag.id).toString()),
                Xmler.tag("value", tag.getValue())
        ));
    }

    public static Xmler.Tag makeTag(Position position) {
        List<Xmler.Tag> tagsRepr = new LinkedList<Xmler.Tag>();
        for (PositionTag tag : position.getTags()) {
            tagsRepr.add(makeTag(tag));
        }
        List<Xmler.Tag> all = Arrays.asList(
                Xmler.tag("id", ((Integer) position.id).toString()),
                Xmler.tag("title", position.getTitle()),
                Xmler.tag("haspic", ((Integer) (position.isHasPic() ? 1 : 0)).toString()),
                Xmler.tag("description", position.getDescription()),
                Xmler.tag("price", position.getPrice().toString()),
                Xmler.tag("ratio", position.getRatio().toString()),
                Xmler.tag("url", position.getUrl()),
                makeTag(position.getPlace()),
                Xmler.tag("tags", tagsRepr)
        );
        return Xmler.tag("item", all);

    }

    public static Xmler.Tag makeTag(Set<Place> places) {
        List<Xmler.Tag> placesRepr = new LinkedList<Xmler.Tag>();
        for (Place place: places) {
            placesRepr.add(makeTag(place));
        }
        return Xmler.tag("places", placesRepr);
    }
}

