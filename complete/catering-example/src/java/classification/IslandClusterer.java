package classification;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IslandClusterer {
	private List<ClusterableDocument> docs;
	private int termCount;
	private int totalCount;
	private BernoulliScheme probs;
	private double bound;
	DSU islands;
	boolean islandFixed[];
	boolean termFixed[];
	private DocumentConverter converter;

	private boolean isTermFixed(int term) {
		return termFixed[term] || islandFixed[islands.get(term)];
	}

	private double getCorrelation(int i, int j) {
		int ni = 0;
		int nj = 0;
		int nij = 0;
		for (ClusterableDocument doc : docs) {
			if (doc.getTermCount(i) > 0) {
				ni += doc.getTotalCount();
				nij += doc.getTermCount(j);
			}
			nj += doc.getTermCount(j);
		}
		if ((double) nij <= (double) ni * nj / totalCount)
			return 1;
		return probs.getProbability(nj, nij, (double) ni / (double) totalCount);
	}

	public ArrayList<ArrayList<String>> cluster(int clusterSize) {
		ArrayList<TermLink> links = new ArrayList<TermLink>();
		System.out.println(termCount + " terms found");
		int all = termCount * (termCount - 1) / 2;
		int step = 0;
		long time = System.currentTimeMillis();
		for (int i = 0; i < termCount; i++)
			for (int j = i + 1; j < termCount; j++) {
				if (step % 1000 == 0) {
					System.out.println("Calculating correlation " + step
							+ " of " + all);
					System.out.println(((System.currentTimeMillis() - time)
							/ (step + 1) * (all - step - 1))
							/ (60000) + " minutes remains");
				}
				step++;
				double correlation = Math.max(getCorrelation(i, j),
						getCorrelation(j, i));
				if (correlation < bound)
					links.add(new TermLink(i, j, correlation));
			}
		System.out.println("" + links.size() + " links added, sorting...");
		Collections.sort(links, new Comparator<TermLink>() {
			@Override
			public int compare(TermLink o1, TermLink o2) {
				if (o1.getP() < o2.getP())
					return -1;
				if (o1.getP() > o2.getP())
					return 1;
				return 0;
			}
		});
		System.out.println("Sorted.");
		islands = new DSU(termCount);
		islandFixed = new boolean[termCount];
		termFixed = new boolean[termCount];
		for (int i = 0; i < termCount; i++) {
			int sum = 0;
			for (ClusterableDocument doc : docs)
				if (doc.getTermCount(i) > 0)
					sum++;
			if (sum >= clusterSize)
				termFixed[i] = true;
		}
		step = 0;
		for (TermLink link : links) {
			System.out.println("Processing of " + step + " link");
			step++;
			if (isTermFixed(link.getI()) && isTermFixed(link.getJ()))
				continue;
			if (isTermFixed(link.getI()) && !isTermFixed(link.getJ())) {
				islandFixed[islands.get(link.getJ())] = true;
				continue;
			}
			if (!isTermFixed(link.getI()) && isTermFixed(link.getJ())) {
				islandFixed[islands.get(link.getI())] = true;
				continue;
			}
			int isl1 = islands.get(link.getI());
			int isl2 = islands.get(link.getJ());
			if (isl1 == isl2) {
				if (pop(isl1) > clusterSize || pop(isl2) > clusterSize) {
					islandFixed[isl1] = true;
					islandFixed[isl2] = true;
				}
			} else {
				islands.union(isl1, isl2);
			}
		}
		System.out.println("Clustered. Writing results...");
		ArrayList<ArrayList<String>> clusters = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < termCount; i++) {
			clusters.add(new ArrayList<String>());
		}
		for (int i = 0; i < termCount; i++) {
			clusters.get(islands.get(i)).add(converter.convertBack(i));
		}
		return clusters;
	}

	private int pop(int island) {
		int res = 0;
		for (ClusterableDocument doc : docs)
			for (int term = 0; term < termCount; term++)
				if (islands.get(term) == island && doc.getTermCount(term) > 0)
					res++;
		return res;
	}

	public IslandClusterer(List<Position> list) {
		converter = new DocumentConverter(list);
		System.out.println("Converting...");
		docs = converter.convert();
		System.out.println("Converted.");
		termCount = converter.getTermCount();
		for (ClusterableDocument doc : docs)
			totalCount += doc.getTotalCount();
		int maxMent = 0;
		for (int term = 0; term < termCount; term++) {
			int sum = 0;
			for (ClusterableDocument doc : docs)
				sum += doc.getTermCount(term);
			if (sum > maxMent)
				maxMent = sum;
		}
		probs = new BernoulliScheme(maxMent);
		bound = 0.03 / Math.max(termCount * termCount, docs.size());
		System.out.println("Sifting...");
		Sifter.sift(docs, termCount);
		System.out.println("Sifted.");
	}

	private class TermLink {
		int i;
		int j;
		double p;

		TermLink(int i, int j, double p) {
			this.i = i;
			this.j = j;
			this.p = p;
		}

		public double getP() {
			return p;
		}

		public int getI() {
			return i;
		}

		public int getJ() {
			return j;
		}

		public String toString() {
			return i + " " + j + " " + p;
		}
	}
}
