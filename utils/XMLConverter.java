import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Converts xml from Max format to Anton format
 * 
 * @author Grechko Vladislav
 * 
 */
public class XMLConverter {
	/**
	 * Converts xml from Max format to Anton format
	 * 
	 * @param source
	 *            Source xml filename
	 * @param destination
	 *            Destination filename WITHOUT extension '.xls'
	 * @throws IOException
	 */
	public static void convert(String source, String destination)
			throws IOException {

		File file = new File(source);
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

		Document newDocument = builder.newDocument();

		newDocument.setXmlStandalone(true);

		Node pi = newDocument.createProcessingInstruction("xml-stylesheet",
				"type=\"text/xsl\" href=\"" + destination + ".xls\"");

		Element rootElement = newDocument.createElement("menu");
		newDocument.appendChild(rootElement);

		newDocument.insertBefore(pi, rootElement);

		TransformerFactory transFac = TransformerFactory.newInstance();
		Transformer trans = null;
		try {
			trans = transFac.newTransformer();
		} catch (TransformerConfigurationException e) {
			System.out.println("Exception while TransformerFactory creating");
		}
		DOMSource domsource = new DOMSource(newDocument);
		StreamResult stream = new StreamResult(new File(destination + ".xml"));

		document.getDocumentElement().normalize();
		NodeList list = document.getElementsByTagName("item");

		for (int i = 0; i < list.getLength(); i++) {
			Element node = (Element) list.item(i);
			String name = node.getElementsByTagName("name").item(0)
					.getChildNodes().item(0).getNodeValue();
			String price = null;
			if (node.getElementsByTagName("price").getLength() > 0)
				price = node.getElementsByTagName("price").item(0)
						.getChildNodes().item(0).getNodeValue();
			Element item = newDocument.createElement("item");
			Element title = newDocument.createElement("title");
			item.appendChild(title);
			title.setAttribute("lang", "ru");
			title.appendChild(newDocument.createTextNode(name));
			if (price != null) {
				Element priceElem = newDocument.createElement("price");
				priceElem.appendChild(newDocument.createTextNode(price));
				item.appendChild(priceElem);
			}
			rootElement.appendChild(item);
		}

		try {
			trans.transform(domsource, stream);
		} catch (TransformerException e) {
			System.out.println("Exception while transforming");
		}
	}
}