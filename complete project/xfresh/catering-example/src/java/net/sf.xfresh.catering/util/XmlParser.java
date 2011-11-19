package net.sf.xfresh.catering.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Converts xml file to Position objects
 * 
 * @author Vladislav Grechko
 * 
 */
public class XmlParser {
	/**
	 * 
	 * @param doc
	 *            Document to convert
	 * @param map
	 *            Maps Position fields names (title, description, hasPic, price,
	 *            tags (current implementation returns only one tag per
	 *            position), url) to xml tags in given document
	 * 
	 *            For example, if xml document has format \<item\> \<desc\>
	 *            \</desc\> \<cost\> \</cost\>, map should be filled in this
	 *            way: [["description", "desc"], ["price", "cost"]]
	 * @return ArrayList<Position> from given xml
	 * 
	 */
	private static ArrayList<Position> parse(Document doc,
			Map<String, String> map, String placeTitle) {
		ArrayList<Position> res = new ArrayList<Position>();
		doc.getDocumentElement().normalize();
		NodeList list = doc.getElementsByTagName("item");

		for (int i = 0; i < list.getLength(); i++) {
			Element elem = (Element) list.item(i);

			String title = null;
			String desc = null;
			String positoonTag = null;
			String url = null;
			boolean hasPic = false;
			int price = 0;

			for (String column : map.keySet()) {
				String xmlTag = null;
				if (map.containsKey(column))
					xmlTag = map.get(column);
				else
					continue;
				if (elem.getElementsByTagName(xmlTag).getLength() > 0
						&& elem.getElementsByTagName(xmlTag).item(0)
								.getChildNodes().getLength() > 0) {
					String value = elem.getElementsByTagName(xmlTag).item(0)
							.getChildNodes().item(0).getNodeValue();

					if (column.equalsIgnoreCase("title")) {
						title = value;
					}
					if (column.equalsIgnoreCase("description")) {
						desc = value;
					}
					if (column.equalsIgnoreCase("positionTag")) {
						positoonTag = value;
					}
					if (column.equalsIgnoreCase("url")) {
						url = value;
					}
					if (column.equalsIgnoreCase("hasPic")) {
						hasPic = value.length() > 0;
					}
					// TODO
					if (column.equalsIgnoreCase("price")) {
						price = parseInt(value);
					}

				}
			}
			ArrayList<PositionTag> tags = new ArrayList<PositionTag>();
			tags.add(new PositionTag(positoonTag));
			res.add(new Position(0, title, desc, hasPic, price, (float) 0.0,
					tags, new Place(0, placeTitle, 0, "0,0"), url));
		}
		return res;
	}

	/**
	 * 
	 * @param filename
	 *            Filename WITHOUT extension (.xml)
	 * @param map
	 *            Maps Position fields names (title, description, hasPic, price,
	 *            tags (current implementation returns only one tag per
	 *            position), url) to xml tags in given xml file
	 * 
	 *            For example, if xml document has format \<item\> \<desc\>
	 *            \</desc\> \<cost\> \</cost\>, map should be filled in this
	 *            way: [["description", "desc"], ["price", "cost"]]
	 * @return ArrayList<Position> from given xml file
	 * @throws IOException
	 */
	public static ArrayList<Position> parse(String filename,
			Map<String, String> map) throws IOException {
		File file = new File(filename + ".xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.out
					.println("Exception while DocumentBuilderFactory creating");
		}
		Document document = null;
		try {
			document = builder.parse(file);
		} catch (SAXException e) {
			System.out.println("Invalid xml document");
		}
		return parse(document, map, filename);
	}

	private static boolean isDigit(char a) {
		return a >= '0' && a <= '9';
	}

	private static int parseInt(String s) {
		int pos = 0;
		for (pos = 0; pos < s.length(); pos++)
			if (!isDigit(s.charAt(pos)))
				break;
		return Integer.parseInt(s.substring(0, pos));

	}
}
