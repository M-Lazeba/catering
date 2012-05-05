package classification;
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
