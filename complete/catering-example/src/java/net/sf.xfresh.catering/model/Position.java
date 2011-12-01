package net.sf.xfresh.catering.model;

import java.util.ArrayList;
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
public class Position {
    public int id;
    private String title;
    private String description;
    private boolean hasPic;
    private Integer price;
    private Float ratio;
    private String url;
    private String imgUrl;
    private List<PositionTag> tags;
    private Place place;


    public Position(int id, String title, String description, boolean hasPic, int price, float ratio, String url, ArrayList<PositionTag> tags, Place place) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.hasPic = hasPic;
        this.price = price;
        this.ratio = ratio;
        this.url = url;
        this.tags = (List) tags;
        this.place = place;
    }


    public void setPlace(Place place) {
        this.place = place;
    }

    public Place getPlace() {
        return place;
    }

    public Integer getPrice() {
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

    public int getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHasPic(boolean hasPic) {
        this.hasPic = hasPic;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public boolean isHasPic() {
        return hasPic;
    }

    public Float getRatio() {
        return ratio;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<PositionTag> getTags() {
        return tags;
    }

    public void setTags(LinkedList<PositionTag> tags) {
        this.tags = tags;
    }
}

