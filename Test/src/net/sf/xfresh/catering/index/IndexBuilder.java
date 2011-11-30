package net.sf.xfresh.catering.index;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import net.sf.xfresh.catering.util.MyDBUtils;
import net.sf.xfresh.catering.util.Position;
import net.sf.xfresh.catering.util.PositionTag;
import org.apache.lucene.analysis.ru.RussianAnalyzer;


/**
 * Class for building an index. Connects with a database and takes positions
 * that have never been added to an index.
 * 
 * @author Kononov Vladislav
 */

public class IndexBuilder{

    private String urlDB;
    private String indexPath;
    private MyDBUtils utils;
    private String user;
    private String pass;
    
    
    public IndexBuilder(String url, String path, String username, String password)
    throws SQLException, ClassNotFoundException{
        urlDB = url;
        indexPath = path;
        user = username;
        pass = password;
        utils = new MyDBUtils(urlDB, user, pass);
    }
    
    
    /*
     * Index positions that are not indexed yet. 
     */
    public void indexNotIndexed() throws SQLException, IOException{
        ArrayList<Position> pos = utils.getByPositionIds(utils.getUnIndexed());
        File file = new File(indexPath);
        file.mkdir();
        Directory dir = FSDirectory.open(file);
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_34, 
                new RussianAnalyzer(Version.LUCENE_34));
        IndexWriter writer = new IndexWriter(dir, conf);
        for (Position i : pos){
            Document doc = new Document();
            
            Integer id = i.getID();
            String name = i.geTitle();
            String place = i.getPlace().getName();
            String description = i.getDesc();
            ArrayList<PositionTag> tags = i.getTags();
            
            if (id != null){
                doc.add(new Field("id", "" + id, Field.Store.YES, 
                        Field.Index.NOT_ANALYZED));
            }
            if (name != null){
                doc.add(new Field("name", name, Field.Store.YES, 
                        Field.Index.ANALYZED));
            }
            if (place != null){
                doc.add(new Field("place", place, Field.Store.YES,
                        Field.Index.ANALYZED));
            }
            if (description != null){
                doc.add(new Field("description", description, Field.Store.YES,
                        Field.Index.ANALYZED));
            }
            for (PositionTag pt : tags){
                String tagName = pt.getValue();
                doc.add(new Field("tags", tagName, Field.Store.YES, 
                        Field.Index.ANALYZED));
            }
            writer.addDocument(doc);
            utils.setIndexed(id);
        }
        writer.close();
    }
    
    



}