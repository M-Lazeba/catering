package classification;
public class ClassifiedPosition extends Position {

	/**
	 * 
	 */
	private static final long serialVersionUID = -594833453610578641L;
	private Cluster classId;

	public ClassifiedPosition(String name) {
		super(name);
	}

	public ClassifiedPosition(classification.Position position, classification.Cluster cluster) {
		super(position.getName(), position.getDescription());
		this.classId = cluster;
	}

	public Cluster getClassId() {
		return classId;
	}
}
