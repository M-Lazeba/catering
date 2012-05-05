package classification;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.ru.RussianLightStemFilter;

import java.io.Reader;
import java.util.Set;

public class StemAnalyzer extends Analyzer {

	private Set stopWords;

	public StemAnalyzer() {
		this(RussianAnalyzer.getDefaultStopSet());
	}

	public StemAnalyzer(Set stopWords) {
		this.stopWords = stopWords;
	}

	@Override
	public TokenStream tokenStream(String s, Reader reader) {
		StopFilter filter = new StopFilter(Version.LUCENE_34,
				new LowerCaseTokenizer(Version.LUCENE_34, reader), stopWords,
				true);
		filter.setEnablePositionIncrements(true);
		return new RussianLightStemFilter(filter);
	}
}
