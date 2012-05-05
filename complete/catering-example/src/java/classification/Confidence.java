package classification;
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

	public String toString() {
		return classID + ": " + prob;
	}
}
