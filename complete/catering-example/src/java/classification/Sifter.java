package classification;
import java.util.List;

public class Sifter {
	public static void sift(List<ClusterableDocument> docs, int termsCount) {
		int count[] = new int[termsCount];
		int totalCount = 0;
		for (ClusterableDocument doc : docs) {
			for (int term = 0; term < termsCount; term++)
				count[term] += doc.getTermCount(term);
			totalCount += doc.getTotalCount();
		}
		int step = 0;
		for (ClusterableDocument doc : docs) {
			System.out.println("Sifting of " + step + " document");
			step++;
			for (int term = 0; term < termsCount; term++) {
				// if (doc.getTermCount(term) * totalCount <= count[term]
				// * doc.getTotalCount())
				// doc.remove(term);
				// if (count[term] < 5)
				// remove(term);
			}
		}
	}
}
