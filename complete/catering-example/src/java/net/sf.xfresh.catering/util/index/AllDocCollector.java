package net.sf.xfresh.catering.util.index;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Scorer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple Collector is made to gather all the information about matches. Not only
 * "ANY_NUMBER" of documents but everyone;
 *
 * @author Kononov Vladislav
 */


public class AllDocCollector extends Collector {
    List<ScoreDoc> list = new ArrayList<ScoreDoc>();
    private Scorer scorer;
    private int docBase;

    @Override
    public void setScorer(Scorer scorer) {
        this.scorer = scorer;
    }

    @Override
    public void collect(int doc) throws IOException {
        list.add(new ScoreDoc(docBase + doc, scorer.score()));
    }

    @Override
    public void setNextReader(IndexReader reader, int docBase) {
        this.docBase = docBase;
    }

    public void reset() {
        list.clear();
    }

    public List<ScoreDoc> getHits() {
        return list;
    }

    @Override
    public boolean acceptsDocsOutOfOrder() {
        return true;
    }


}