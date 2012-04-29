import net.sf.xfresh.catering.db.SimpleDBUtils;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

import net.sf.xfresh.catering.model.Place;
import net.sf.xfresh.catering.model.Position;
import org.springframework.dao.DataAccessException;

/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 23.04.12
 * Time: 23:01
 * To change this template use File | Settings | File Templates.
 */
public class DBFiller {

    private String path;
    private SimpleDBUtils utils;
    private SuperClassificator classy;

    public DBFiller(String s, SimpleDBUtils utils, SuperClassificator sc) {
        path = s;
        this.utils = utils;
        classy = sc;
    }

    private boolean fill(File file) {
        try {
            File f = file;
            Scanner scan = new Scanner(f);
            String dish = null;
            String description = null;
            String price = null;
            String restaurant = null;
            while (scan.hasNext()) {
                String tempo = scan.nextLine();
                tempo = tempo.replaceAll("'", "");
                if (tempo.startsWith("Restaurant")) {
                    restaurant = tempo.substring(tempo.indexOf(":") + 1).trim();
                    System.out.println(restaurant);
                } else if (tempo.startsWith("Dish")) {
                    dish = tempo.substring(tempo.indexOf(":") + 1).trim();
                } else if (tempo.startsWith("Description")) {
                    description = tempo.substring(tempo.indexOf(":") + 1).trim();
                } else if (tempo.startsWith("Price")) {
                    price = tempo.substring(tempo.indexOf(":") + 1).trim();
                    if (description == null) {
                        description = "";
                        dish = null;
                        description = null;
                        price = null;
                        continue;
                    }
                    if (price.equals("-"))
                        price = "0";
                    //System.out.println("Dish is " + dish + " " + description + " " + price);
                    try {
                        float ft = Float.valueOf(price);
                        int pr = (int) ft;
                        Place pl = new Place(restaurant);
                        int a = classy.classify(dish, description);
                        Position pos = new Position(dish, description, pr, pl, a + 1);
                        int b = utils.simpleInsertPosition(pos);
                        utils.simpleInsertTagPosition(b, a + 1);
                    } catch (Exception e) {
                        System.out.println("Shit happens " + e.toString());
                        break;
                    }
                    dish = null;
                    description = null;
                    price = null;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Not found " + file.toString());
            return false;
        } catch (DataAccessException dae) {
            System.out.println("Wrong format for db " + dae.toString());
            return false;
        }
        return true;
    }

    public void insert() {
        try {
            File pathFile = new File(path);
            for (File file : pathFile.listFiles()) {
                for (File insideFile : file.listFiles()) {
                    if (!fill(insideFile)) {
                        System.out.println("Problems with restaurant " + insideFile.getName());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}