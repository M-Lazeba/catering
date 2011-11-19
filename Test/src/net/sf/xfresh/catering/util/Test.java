package net.sf.xfresh.catering.util;

/*
 * Does nothing right now, cause we don't have a database.
 */

public class Test {

    public static void main(String[] args) {
        try {
            IndexBuilder builder = new IndexBuilder("some", "some", "some", "some");
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
