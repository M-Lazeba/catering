/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 28.04.12
 * Time: 1:24
 * To change this template use File | Settings | File Templates.
 */
import java.io.Serializable;

public class Position implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -425372572464118099L;
    private String name;
    private String description;

    public String getDescription() {
        return description;
    }

    public Position(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Position(String name) {
        this.name = name;
        description = "";
    }

    public void appendToDescription(String term) {
        description += term + " ";
    }

    public String toString() {
        return name + ": " + description;
    }

    public String getName() {
        return name;
    }

}
