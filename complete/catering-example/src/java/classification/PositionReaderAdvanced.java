package classification;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class PositionReaderAdvanced {
	public static ArrayList<Position> read(int count)
			throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				new File("base.srlz")));
		Object obj = ois.readObject();
		ArrayList<Position> positions;
		if (obj instanceof ArrayList<?>)
			positions = (ArrayList<Position>) obj;
		else
			throw new FileNotFoundException("unnown format");
		if (count > positions.size())
			return positions;
		ArrayList<Position> res = new ArrayList<>();
		for (int i = 0; i < count; i++)
			res.add(positions.get(i));
		return res;
	}
}
