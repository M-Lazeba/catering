package net.sf.xfresh.catering.miner;

import java.util.ArrayList;
import java.util.List;

import net.sf.xfresh.catering.model.Address;
import net.sf.xfresh.catering.model.Place;
import net.sf.xfresh.catering.model.Position;
import net.sf.xfresh.catering.model.PositionTag;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.variables.Variable;

import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

	/**
	 * @author Lazeba Maxim
	 */

public class DataCollector {
	
	ArrayList<Position> positions;
	ArrayList<ScraperConfiguration> scraperConfigs;

	public DataCollector(File configs[]) {
		positions = new ArrayList<Position>();
		scraperConfigs = new ArrayList<ScraperConfiguration>();
		for (File conf : configs) {
			try {
				scraperConfigs.add(new ScraperConfiguration(conf));
			} catch (FileNotFoundException e) {
				System.out.println("Bad config file: " + conf);
				e.printStackTrace();
			}
		}
	}

	ArrayList<Position> grabDataFromOnePlace(ScraperConfiguration conf) {
		ArrayList<Position> positions = new ArrayList<Position>();
		Scraper scraper = new Scraper(conf, "parsers");

		scraper.execute();

		Variable test = (Variable) scraper.getContext().getVar("data");

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
			document = builder.parse(new ByteArrayInputStream(test.toBinary()));
			document.getDocumentElement().normalize();
		} catch (SAXException e) {
			System.out.println("Invalid xml document");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Node item = document.getElementsByTagName("item").item(0);
		while (item != null) {
			Position pos = parsePosition(item, "");
			positions.add(pos);
			item = item.getNextSibling();
		}
		return positions;
	}

	ArrayList<Position> grabAllData() {
		for (ScraperConfiguration scraperConfig : scraperConfigs) {
			positions.addAll(grabDataFromOnePlace(scraperConfig));
		}
		return positions;
	}

	List<Position> getParsedData() {
		return positions;
	}

	Position parsePosition(Node item, String placeName) {
		String title = null;
		String desc = null;
		String positonTag = null;
		String url = null;
		String imgUrl = null;
		boolean hasPic = false;
		int price = 0;
		Place place = new Place(0, placeName, 0, new ArrayList<Address>());
		Node e = item.getFirstChild();

		do {
			String nodeName = e.getNodeName().trim();
			String nodeValue = e.getTextContent();
			if (nodeValue == null)
				continue;
			nodeValue = nodeValue.trim();
			if (nodeName.equalsIgnoreCase("name")) {
				title = nodeValue;
				continue;
			}
			if (nodeName.equalsIgnoreCase("desc")) {
				desc = nodeValue;
				continue;
			}
			if (nodeName.equalsIgnoreCase("price")) {
				if (!nodeValue.matches("[0..9]*")) {
					price = Integer.parseInt(nodeValue.trim());
				}
				continue;
			}
			if (nodeName.equalsIgnoreCase("imgsrc")) {
				if (nodeValue.length() > 0) {
					hasPic = true;
					imgUrl = nodeValue;
				}
				continue;
			}
			if (nodeName.equalsIgnoreCase("place")) {
				place.getAddrs().add(new Address(0, 0, "0.0", nodeValue));
				continue;
			}
			if (nodeName.equalsIgnoreCase("website")) {
				url = nodeValue;
				continue;
			}
			if (nodeName.equalsIgnoreCase("tag")) {
				positonTag = nodeValue;
			}
		} while ((e = e.getNextSibling()) != null);
		ArrayList<PositionTag> tags = new ArrayList<PositionTag>();
		tags.add(new PositionTag(0, positonTag));
		Position result = new Position(0, title, desc, hasPic, price,
				(float) 0.0, url, tags, place);
		result.setImgUrl(imgUrl);
		return result;
	}
}
