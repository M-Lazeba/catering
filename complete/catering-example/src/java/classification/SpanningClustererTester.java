package classification;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SpanningClustererTester {
	public static void main(String args[]) throws NumberFormatException,
			IOException {
		ArrayList<Position> positions = PositionReader.read(Integer
				.parseInt(args[0]));
		SpanningClusterer clusterer = new SpanningClusterer(positions);
		ArrayList<Integer> clusters = clusterer.cluster(400);
		ArrayList<ArrayList<String>> res = new ArrayList<>();
		for (int i = 0; i < positions.size(); i++)
			res.add(new ArrayList<String>());
		for (int i = 0; i < clusters.size(); i++)
			res.get(clusters.get(i)).add(positions.get(i).getName());
		PrintWriter out = new PrintWriter(new File("clusters.txt"));
		for (int i = 0; i < res.size(); i++) {
			if (res.get(i).size() == 0)
				continue;
			out.println("--------------CLUSTER : " + i);
			for (String name : res.get(i))
				out.println(name);
		}
		out.close();
	}
}
