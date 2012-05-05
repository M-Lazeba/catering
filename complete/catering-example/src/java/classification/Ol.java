package classification;
import java.util.ArrayList;
import java.util.Collections;

public class Ol {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList<String> names = new ArrayList<>();
		for (int i = 0; i < Cluster.values().length; i++) {
			System.out.println(i + " " + Cluster.values()[i]);
			names.add(Cluster.values()[i].toString());
		}
		Collections.sort(names);
		for (String name : names)
			System.out.print(name + ", ");
	}

}
