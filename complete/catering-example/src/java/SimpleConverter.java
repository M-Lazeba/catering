/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 28.04.12
 * Time: 1:26
 * To change this template use File | Settings | File Templates.
 */
import java.util.StringTokenizer;

public class SimpleConverter {
    public static SimpleDocument convert(Position pos) {
        SimpleDocument doc = new SimpleDocument();
        StringTokenizer tokenizer = new StringTokenizer(pos.getDescription(),
                " ,.!;:0123456789/\\'*$()#@%-<>\"");
        while (tokenizer.hasMoreTokens()) {
            doc.add(tokenizer.nextToken().toLowerCase());
        }
        return doc;
    }
}
