package classification;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * Created by IntelliJ IDEA. User: Влад Date: 17.04.12 Time: 23:29 To change
 * this template use File | Settings | File Templates.
 */
public class ClasterStemmer {

	private Analyzer analyzer;

	public ClasterStemmer() {
		analyzer = new StemAnalyzer();

	}

	public String stem(String s) throws IOException {
		Scanner scan = new Scanner(s);
		String result = "";
		while (scan.hasNext()) {
			result += analyze(scan.next()) + " ";
		}
		return result;
	}

	private String analyze(String s) throws IOException {
		// System.out.println("Analizing " + s);
		return displayTokens(analyzer, s);
	}

	private String displayTokens(Analyzer a, String t) throws IOException {
		return displayTokens(a.tokenStream("content", new StringReader(t)));
	}

	private String displayTokens(TokenStream stream) throws IOException {
		CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);
		String tempo = "";
		while (stream.incrementToken()) {
			tempo += term.toString();
		}
		return tempo;
	}
}
