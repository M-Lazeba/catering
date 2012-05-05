package classification;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MegaClass {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException,
			ClassNotFoundException, IOException {
		ArrayList<ClassifiedPosition> pos1 = ClassifiedPositionReaderAdvanced
				.read(1000, "FUCK!!!!.srzl");
		ArrayList<ClassifiedPosition> pos2 = ClassifiedPositionReaderAdvanced
				.read(1000, "FUCK2.srzl");
		pos2.addAll(pos1);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				new File("classified.srzl")));
		oos.writeObject(pos2);
		oos.close();
	}

}
