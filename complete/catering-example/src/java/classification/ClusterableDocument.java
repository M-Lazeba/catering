package classification;
public class ClusterableDocument {

	private final static int MAXTERM = 7500;

	private double map[];
	private int total;
	private boolean isNormed;

	public ClusterableDocument() {
		map = new double[MAXTERM];
		total = 0;
		isNormed = false;
	}

	public void add(int id) {
		map[id]++;
		total++;
	}

	public int getTermCount(int i) {
		return (int) map[i];
	}

	public int getTotalCount() {
		return total;
	}

	private void norm() {
		if (isNormed)
			return;
		double norm = 0;
		for (int i = 0; i < map.length; i++) {
			if (map[i] >= 1)
				map[i] = 1;
			norm += map[i];
		}
		norm = Math.sqrt(norm);
		for (int i = 0; i < map.length; i++)
			map[i] /= norm;
		isNormed = true;

	}

	public double getDistance(ClusterableDocument doc) {
		norm();
		doc.norm();
		double dist = 0;
		for (int i = 0; i < map.length; i++)
			dist += Math.pow(map[i] - doc.map[i], 2);
		return Math.sqrt(dist);
	}

	public void remove(int term) {
		total -= map[term];
		map[term] = 0;

	}
}