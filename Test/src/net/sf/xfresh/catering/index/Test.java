package net.sf.xfresh.catering.index;

/**
 * Searches in an index for matches. 
 */

public class Test {

    public static void main(String[] args) {
        try {
            //IndexBuilder builder = new IndexBuilder(
            //        "localhost:3306/newdb", 
            //        "C:/textFiles/food/", 
            //        "root", 
            //        "Kosmos1991"); 
            //builder.indexNotIndexed();
            SearchResponserImpl search = new SearchResponserImpl("C:/textFiles/food/");
            search.showIds(search.search("сало"));
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
