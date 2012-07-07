package net.sf.xfresh.catering.util.index;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Влад
 * Date: 21.06.12
 * Time: 5:03
 * To change this template use File | Settings | File Templates.
 */
public class SynonymsChecker {

    private static ArrayList<HashSet<String>> synonyms = build();
    
    private static ArrayList<HashSet<String>> build() {
        ArrayList<HashSet<String>> t = new ArrayList<HashSet<String>>();
        HashSet<String> a = new HashSet<String>();
        a.add("спагетт");
        a.add("макарон");
        a.add("паст");
        HashSet<String> b = new HashSet<String>();
        b.add("шампанск");
        b.add("вин");
        HashSet<String> c = new HashSet<String>();
        c.add("холодец");
        c.add("студен");
        HashSet<String> d = new HashSet<String>();
        d.add("бутерброд");
        d.add("сендвич");
        d.add("сэндвич");
        HashSet<String> e = new HashSet<String>();
        e.add("яйц");
        e.add("яичниц");
        e.add("омлет");
        HashSet<String> f = new HashSet<String>();
        f.add("коньяк");
        f.add("бренди");
        f.add("брэнди");
        t.add(a);
        t.add(b);
        t.add(c);
        t.add(d);
        t.add(e);
        t.add(f);
        return t;
    }
    
    public static String logic(String request){
        String state = "+";
        StringTokenizer tokenizer = new StringTokenizer(request);
        StringBuilder builder = new StringBuilder();
        while (tokenizer.hasMoreTokens()){
            String temp = tokenizer.nextToken();
            if (temp.equals("AND") || temp.equals("&&")){
                state = "+";
                continue;
            } else if (temp.equals("OR") || temp.equals("||")){
                state = "";
                continue;
            } else if (temp.equals("NOT") || temp.equals("!")){
                state = "-";
                continue;
            }
            boolean change = false;
            iterate : for (Set<String> s : synonyms){
                Iterator<String> it = s.iterator();
                while (it.hasNext()){
                    String st = it.next();
                    if (temp.startsWith(st)){
                        Iterator<String> tempIt = s.iterator();
                        builder.append(state + "( " + tempIt.next());
                        while (tempIt.hasNext())
                            if (state.equals("-"))
                                builder.append(" && " + tempIt.next());
                            else 
                                builder.append(" || " + tempIt.next());
                        builder.append(" ) ");
                        change = true;
                        state = "+";
                        break iterate;
                    }
                }
            }
            if (change)
                continue;
            if (Character.isAlphabetic(temp.charAt(0))){
                builder.append(state + temp + " ");
                state = "+";
            }
            if (temp.charAt(0) == '('){
                builder.append(state + "( ");
            }
            if (temp.charAt(0) == ')'){
                builder.append(" )");
            }
        }
        return builder.toString();
    }

}
