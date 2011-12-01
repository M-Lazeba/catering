package net.sf.xfresh.catering.model;

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
public class Place {
    public int id;
    private String name;
    private int type;
    private String coord;
    private String addr;
    private List<Address> addrs;

    public Place(int id, String name, int type, List<Address> addrs) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.addrs = addrs;
        if (addrs != null) {
            if (addrs.size() > 0) {
                setAddrAndCoord(0);
            }
        }
    }


    public void setAddrAndCoord(int i) {
        if (addrs.size() > i) {
            addr = addrs.get(i).getAddr();
            coord = addrs.get(i).getCoord();
            type = addrs.get(i).getType();
        }
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

    public void setAddrs(LinkedList<Address> addrs) {
        this.addrs = addrs;
    }

    public int getType() {
        return type;
    }

    public String getAddr() {
        return addr;
    }
}
