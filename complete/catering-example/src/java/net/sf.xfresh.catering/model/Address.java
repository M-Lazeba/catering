package net.sf.xfresh.catering.model;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/26/11
 * Time: 2:31 PM
 * @author Anton Ohitin
 */
public class Address {
    public int id;
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


}
