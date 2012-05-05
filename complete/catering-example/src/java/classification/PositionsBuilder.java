package classification;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PositionsBuilder {
	public static void main(String args[]) throws FileNotFoundException,
			IOException {
		int count = Integer.parseInt(args[0]);
		ArrayList<Position> positions = PositionReader.read(count);
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				new File("base.srlz")));
		oos.writeObject(positions);
		oos.close();
	}
}
