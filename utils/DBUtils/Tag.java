package net.sf.xfresh.catering.util;

public class Tag {
	private int id;
	private String value;

	public Tag(int id, String value) {
		this.id = id;
		this.value = value;
	}

	public String toString() {
		return "id: " + id + " value: " + value;
	}
}
