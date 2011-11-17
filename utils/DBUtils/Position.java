package net.sf.xfresh.catering.util;

import java.util.ArrayList;

public class Position {

	private int id;
	private String title;
	private String description;
	private boolean hasPic;
	private int price;
	private float ratio;
	ArrayList<Tag> tags;
	ArrayList<PlaceAddress> places;

	public Position(int id, String title, String description, boolean hasPic,
			int price, float ratio, ArrayList<Tag> tags,
			ArrayList<PlaceAddress> places) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.hasPic = hasPic;
		this.price = price;
		this.ratio = ratio;
		this.tags = new ArrayList<Tag>(tags);
		this.places = new ArrayList<PlaceAddress>(places);
	}

	public String toString() {
		String ans = "";
		ans += "id: " + id;
		ans += " title: " + title;
		ans += " description: " + description;
		ans += " hasPic: " + hasPic;
		ans += " price: " + price;
		ans += " ratio: " + ratio + "\n";
		ans += "Tags:\n";
		for (Tag tag : tags)
			ans += "	" + tag + "\n";
		ans += "PlaceAddresses:\n";
		for (PlaceAddress place : places)
			ans += "	" + place + "\n";
		return ans;
	}
}
