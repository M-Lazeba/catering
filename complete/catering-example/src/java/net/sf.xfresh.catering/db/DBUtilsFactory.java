package net.sf.xfresh.catering.db;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 12/1/11
 * Time: 6:28 PM
 *
 * @author Anton Ohitin
 */
@Deprecated
public class DBUtilsFactory {
    public static DBUtils getDBUtils() {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("catering-example/src/script/beans.xml");
        DBUtils db = (DBUtils) ctx.getBean("DBUtilsImpl");
        return db;
    }
}
