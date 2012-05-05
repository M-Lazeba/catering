package classification;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SuperClassificator {

	private DoubleClassificator classificator;

	public SuperClassificator() throws FileNotFoundException,
			ClassNotFoundException, IOException {
		classificator = new DoubleClassificator();
		ArrayList<ClassifiedPosition> classified = ClassifiedPositionReaderAdvanced
				.read(1000, "classifiedNew.srzl");
		for (ClassifiedPosition pos : classified)
			classificator.add(pos);
	}

	public Confidence classify(String name, String desc) throws IOException {
		return classificator.classify(name, desc, 1);
	}
}
