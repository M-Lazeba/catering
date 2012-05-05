package classification;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ClassifiedPositionReaderAdvanced {
	public static ArrayList<ClassifiedPosition> read(int count, String filename)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				new File(filename)));
		Object obj = ois.readObject();
		ArrayList<ClassifiedPosition> positions;
		if (obj instanceof ArrayList<?>)
			positions = (ArrayList<ClassifiedPosition>) obj;
		else
			throw new FileNotFoundException("unnown format");
		ArrayList<ClassifiedPosition> res = new ArrayList<ClassifiedPosition>();
		for (int i = 0; i < Math.min(count, positions.size()); i++)
			res.add(positions.get(i));
		return res;
	}
}
