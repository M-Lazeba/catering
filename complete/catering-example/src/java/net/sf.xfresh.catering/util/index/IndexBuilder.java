package net.sf.xfresh.catering.util.index;

import net.sf.xfresh.catering.db.DBUtils;
import net.sf.xfresh.catering.model.Position;
import net.sf.xfresh.catering.model.PositionTag;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


/**
 * Class for building an index. Connects with a database and takes positions
 * that have never been added to index.
 *
 * @author Kononov Vladislav
 */

public class IndexBuilder {


    private String path;
    private DBUtils dbUtils;

    public void setPath(String path) {
        this.path = path;
    }

    public void setDbUtils(DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    /*
    * Index positions that are not indexed yet.
    */

    public void indexNotIndexed() throws SQLException, IOException {
        //List<PositionShir> pos = (List<PositionShir>) dbUtils.getByPositionIds(dbUtils.getUnIndexed());
        List<Position> pos = (List<Position>) dbUtils.getAllPositions();
        File file = new File(path);
        file.mkdir();
        Directory dir = FSDirectory.open(file);
        IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_34,
                new RussianAnalyzer(Version.LUCENE_34));
        IndexWriterConfig conf2 = new IndexWriterConfig(Version.LUCENE_34,
                new StandardAnalyzer(Version.LUCENE_34));
        IndexWriter writer = new IndexWriter(dir, conf);

        for (Position i : pos) {
            Document doc = new Document();

            Integer id = i.getId();
            String name = i.getTitle();
            String place = null;
            if (i.getPlace() != null)
                place = i.getPlace().getName();
            String description = i.getDescription();
            List<PositionTag> tags = i.getTags();
            if (i.getPrice() == null)
                continue;
            if (id <= 17000)
                continue;
            if (id >= 12000)
                break;
            System.out.println("InD " + id);
            if (id != null) {
                doc.add(new Field("id", "" + id, Field.Store.YES,
                        Field.Index.NOT_ANALYZED));
            }
            if (id == 12128){
                int b =12;
                System.out.println("Problems");
            }
            if (name != null) {
                doc.add(new Field("name", name, Field.Store.YES,
                        Field.Index.ANALYZED));
            }
            if (place != null) {
                doc.add(new Field("place", place, Field.Store.YES,
                        Field.Index.ANALYZED));
            }
            if (description != null) {
                doc.add(new Field("description", description, Field.Store.YES,
                        Field.Index.ANALYZED));
            }
            if (tags != null)
                for (PositionTag pt : tags) {
                    String tagName = pt.getValue();
                    if (tagName == null){
                        System.out.println(name + " dies");
                        break;
                    }
                    doc.add(new Field("tags", tagName, Field.Store.YES, Field.Index.ANALYZED));
                }
            writer.addDocument(doc);
            dbUtils.setIndexed(id);
        }

        writer.close();
        IndexWriter writer2 = new IndexWriter(dir, conf2);

        for (Position i : pos) {
            Document doc = new Document();

            Integer id = i.getId();
            String name = i.getTitle();
            String place = null;
            if (i.getPlace() != null)
                place = i.getPlace().getName();String description = i.getDescription();
            List<PositionTag> tags = i.getTags();
            if (i.getPrice() == null)
                continue;
            if (id >= 17000)
                break;
            if (id <= 12000)
                continue;
            System.out.println("InD " + id);
            if (id != null) {
                doc.add(new Field("id", "" + id, Field.Store.YES,
                        Field.Index.NOT_ANALYZED));
            }
            if (name != null) {
                String nameTempo = Transliterator.transliteral(name);
                doc.add(new Field("name", nameTempo, Field.Store.YES,
                        Field.Index.ANALYZED));
            }
            if (place != null) {
                String placeTempo = Transliterator.transliteral(place);
                doc.add(new Field("place", placeTempo, Field.Store.YES,
                        Field.Index.ANALYZED));
            }
            if (description != null) {
                String descriptionTempo = Transliterator.transliteral(description);
                doc.add(new Field("description", descriptionTempo, Field.Store.YES,
                        Field.Index.ANALYZED));
            }
            if (tags != null)
                for (PositionTag pt : tags) {
                    String tagName = pt.getValue();
                    if (tagName == null){
                        System.out.println(name + " dies");
                        break;
                    }
                    String tagNameTempo = Transliterator.transliteral(tagName);
                    doc.add(new Field("tags", tagNameTempo, Field.Store.YES, Field.Index.ANALYZED));
                }
            writer2.addDocument(doc);
            dbUtils.setIndexed(id);
        }
        writer2.close();
    }


}