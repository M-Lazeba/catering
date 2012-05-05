package classification;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class DocumentConverter {
	private List<Position> list;
	HashMap<String, Integer> map = new HashMap<String, Integer>();
	HashMap<Integer, String> backMap = new HashMap<Integer, String>();

	public DocumentConverter(List<Position> list) {
		this.list = list;
	}

	public ArrayList<ClusterableDocument> convert() {
		ArrayList<ClusterableDocument> res = new ArrayList<ClusterableDocument>();
		int maxId = 0;
		int step = 0;
		for (Position pos : list) {
			System.out.println("Converting " + step + " position");
			step++;
			ClusterableDocument doc = new ClusterableDocument();
			StringTokenizer tokenizer = new StringTokenizer(
					pos.getDescription(), " ,.!;:0123456789/\\'*$()#@%-<>");
			while (tokenizer.hasMoreTokens()) {
				String term = tokenizer.nextToken();
				int id;
				if (map.containsKey(term))
					id = map.get(term);
				else {
					map.put(term, maxId++);
					id = maxId - 1;
					backMap.put(id, term);
				}
				doc.add(id);
			}
			res.add(doc);
		}
		return res;
	}

	public int getTermCount() {
		return map.size();
	}

	public String convertBack(int i) {
		return backMap.get(i);
	}
}
