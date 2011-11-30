package net.sf.xfresh.catering.util.index;

import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple searcher, works with tags and description but we have no tags right now,
 * because our database is shit, so I don't really know what we can get from
 * the index.
 * <p/>
 * Searches unlimited amount of information, not only the first "ANY_NUMBER" positions.
 *
 * @author Kononov Vladislav
 */
public class SearchResponserImpl implements SearchResponser {

    Directory indexDir;

    public SearchResponserImpl(String path) throws IOException {
        Directory dir = FSDirectory.open(new File(path));
        indexDir = dir;
    }

    public ArrayList<Integer> search(String request) {
        ArrayList<Integer> ids = new ArrayList<Integer>(16);
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        AllDocCollector collector = new AllDocCollector();
        try {
            IndexSearcher searcher = new IndexSearcher(indexDir);
            QueryParser parser = new QueryParser(Version.LUCENE_34, "name",
                    new RussianAnalyzer(Version.LUCENE_34));

            Query query = parser.parse(request);
            searcher.search(query, collector);
            List<ScoreDoc> temp = collector.getHits();
            for (ScoreDoc doc : temp) {
                int a = (Integer.valueOf(searcher.doc(doc.doc).get("id")));
                if (!map.containsKey(a)) {
                    map.put(a, Boolean.TRUE);
                    ids.add(a);
                }
            }
            parser = new QueryParser(Version.LUCENE_34, "tags",
                    new RussianAnalyzer(Version.LUCENE_34));
            query = parser.parse(request);
            collector.reset();
            searcher.search(query, collector);
            temp = collector.getHits();
            for (ScoreDoc doc : temp) {
                int a = (Integer.valueOf(searcher.doc(doc.doc).get("id")));
                if (!map.containsKey(a)) {
                    map.put(a, Boolean.TRUE);
                    ids.add(a);
                }
            }
            parser = new QueryParser(Version.LUCENE_34, "description",
                    new RussianAnalyzer(Version.LUCENE_34));
            query = parser.parse(request);
            collector.reset();
            searcher.search(query, collector);
            temp = collector.getHits();
            for (ScoreDoc doc : temp) {
                int a = (Integer.valueOf(searcher.doc(doc.doc).get("id")));
                if (!map.containsKey(a)) {
                    map.put(a, Boolean.TRUE);
                    ids.add(a);
                }
            }
        } catch (CorruptIndexException e) {
            System.out.println("Index is dead, needs to be reindexed");
        } catch (IOException ie) {
            System.out.println(ie.toString());
        } catch (ParseException pe) {
            pe.printStackTrace();
        } finally {
            return ids;
        }
    }

    public ArrayList<Integer> search(String[] request) {
        ArrayList<Integer> ids = new ArrayList<Integer>(16);
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();

        for (String req : request) {
            ArrayList<Integer> temp;
            temp = search(req);
            for (int i = 0; i < temp.size(); ++i) {
                int a = temp.get(i);
                if (!map.containsKey(a)) {
                    map.put(a, true);
                    ids.add(a);
                }
            }
        }
        return ids;

    }

    public ArrayList<Integer> search(String request, int type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Integer> searchByPlaces(String place) {
        ArrayList<Integer> ids = new ArrayList<Integer>(16);
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        try {
            IndexSearcher searcher = new IndexSearcher(indexDir);
            QueryParser parser = new QueryParser(Version.LUCENE_34, "place",
                    new RussianAnalyzer(Version.LUCENE_34));

            Query query = parser.parse(place);
            AllDocCollector collector = new AllDocCollector();
            searcher.search(query, collector);
            List<ScoreDoc> temp = collector.getHits();
            for (ScoreDoc doc : temp) {
                int a = (Integer.valueOf(searcher.doc(doc.doc).get("id")));
                if (!map.containsKey(a)) {
                    map.put(a, Boolean.TRUE);
                    ids.add(a);
                }
            }
        } catch (CorruptIndexException e) {
            System.out.println("Index is dead, need to be reindexed");
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        } catch (ParseException pe) {
            pe.printStackTrace();
        } finally {
            return ids;
        }
    }

    public void showIds(ArrayList<Integer> ids) {
        for (int i = 0; i < ids.size(); ++i) {
            System.out.println("id " + ids.get(i));
        }
    }
}