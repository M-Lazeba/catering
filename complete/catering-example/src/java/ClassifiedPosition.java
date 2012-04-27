/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 28.04.12
 * Time: 1:24
 * To change this template use File | Settings | File Templates.
 */
public class ClassifiedPosition extends Position {

    private Cluster classId;

    public ClassifiedPosition(String name) {
        super(name);
    }

    public ClassifiedPosition(Position position, Cluster cluster) {
        super(position.getName(), position.getDescription());
        this.classId = cluster;
    }

    public Cluster getClassId() {
        return classId;
    }
}

