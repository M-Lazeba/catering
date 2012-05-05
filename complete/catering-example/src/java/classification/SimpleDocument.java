package classification;
import java.util.HashSet;

public class SimpleDocument extends HashSet<String> {

	public SimpleDocument(SimpleDocument doc) {
		super(doc);
	}

	public SimpleDocument() {

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6355093787304338046L;

}
