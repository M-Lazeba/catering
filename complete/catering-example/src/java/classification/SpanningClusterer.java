package classification;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SpanningClusterer {
	private List<ClusterableDocument> docs;
	private DocumentConverter converter;

	public SpanningClusterer(List<Position> list) {
		converter = new DocumentConverter(list);
		docs = converter.convert();
	}

	public ArrayList<Integer> cluster(int clustersNum)
			throws FileNotFoundException {
		ArrayList<DocumentLink> edges = new ArrayList<DocumentLink>();
		for (int i = 0; i < docs.size(); i++)
			for (int j = i + 1; j < docs.size(); j++) {
				edges.add(new DocumentLink(i, j, docs.get(i).getDistance(
						docs.get(j))));
			}
		Collections.sort(edges, new Comparator<DocumentLink>() {
			@Override
			public int compare(DocumentLink o1, DocumentLink o2) {
				if (o1.w < o2.w)
					return -1;
				if (o1.w == o2.w)
					return 0;
				return 1;
			}
		});
		PrintWriter out = new PrintWriter(new File("edges.txt"));
		System.out.println("EDGES");
		for (DocumentLink edge : edges) {
			out.println(edge.getW());
		}
		DSU trees = new DSU(docs.size());
		int count = 0;
		for (DocumentLink edge : edges) {
			if (edge.getW() > 0.9)
				break;
			if (trees.get(edge.getU()) != trees.get(edge.getV())) {
				trees.union(edge.getU(), edge.getV());
				count++;
			}
		}
		ArrayList<Integer> clusters = new ArrayList<Integer>();
		for (int i = 0; i < docs.size(); i++)
			clusters.add(trees.get(i));
		return clusters;
	}

	private class DocumentLink {
		private int u;
		private int v;
		private double w;

		public DocumentLink(int u, int v, double w) {
			this.u = u;
			this.v = v;
			this.w = w;
		}

		public int getU() {
			return u;
		}

		public int getV() {
			return v;
		}

		public double getW() {
			return w;
		}

	}
}
