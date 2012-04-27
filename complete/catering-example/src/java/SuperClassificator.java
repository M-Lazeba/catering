import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 28.04.12
 * Time: 1:21
 * To change this template use File | Settings | File Templates.
 */


public class SuperClassificator {

    ArrayList<ClassifiedPosition> classified;
    BayesClassificator classificatorDesc;
    BayesClassificator classificatorName;
    PrintWriter out;

    public SuperClassificator() throws FileNotFoundException,
            ClassNotFoundException, IOException {
        classified = ClassifiedPositionReaderAdvanced.read(1000,
                "C:/catering/complete/classified.srzl");
        classificatorDesc = new BayesClassificator(classified);
        ArrayList<ClassifiedPosition> classifiedRev = new ArrayList<ClassifiedPosition>();
        for (ClassifiedPosition pos : classified)
            classifiedRev.add(new ClassifiedPosition(new Position(
                    pos.getName(), pos.getName()), pos.getClassId()));
        classificatorName = new BayesClassificator(classifiedRev);
        out = new PrintWriter(new File("mistake.txt"));
        int miss = 0;
        for (ClassifiedPosition doc : classified) {
            int res = classify(doc.getName(), doc.getDescription());
            if (res != doc.getClassId().ordinal())
                miss++;
        }
        out.println(miss);
        out.close();
    }

    public int classify(String name, String desc) {

        Position posDesc = new Position(name, desc);
        Position posName = new Position(name, name);

        SimpleDocument docDesc = SimpleConverter.convert(posDesc);
        SimpleDocument docName = SimpleConverter.convert(posName);

        Confidence conf1 = classificatorDesc.classify(docDesc);
        Confidence conf2 = classificatorName.classify(docName);

        if (conf1.getProb() > conf2.getProb()) {
            return conf1.getClassID().ordinal();
        } else {
            return conf2.getClassID().ordinal();
        }
    }
}