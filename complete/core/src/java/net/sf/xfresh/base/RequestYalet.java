package net.sf.xfresh.base;

import net.sf.xfresh.core.Yalet;
import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;

/**
 * Date: 29.08.2007
 * Time: 8:49:53
 * <p/>
 * Shows content of request.
 *
 * @author Nikolay Malevanny nmalevanny@yandex-team.net
 */
public class RequestYalet implements Yalet {
    public void process(final InternalRequest req, final InternalResponse res) {
        res.add(new Request(req));
    }
}
