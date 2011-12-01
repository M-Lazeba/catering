package net.sf.xfresh.catering.yalets;

import net.sf.xfresh.catering.db.DBUtils;
import net.sf.xfresh.catering.util.index.SearchResponser;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import net.sf.xfresh.core.Yalet;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 12/1/11
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractCateringYalet implements Yalet {

    protected DBUtils dbUtils;
    protected SearchResponser searchResponser;

    public void setDbUtils(DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public void setSearchResponser(SearchResponser searchResponser) {
        this.searchResponser = searchResponser;
    }

    public abstract void process(InternalRequest req, InternalResponse res);
}
