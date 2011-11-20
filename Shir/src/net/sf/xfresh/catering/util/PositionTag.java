package net.sf.xfresh.catering.util;

public class PositionTag {
	private int id;
	private String value;

	public PositionTag(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public PositionTag(String value) {
		this.value = value;
	}

	public String toString() {
		return "id: " + id + " value: " + value;
	}

	public String getValue() {
		return value;
	}
}
