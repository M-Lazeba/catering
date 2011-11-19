package net.sf.xfresh.catering.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	public static void main(String args[]) throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", "name");
		map.put("description", "desc");
		map.put("price", "price");
		ArrayList<Position> poss = XmlParser.parse("max", map);
		for (Position pos : poss)
			System.out.println(pos);
	}
}
