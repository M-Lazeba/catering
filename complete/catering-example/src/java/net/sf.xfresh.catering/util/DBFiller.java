package net.sf.xfresh.catering.util;

import net.sf.xfresh.catering.db.SimpleDBUtils;
import classification.SuperClassificator;
import classification.Confidence;
import net.sf.xfresh.catering.model.PositionTag;
import java.util.Random;

import java.io.*;
import java.util.ArrayList;
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
    private Random randomGenerator;
    private int border = 2000;
    private int count = 0;

    public DBFiller(String s, SimpleDBUtils utils, SuperClassificator sc) {
        path = s;
        this.utils = utils;
        classy = sc;
        randomGenerator = new Random();
    }

    private boolean fillSafe(File file){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String dish;
            String description;
            String priceString;
            String restaurant;
            restaurant = reader.readLine();
            restaurant = restaurant.substring(restaurant.indexOf(":") + 1).trim().replace("'", "");

            Place place = new Place(restaurant);
            Integer placeNumber = -1;
            try {
                placeNumber = utils.insertPlace(place);
            } catch (DataAccessException dae){
                System.out.println("Can not work with a restaurant " + restaurant + " due to "
                + dae.getMessage());
                return false;
            }
            while ((dish = reader.readLine()) != null){
                description = reader.readLine();
                priceString = reader.readLine();
                dish = dish.substring(dish.indexOf(":") + 1).trim().replace("'", "");
                description = description.substring(description.indexOf(":") + 1).trim().replace("'", "");
                priceString = priceString.substring(priceString.indexOf(":") + 1).trim().replace("'", "");
                Integer price = null;
                try {
                    price = Integer.valueOf(priceString);
                } catch (Exception e){
                    System.out.println("Wrong format " + priceString);
                    continue;
                }
                if (dish.length() > 40)
                    continue;
                if (description.length() > 70)
                    continue;
                Confidence confidence = classy.classify(dish, description);
                if (confidence.getProb() < 0.75)
                    continue;
                ArrayList<PositionTag> tags = new ArrayList<PositionTag>();
                PositionTag pt1 = new PositionTag(0, String.valueOf(confidence.getClassID().ordinal() + 1));
                tags.add(pt1);
                Integer tagPos = null;
                try{
                    tagPos = utils.simpleInsertTag(String.valueOf(confidence.getProb()));
                    PositionTag pt2 = new PositionTag(0, String.valueOf(tagPos));
                    tags.add(pt2);
                } catch (DataAccessException dae){
                    System.out.println("Can not create tag with probability");
                    continue;
                }
                Position toInsert = null;
                toInsert = new Position(dish, description, price, place, tags,
                        (confidence.getClassID().ordinal() != 25) ? true : false);
                Integer lastIndex;
                try{
                    lastIndex = utils.simpleInsertPosition(toInsert);
                } catch (DataAccessException dae){
                    System.out.println(dae.getMessage());
                    continue;
                }
                try{
                    utils.simpleInsertTagPosition(lastIndex, confidence.getClassID().ordinal() + 1);
                } catch (DataAccessException dae){
                    System.out.println("Can not insert ordinal integer of " + dish);
                    continue;
                }
                try{
                    utils.simpleInsertTagPosition(lastIndex, tagPos);
                } catch (DataAccessException dae){
                    System.out.println("Can not insert probability of " + dish);
                    continue;
                }
                if (confidence.getClassID().ordinal() != 25) {
                    try{
                        ImgUtils.makeThumb(confidence.getClassID().ordinal() + 1, lastIndex, "jpg", randomGenerator);
                    } catch (IOException ioe){
                        System.out.println("Can not create an image for " + dish);
                        System.err.println(ioe.getMessage());
                    }
                }
                System.out.println(count + ": " + dish + " " + confidence.getClassID().ordinal() + " " + confidence.getProb());
                ++count;
                if (count >= border){
                    return true;
                }
            }
        } catch (FileNotFoundException fnfe){
            System.out.println("Can not find file with the name " + file.getName());
            return false;
        } catch (IOException ioe){
            System.out.println("An IO error occurred " + ioe.getMessage());
            return false;
        }
        return true;
    }
    
    @Deprecated
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
                        //int a = classy.classify(dish, description);
                        //Position pos = new Position(dish, description, pr, pl, a + 1);
                        //int b = utils.simpleInsertPosition(pos);
                        //utils.simpleInsertTagPosition(b, a + 1);
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
        count = 0;
        try {
            File pathFile = new File(path);
            for (File file : pathFile.listFiles()) {
                for (File insideFile : file.listFiles()) {
                    if (!fillSafe(insideFile)) {
                        System.out.println("Problems with restaurant " + insideFile.getName());
                    } else {
                        ++count;
                    }
                    if (count >= border)
                        return;
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println("The number of inserted restaurants is " + count);
    }
}