package net.sf.xfresh.catering.util.index;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/21/11
 * Time: 9:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        IndexBuilder builder = new IndexBuilder(
                "localhost/catering",
                "/home/exprmntr/xfresh/xfresh/catering-example/index",
                "root",
                "toor");
        builder.indexNotIndexed();
    }
}
