/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 28.04.12
 * Time: 1:25
 * To change this template use File | Settings | File Templates.
 */
import java.util.HashSet;

public class SimpleDocument extends HashSet<String> {

    public SimpleDocument(SimpleDocument doc) {
        super(doc);
    }

    public SimpleDocument() {

    }

    /**
     *
     */
    private static final long serialVersionUID = -6355093787304338046L;

}
