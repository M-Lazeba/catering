package net.sf.xfresh.catering.util;

import java.util.ArrayList;

public class Position {

	private int id;
	private String title;
	private String description;
	private boolean hasPic;
	private int price;
	private float ratio;
	private ArrayList<PositionTag> tags;
	private Place place;
	private String url;

	public Position(int id, String title, String description, boolean hasPic,
			int price, float ratio, ArrayList<PositionTag> tags, Place place,
			String url) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.hasPic = hasPic;
		this.price = price;
		this.ratio = ratio;
		this.tags = new ArrayList<PositionTag>(tags);
		this.place = place;
		this.url = url;
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
		for (PositionTag tag : tags)
			ans += "	" + tag + "\n";
		ans += "place: " + place;
		return ans;
	}

	public Place getPlace() {
		return place;
	}

	public ArrayList<PositionTag> getTags() {
		return tags;
	}

	public int getPrice() {
		return price;
	}

	public String geTitle() {
		return title;
	}

	public String getDesc() {
		return description;
	}

	public String getUrl() {
		return url;
	}

}
