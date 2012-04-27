/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 28.04.12
 * Time: 1:24
 * To change this template use File | Settings | File Templates.
 */
public class Confidence {
    private Cluster classID;
    private double prob;

    public Confidence(Cluster clasID, double prob) {
        this.classID = clasID;
        this.prob = prob;
    }

    public Cluster getClassID() {
        return classID;
    }

    public double getProb() {
        return prob;
    }
}
