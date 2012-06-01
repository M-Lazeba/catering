package net.sf.xfresh.catering.util.index;

import classification.Confidence;
import classification.SuperClassificator;
import net.sf.xfresh.catering.model.KVTagConverter;
import net.sf.xfresh.catering.model.Position;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

    private Directory indexDir;
    private SuperClassificator classy;

    public void setPath(String path) throws IOException, ClassNotFoundException {

        Directory dir = FSDirectory.open(new File(path));
        indexDir = dir;
        classy = new SuperClassificator();
    }

    public ArrayList<Integer> search(String request) {
        ArrayList<Integer> ids = new ArrayList<Integer>(16);
        Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        try {
            IndexSearcher searcher = new IndexSearcher(indexDir);
            insideParsing(ids, map, searcher, request, "dish");
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
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

    private void insideParsing(List<Integer> ids, Map<Integer, Boolean> exist,
                               IndexSearcher searcher,
                               final String requestFirst, final String type) throws ParseException,
            IOException {
        QueryParser parser = new QueryParser(Version.LUCENE_34, type,
                new RussianAnalyzer(Version.LUCENE_34));

        QueryParser parserStandard = new QueryParser(Version.LUCENE_34, type,
                new StandardAnalyzer(Version.LUCENE_34));

        String request = Transliterator.transliteral(requestFirst);
        parser.setDefaultOperator(QueryParser.Operator.AND);
        Query query = parser.parse(requestFirst);
        System.out.println("For fun in russian " + query.toString());
        Query helpQuery = parserStandard.parse(request);
        System.out.println("For fun in english " + helpQuery.toString());
        TopDocs collector = searcher.search(query, 200);
        ScoreDoc[] temp = collector.scoreDocs;        
        TopDocs collectorHelp = searcher.search(helpQuery, 200);
        ScoreDoc[] tempHelp = collector.scoreDocs;
        Confidence confidence = classy.classify(requestFirst, "");
        String requestTag = KVTagConverter.getByKey(confidence.getClassID().ordinal() + 1);
        ArrayList<ScoreDoc> relevant = new ArrayList<ScoreDoc>();
        ArrayList<ScoreDoc> lessRelevant = new ArrayList<ScoreDoc>();
        for (int i = 0; i < temp.length; ++i)
            if (searcher.doc(temp[i].doc).get("tags").equals(requestTag)){
                relevant.add(temp[i]);    
            } else {
                lessRelevant.add(temp[i]);
            }
        for (int i = 0; i < tempHelp.length; ++i)
            if (searcher.doc(tempHelp[i].doc).get("tags").equals(requestTag)){
                relevant.add(tempHelp[i]);
            } else {
                lessRelevant.add(tempHelp[i]);
            }
        for (int i = 0 ; i < relevant.size() - 1; ++i)
            for (int k = i + 1; k < relevant.size(); ++k)
                if (relevant.get(i).score < relevant.get(k).score){
                    Collections.swap(relevant, i, k);
                }
        for (int i = 0; i < lessRelevant.size() - 1; ++i)
            for (int k = i + 1; k < lessRelevant.size(); ++k)
                if (lessRelevant.get(i).score < lessRelevant.get(k).score){
                    Collections.swap(lessRelevant, i, k);
                }
        for (int i = 0; i < relevant.size(); ++i) {
            ids.add(Integer.valueOf(searcher.doc(relevant.get(i).doc).get("id")));
            System.out.println(relevant.get(i).score);
        }
        for (int i = 0; i < lessRelevant.size(); ++i) {
            ids.add(Integer.valueOf(searcher.doc(lessRelevant.get(i).doc).get("id")));
            System.out.println(lessRelevant.get(i).score);
        }
    }
    
    public void showQuerySearch(String request){
        IndexSearcher searcher;
        try{
            searcher = new IndexSearcher(indexDir);
        } catch (CorruptIndexException cie){
            System.out.println("your index is corrupted");
            return;
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
            return;
        }
        QueryParser parser = new QueryParser(Version.LUCENE_34, "dish",
                new RussianAnalyzer(Version.LUCENE_34));
        parser.setDefaultOperator(QueryParser.Operator.AND);
        try {
            Query query = parser.parse(request);
            System.out.println("Кусок добра выглядит так " + query.toString());
            TopDocs docs = searcher.search(query, 100);
            System.out.println("Страдай " + docs.totalHits);
            for (ScoreDoc res : docs.scoreDocs){
                Explanation exp = searcher.explain(query, res.doc);
                System.out.println(exp.toString());
                System.out.println("For personal purposes " + searcher.doc(res.doc).get("tags"));
            }
        } catch (ParseException pe){
            System.out.println(pe.getMessage());
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }
    
    public ArrayList<Integer> getRandomPositions(int n){
        IndexSearcher searcher = null;
        try{
            searcher = new IndexSearcher(indexDir);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        int docsNumber = searcher.maxDoc();
        if (n > docsNumber)
            n = docsNumber;
        HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
        ArrayList<Integer> pos = new ArrayList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < n; ++i){
            int a = rand.nextInt(docsNumber);
            if (!map.containsKey(a)){
                map.put(a, Boolean.TRUE);
                try {
                    pos.add(Integer.valueOf(searcher.doc(a).get("id")));
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
        try{
            pos.add(Integer.valueOf(searcher.doc(0).get("id")));
        } catch (Exception e){
            System.out.println("Problems with zero " + e.getMessage());
        }
        return pos;
    }
}