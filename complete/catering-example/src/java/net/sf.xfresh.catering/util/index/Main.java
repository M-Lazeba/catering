package net.sf.xfresh.catering.util.index;

import classification.SuperClassificator;
import net.sf.xfresh.catering.db.SimpleDBUtils;

import net.sf.xfresh.catering.model.Position;
import net.sf.xfresh.catering.util.Consts;
import net.sf.xfresh.catering.util.DBFiller;
import net.sf.xfresh.catering.util.ImgUtils;
import org.apache.lucene.search.IndexSearcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


/**
 * Created by IntelliJ IDEA.
 * User: exprmntr
 * Date: 11/21/11
 * Time: 9:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
        ApplicationContext ctx = new FileSystemXmlApplicationContext("C:/catering/complete/catering-example/src/script/beans.xml");
        SimpleDBUtils utils = (SimpleDBUtils) ctx.getBean("SimpleDBUtils");
        SuperClassificator superClassificator = new SuperClassificator();
        String path = "C:/Perl/examples/Moscow";
        DBFiller filler = new DBFiller(path, utils, superClassificator);
        System.out.println("Before the work");
        //filler.insert();
        //IndexBuilder builder = (IndexBuilder) ctx.getBean("IndexBuilder");
        //builder.indexNotIndexed();
        //SearchResponser sri = (SearchResponserImpl) ctx.getBean("SearchResponserImpl");
        //sri.thumbsMaker();
        //sri.showQuerySearch("красное вино");
        //outPositions(utils.getByPositionIds(sri.getRandomPositions(10)));
        System.out.println("Our work is done");
    }
    
    private static void outPositions(Collection<Position> e){
        for (Position p : e){
            System.out.println("Dish: " + p.getTitle() + "\nDesc: " + p.getDescription() + "\nTag1: " +
                    p.getTags().get(0).getValue() + "\nTag2: " + p.getTags().get(1).getValue());
        }
    }
}
