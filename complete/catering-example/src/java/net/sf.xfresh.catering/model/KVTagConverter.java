package net.sf.xfresh.catering.model;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 28.04.12
 * Time: 4:17
 * To change this template use File | Settings | File Templates.
 */
public class KVTagConverter {
    private final static HashMap<Integer, String> converter = create();
    
    private static HashMap<Integer, String> create(){
        HashMap<Integer, String> hm = new HashMap<Integer, String>();
        hm.put(1, "пиво");
        hm.put(2, "хлеб");
        hm.put(3, "сыр");
        hm.put(4, "алкогольный коктейль");
        hm.put(5, "безалкогольный коктейль");
        hm.put(6, "кофе");
        hm.put(7, "десерт");
        hm.put(8, "яйца");
        hm.put(9, "рыба");
        hm.put(10, "газировка");
        hm.put(11, "каша");
        hm.put(12, "мясо");
        hm.put(13, "молоко");
        hm.put(14, "блины");
        hm.put(15, "паста");
        hm.put(16, "пельмени");
        hm.put(17, "пицца");
        hm.put(18, "салат");
        hm.put(19, "бутерброд");
        hm.put(20, "соус");
        hm.put(21, "сосиски");
        hm.put(22, "табачная продукция");
        hm.put(23, "суп");
        hm.put(24, "суши");
        hm.put(25, "закуска");
        hm.put(26, "сильный алкоголь");
        hm.put(27, "чай");
        hm.put(28, "веган");
        hm.put(29, "вино");
        return hm;
    }
    
    public static String getByKey(int i){
        return converter.get(i);
    }
    
}
