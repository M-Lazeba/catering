package net.sf.xfresh.catering.util;

public class Place {
	private int id;
	private String name;
	private int type;
	private String coord;

	public Place(int id, String name, int type, String coord) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.coord = coord;
	}

	public String toString() {
		return "id: " + id + " name: " + name + " type " + type + " coord: "
				+ coord;
	}

	public String getName() {
		return name;
	}

	public String getCoord() {
		return coord;
	}
}
