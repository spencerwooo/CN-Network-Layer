package SoftRouter;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Statistics {
  private int nodeId;
  private String packetType;
  private RouterPacket routerPacket;

  public Statistics(int nodeId, String packetType, RouterPacket routerPacket) {
    this.nodeId = nodeId;
    this.packetType = packetType;
    this.routerPacket = routerPacket;
  }

  public void saveToXML(String filePath)
      throws ParserConfigurationException, SAXException, IOException, TransformerException {
    File xmlFile = new File(filePath);

    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(xmlFile);

    doc.getDocumentElement().normalize();

    NodeList nlist = doc.getElementsByTagName("node");

    for (int i = 0; i < nlist.getLength(); i++) {
      Node nnode = nlist.item(i);
      String id = ((Element) nnode).getAttribute("id");

      if (id.equals(String.valueOf(nodeId))) {
        Node sendNode = ((Element) nnode).getElementsByTagName(packetType).item(0);
        Element appendPacket = doc.createElement("packet");
        appendPacket.setTextContent(routerPacket.toString());
        sendNode.appendChild(appendPacket);
      }
    }

    DOMSource source = new DOMSource(doc);
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    StreamResult result = new StreamResult(filePath);
    transformer.transform(source, result);
  }
}