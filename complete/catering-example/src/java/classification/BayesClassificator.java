package classification;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class BayesClassificator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3997025642463737156L;
	private ArrayList<ClassifiedSimpleDocument> classified;
	private int classNum = 0;
	int docInClass[];
	private ArrayList<HashMap<String, Integer>> termClass = new ArrayList<>();
	int countClass[];
	private HashSet<String> vocabulary = new HashSet<>();
	PrintWriter out;

	public ArrayList<HashMap<String, Integer>> getTermClass() {
		return termClass;
	}

	public BayesClassificator(List<ClassifiedPosition> classified)
			throws IOException {
		this.classified = new ArrayList<>();
		for (ClassifiedPosition pos : classified) {
			this.classified.add(new ClassifiedSimpleDocument(SimpleConverter
					.convert(pos), pos.getClassId()));
		}
		classNum = Cluster.values().length;
		docInClass = new int[classNum];
		countClass = new int[classNum];
		for (int i = 0; i < classNum; i++) {
			termClass.add(new HashMap<String, Integer>());
		}
		for (ClassifiedSimpleDocument doc : this.classified)
			add(doc);

	}

	public void add(ClassifiedPosition pos) throws IOException {
		ClassifiedSimpleDocument doc = new ClassifiedSimpleDocument(
				SimpleConverter.convert(pos), pos.getClassId());
		add(doc);
	}

	public void add(ClassifiedSimpleDocument doc) {
		classified.add(doc);
		docInClass[doc.getClassId().ordinal()]++;
		for (String term : doc) {
			vocabulary.add(term);
			termClass.get(doc.getClassId().ordinal())
					.put(term,
							termClass.get(doc.getClassId().ordinal())
									.containsKey(term) ? termClass.get(
									doc.getClassId().ordinal()).get(term) + 1
									: 1);
			countClass[doc.getClassId().ordinal()]++;
		}
	}

	public void removeTerm(String term) {
		vocabulary.remove(term);
		for (int c = 0; c < classNum; c++)
			if (termClass.get(c).containsKey(term)) {
				countClass[c]--;
				termClass.get(c).remove(term);
			}
	}

	public Confidence classify(SimpleDocument doc) {
		double maxProbability = 0;
		int bestClass = 0;
		double sumProbability = 0;
		for (int c = 0; c < classNum; c++) {
			double probability = (double) docInClass[c] / classified.size();
			for (String term : doc) {
				probability *= (termClass.get(c).containsKey(term) ? (double) termClass
						.get(c).get(term) + 1.0 : 1.0)
						/ ((double) countClass[c] + (double) vocabulary.size());
			}
			sumProbability += probability;
			if (probability > maxProbability) {
				maxProbability = probability;
				bestClass = c;
			}
		}
		return new Confidence(Cluster.values()[bestClass], maxProbability
				/ sumProbability);

	}
}
