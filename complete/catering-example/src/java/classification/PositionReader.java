package classification;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PositionReader {
	public static ArrayList<Position> read(int number) throws IOException {
		ArrayList<Position> positions = new ArrayList<Position>();
		ClasterStemmer stemmer = new ClasterStemmer();

		File dir = new File("rest");
		for (File rest : dir.listFiles()) {
			if (positions.size() > number)
				break;
			FileInputStream is = new FileInputStream(rest);
			Scanner sc = new Scanner(is, "UTF-8");
			sc.nextLine();
			ArrayList<String> dishes = new ArrayList<String>();
			while (sc.hasNext())
				dishes.add(sc.nextLine());
			for (int i = 0; i < dishes.size(); i++) {
				String name = dishes.get(i);
				name = name.substring(name.indexOf(':') + 2);
				if (i == dishes.size() - 1)
					break;
				String something = dishes.get(++i);
				String desc = "";
				if (something.subSequence(0, 2).equals("De")) {
					desc = something.substring(something.indexOf(':') + 2);
					if (i < dishes.size() - 1) {
						i++;
					}
				} else if (!(something.charAt(0) == 'P'))
					i--;
				Position pos = new Position(name, stemmer.stem(desc));
				if (pos.getDescription().length() > 0)
					positions.add(pos);
			}
		}
		return positions;
	}
}
