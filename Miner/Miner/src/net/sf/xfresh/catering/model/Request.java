package net.sf.xfresh.catering.model;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/19/11
 * Time: 6:37 PM
 *
 * @author Anton Ohitin
 */
public class Request {

    private String type;
    private String req;
    private String order;
    private int min;
    private int max;

    public Request(Map<String, List<String>> params) {
        min = 0;
        max = 0;
        order = "";
        req = "";
        if (params.containsKey("type")) {
            this.type = params.get("type").get(0);
        }
        if (params.containsKey("req")) {
            this.req = params.get("req").get(0);
        }
        if (params.containsKey("order")) {
            this.order = params.get("order").get(0);
        }
        if (params.containsKey("min")) {
            try {
                this.min = new Integer(params.get("min").get(0)).intValue();
            } catch (Exception e) {

            }
        }
        if (params.containsKey("max")) {
            try {
                this.max = new Integer(params.get("max").get(0)).intValue();
            } catch (Exception e) {

            }
        }
    }

    public String getType() {
        return type;
    }

    public String getReq() {
        return req;
    }

    public String getOrder() {
        return order;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
