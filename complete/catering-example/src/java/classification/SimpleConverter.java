package classification;
import java.io.IOException;
import java.util.StringTokenizer;

public class SimpleConverter {
	public static SimpleDocument convert(Position pos) throws IOException {
		SimpleDocument doc = new SimpleDocument();
		ClasterStemmer stemmer = new ClasterStemmer();
		StringTokenizer tokenizer = new StringTokenizer(pos.getDescription(),
				" ,.!;:0123456789/\\'*$()#@%-<>\"");
		while (tokenizer.hasMoreTokens()) {
			doc.add(stemmer.stem(tokenizer.nextToken().toLowerCase()));
		}
		return doc;
	}
}
