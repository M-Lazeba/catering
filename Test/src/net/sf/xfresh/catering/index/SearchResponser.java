/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.sf.xfresh.catering.index;

import java.util.ArrayList;

/**
 * Interface for searcer; Can do different searches.
 * 
 * @author Kononov Vladislav
 */
public interface SearchResponser {
    
    // one string with any number of words, can search as we want.
    // returns ids of positions of the dishes in our database
    ArrayList<Integer> search(String request);
    
    // many string with any number of words, can search as we want.
    // Can use search(String) and unite results. Each result can be found
    // only once.
    // returns the same
    ArrayList<Integer> search(String[] request);
    
    // type: 0 - search like it's && between all words.
    // type: 1 - search like it's || between all words.
    // return the same
    ArrayList<Integer> search(String request, int type);
    
    // returns ids of places that are suitable for our searching procedure.
    ArrayList<Integer> searchByPlaces(String place);
}
