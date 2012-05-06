package classification;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class DoubleClassificator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2451453338771820777L;
	private BayesClassificator descNameClassificator;
	private BayesClassificator nameClassificator;
	private HashMap<String, Cluster> heuristic;
	private boolean isInit = false;

	public void remove(String term) {
		nameClassificator.removeTerm(term);
		descNameClassificator.removeTerm(term);
	}

	public BayesClassificator getNameClassificator() {
		return nameClassificator;
	}

	public BayesClassificator getNameDescClassificator() {
		return descNameClassificator;
	}

	public DoubleClassificator() throws IOException {
		descNameClassificator = new BayesClassificator(
				new ArrayList<ClassifiedPosition>());
		nameClassificator = new BayesClassificator(
				new ArrayList<ClassifiedPosition>());
	}

	private void initHeuristic() {
		heuristic = new HashMap<String, Cluster>();
		heuristic.put("�����", Cluster.PIZZA);
		heuristic.put("�����", Cluster.PASTA);
		heuristic.put("�������", Cluster.SANDWICH);
		heuristic.put("�������", Cluster.PELMENI);
		heuristic.put("�������", Cluster.PELMENI);
		heuristic.put("����", Cluster.PANCAKES);
		isInit = true;
	}

	public void add(ClassifiedPosition pos) throws IOException {
		descNameClassificator.add(new ClassifiedPosition(new Position(pos
				.getName(), pos.getName() + " " + pos.getDescription()), pos
				.getClassId()));
		nameClassificator.add(new ClassifiedPosition(new Position(
				pos.getName(), pos.getName()), pos.getClassId()));
	}

	public Confidence classify(String name, String desc, double coeff)
			throws IOException {

		if (!isInit)
			initHeuristic();

		for (String term : heuristic.keySet())
			if (name.toLowerCase().contains(term))
				return new Confidence(heuristic.get(term), 1);

		Confidence c1 = descNameClassificator.classify(SimpleConverter
				.convert(new Position(name, name + " " + desc)));
		Confidence c2 = nameClassificator.classify(SimpleConverter
				.convert(new Position(name, name)));
		if (c2.getProb() * coeff > c1.getProb())
			c1 = c2;
		return c1;
	}
}
