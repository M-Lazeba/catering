import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 28.04.12
 * Time: 1:22
 * To change this template use File | Settings | File Templates.
 */
public class BayesClassificator {
    private ArrayList<ClassifiedSimpleDocument> classified;
    private int classNum = 0;
    int docInClass[];
    ArrayList<HashMap<String, Integer>> termClass = new ArrayList<HashMap<String, Integer>>();
    int countClass[];
    private HashSet<String> vocabulary = new HashSet<String>();
    PrintWriter out;

    public BayesClassificator(List<ClassifiedPosition> classified)
            throws FileNotFoundException {
        out = new PrintWriter(new File("ClassesConfidence.txt"));
        this.classified = new ArrayList<ClassifiedSimpleDocument>();
        for (ClassifiedPosition pos : classified) {
            this.classified.add(new ClassifiedSimpleDocument(SimpleConverter
                    .convert(pos), pos.getClassId()));
        }
        classNum = Cluster.values().length;
        docInClass = new int[classNum];
        countClass = new int[classNum];
        for (int i = 0; i < classNum; i++) {
            termClass.add(new HashMap<String, Integer>());
        }
        for (ClassifiedSimpleDocument doc : this.classified) {
            docInClass[doc.getClassId().ordinal()]++;
            for (String term : doc) {
                vocabulary.add(term);
                termClass.get(doc.getClassId().ordinal()).put(
                        term,
                        termClass.get(doc.getClassId().ordinal()).containsKey(
                                term) ? termClass.get(
                                doc.getClassId().ordinal()).get(term) + 1 : 1);
                countClass[doc.getClassId().ordinal()]++;
            }
        }
    }

    public Confidence classify(SimpleDocument doc) {
        double maxProbability = 0;
        int bestClass = 0;
        double sumProbability = 0;
        for (int c = 0; c < classNum; c++) {
            double probability = (double) docInClass[c] / classified.size();
            for (String term : doc) {
                probability *= (termClass.get(c).containsKey(term) ? (double) termClass
                        .get(c).get(term) + 1.0 : 1.0)
                        / ((double) countClass[c] + (double) vocabulary.size());
            }
            sumProbability += probability;
            if (probability > maxProbability) {
                maxProbability = probability;
                bestClass = c;
            }
        }
        return new Confidence(Cluster.values()[bestClass], maxProbability
                / sumProbability);

    }
}