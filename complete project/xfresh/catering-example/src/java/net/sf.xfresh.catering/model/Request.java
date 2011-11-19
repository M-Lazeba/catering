package net.sf.xfresh.catering.model;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/19/11
 * Time: 6:37 PM
 * @author Anton Ohitin
 */
public class Request {

    private String type;
    private String query;
    private String order;

    public Request(Map<String, List<String>> params) {
        if (params.containsKey("type")) {
            this.type = params.get("type").get(0);
        }
        if (params.containsKey("req")) {
            this.query = params.get("req").get(0);
        }
        if (params.containsKey("order")) {
            this.order = params.get("order").get(0);
        }
    }

    public String getType() {
        return type;
    }

    public String getQuery() {
        return query;
    }



}
