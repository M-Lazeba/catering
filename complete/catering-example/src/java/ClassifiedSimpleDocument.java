/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 28.04.12
 * Time: 1:28
 * To change this template use File | Settings | File Templates.
 */
public class ClassifiedSimpleDocument extends SimpleDocument {
    /**
     *
     */
    private static final long serialVersionUID = 3370547631497554219L;
    private Cluster classId;

    public ClassifiedSimpleDocument(SimpleDocument doc, Cluster classId) {
        super(doc);
        this.classId = classId;
    }

    public Cluster getClassId() {
        return classId;
    }
}
