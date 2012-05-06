package net.sf.xfresh.catering.util.index;

import classification.SuperClassificator;
import net.sf.xfresh.catering.db.SimpleDBUtils;

import net.sf.xfresh.catering.util.DBFiller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

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
        ApplicationContext ctx = new FileSystemXmlApplicationContext("catering-example/src/script/beans.xml");
        SimpleDBUtils utils = (SimpleDBUtils) ctx.getBean("SimpleDBUtils");
        SuperClassificator superClassificator = new SuperClassificator();
        String path = "C:/Perl/examples/Moscow";
        DBFiller filler = new DBFiller(path, utils, superClassificator);
        System.out.println("Before the work");
        //filler.insert();
        IndexBuilder builder = (IndexBuilder) ctx.getBean("IndexBuilder");
        builder.indexNotIndexed();

        //System.out.println(utils.getLastInsertedId("positions"));

        System.out.println("Our work is done");
    }
}
