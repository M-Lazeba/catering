package net.sf.xfresh.catering.miner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import net.sf.xfresh.catering.db.DBUtils;
import net.sf.xfresh.catering.db.DBUtilsFactory;
import net.sf.xfresh.catering.model.Position;
import net.sf.xfresh.catering.util.ImgUtils;

/**
 * Parse data from websites and adds it to DataBase
 * 
 * @author Lazeba Maxim
 */

public class MinerMain {

	/**
	 * @param toParse
	 *            Tells what to parse. You give names of organisations or names
	 *            of parsers you want to use
	 * 
	 *            For example: SPB.xml KillFish
	 * 
	 *            if you want to use all parsers toParse should be null or empty
	 *            or the first element should be "all"
	 * @author Lazeba Maxim
	 */
	public static void main(String[] toParse) {
		// reading list of parsers from list.xml
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		ArrayList<File> configs = new ArrayList<File>();

		boolean all = false;
		HashSet<String> setToParse = new HashSet<String>();
		if (toParse == null || toParse.length == 0 || toParse[0].equals("all")
				|| toParse[0].equals("All") || toParse[0].equals("ALL")) {
			all = true;
		} else {
			for (String s : toParse) {
				setToParse.add(s);
			}
		}

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document configListDoc = builder.parse("parsers/list.xml");
			Node conf = configListDoc.getDocumentElement().getFirstChild();
			while (conf != null) {
				String name = null;
				String uri = null;
				if (conf.getNodeName().equals("config")) {
					Node prop = conf.getFirstChild();
					while (prop != null) {
						if (prop.getNodeName().equals("orgName")) {
							name = prop.getTextContent();
						}
						if (prop.getNodeName().equals("parserURI")) {
							uri = prop.getTextContent();
						}
						prop = prop.getNextSibling();
					}
					boolean add = all;
					if (name != null && !add) {
						add = setToParse.contains(name);
					}
					if (uri != null) {
						if (!add) {
							add = setToParse.contains(uri);
						}
						if (add) {
							configs.add(new File("parsers/" + uri));
						}
					}
				}
				conf = conf.getNextSibling();
			}

		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Parsing websites

		DataCollector dc = new DataCollector(configs.toArray(new File[0]));
		ArrayList<Position> positions = dc.grabAllData();

		// Adding parsed Positions to DB and Making thumbnails of images, if
		// they exist

		DBUtils utils = DBUtilsFactory.getDBUtils();

		FileOutputStream out = null;
		try {
			out = new FileOutputStream("test.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Position pos : positions) {
			int posID = utils.uncheckedInsertPosition(pos);
			System.out.println(pos.getTitle() + " was added to DB");
			try {
				out.write(pos.getTitle().getBytes("UTF-8"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (pos.isHasPic()) {
				try {
					System.out.println("Getting image " + pos.getImgUrl());
					ImgUtils.get(pos.getImgUrl(), posID);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Problem while making thumbnail \n "
							+ posID + " " + pos.getImgUrl());
					e.printStackTrace();
				}
			}
		}

		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
