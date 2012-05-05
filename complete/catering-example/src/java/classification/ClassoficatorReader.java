package classification;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;

public class ClassoficatorReader {
	public static void main(String args[]) throws FileNotFoundException,
			IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				new File("classificator.srzl")));
		DoubleClassificator classificator = (DoubleClassificator) ois
				.readObject();
		PrintWriter out = new PrintWriter(new File("tagcloud.txt"));
		out.println("Name classificator:");
		for (Cluster cluster : Cluster.values()) {
			out.println(cluster);
			for (String term : classificator.getNameClassificator()
					.getTermClass().get(cluster.ordinal()).keySet()) {
				for (int i = 0; i < classificator.getNameClassificator()
						.getTermClass().get(cluster.ordinal()).get(term); i++)
					out.print(term + " ");
			}
			out.println();
		}
		out.println();
		out.println("Description + name classificator:");
		for (Cluster cluster : Cluster.values()) {
			out.println(cluster);
			for (String term : classificator.getNameDescClassificator()
					.getTermClass().get(cluster.ordinal()).keySet()) {
				for (int i = 0; i < classificator.getNameDescClassificator()
						.getTermClass().get(cluster.ordinal()).get(term); i++)
					out.print(term + " ");
			}
			out.println();
		}
		out.close();
	}
}
