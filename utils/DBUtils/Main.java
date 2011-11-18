package net.sf.xfresh.catering.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
	public static void main(String args[]) throws SQLException,
			ClassNotFoundException, MalformedURLException, IOException {
		DBUtils util = new MyDBUtils("localhost:3306/cateringdb", "root",
				"pass");
		Place place = new Place(0, "cafe for shitmans", 1, "30.5,60.4");
		PositionTag tag1 = new PositionTag("first");
		PositionTag tag2 = new PositionTag("hot");
		ArrayList<PositionTag> tagList = new ArrayList<PositionTag>();
		tagList.add(tag2);
		tagList.add(tag1);
		Position pos = new Position(0, "tasty shit", "fucking awesome!", false,
				200, (float) 1.3, tagList, place, "ya.ru");
		ArrayList<Position> posList = new ArrayList<Position>();
		posList.add(pos);
		util.insertPositions(posList);
		ArrayList<Integer> pid = new ArrayList<Integer>();
		pid.add(47);
		System.out.println(util.getByPlaceAddressesIds(pid).get(0));
	}
}
