package net.sf.xfresh.catering.util;

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

/**
 * Class for building an index. Connects with a database and takes positions
 * that have never been added to index.
 * 
 * @author Kononov Vladislav
 */

public class IndexBuilder{

    private String urlDB;
    private String indexPath;
    private MyDBUtils utils;
    private String user;
    private String pass;
    
    
    /*
     * Gets values for url with database, username and password for it and 
     * path where to put index
     */
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
                new StandardAnalyzer(Version.LUCENE_34));
        IndexWriter writer = new IndexWriter(dir, conf);
        for (int i = 0; i < pos.size(); ++i){
            Document doc = new Document();
            
            Integer id = pos.get(i).getID();
            String name = pos.get(i).geTitle();
            String place = pos.get(i).getPlace().getName();
            ArrayList<PositionTag> tags = pos.get(i).getTags(); 
            
            doc.add(new Field("id", "" + id, Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("name", name, Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("place", place, Field.Store.YES, Field.Index.ANALYZED));
            for (PositionTag pt : tags){
                String tagName = pt.getValue();
                doc.add(new Field("tags", tagName, Field.Store.YES, Field.Index.ANALYZED));
            }
            writer.addDocument(doc);
        }
        writer.close();
    }
    
    



}