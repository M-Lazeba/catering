import net.sf.xfresh.catering.db.SimpleDBUtils;
//import net.sf.xfresh.catering.util.index.DBFiller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 28.04.12
 * Time: 2:01
 * To change this template use File | Settings | File Templates.
 */
public class Fuck {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("catering-example/src/script/beans.xml");
        //IndexBuilder builder = (IndexBuilder) ctx.getBean("IndexBuilder");
        //builder.indexNotIndexed();
        SimpleDBUtils utils = (SimpleDBUtils) ctx.getBean("SimpleDBUtils");
        System.out.println("Before the filling");
        SuperClassificator c = new SuperClassificator();
        DBFiller filler = new DBFiller("C:/Perl/examples/Moscow", utils, c);
        filler.insert();
        System.out.println("Work is done");
    }
}