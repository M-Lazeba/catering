package classification;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IslandTester {
	public static void main(String args[]) throws IOException {

		int number = Integer.parseInt(args[0]);

		List<Position> positions = new ArrayList<Position>();
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

		System.out.println("Clustering " + positions.size() + " positions");
		IslandClusterer clusterer = new IslandClusterer(positions);
		ArrayList<ArrayList<String>> clusters = clusterer.cluster(positions
				.size() / 5);
		PrintWriter out = new PrintWriter("clusters.txt");
		for (ArrayList<String> cluster : clusters)
			if (cluster.size() > 1)
				out.println(cluster);
		out.close();
		System.out.println("Done.");
	}
}
