package model;

import static controller.Configuration.FILE_REF_PATH;
import static controller.Configuration.MEP_MAX;
import static controller.Configuration.MIN_AMPLITUDE_VALUE;
import static controller.Configuration.PATIENT_DATA;
import static controller.Configuration.RESPONSE;
import static controller.Configuration.SESSION_XML;
import static controller.Configuration.TMS_TRIGGER;
import static controller.Configuration.TRIGGER_DATA;
import static controller.Configuration.VALUE;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TriggerMarkers {

	
	private ArrayList<Response> responses;
	
	// cesta do slozky TMS
	public TriggerMarkers(String path) {
		this.responses = new ArrayList<Response>();
		
		if(!path.endsWith(File.separator)) {
			path += File.separator;
		}
		String sessionXml = path + getSessionPath(path);
		String triggerDataXml = sessionXml.substring(0, sessionXml.indexOf(SESSION_XML)) + getTriggerPath(sessionXml);
		String triggerMarkerXml = triggerDataXml.substring(0, triggerDataXml.indexOf(TRIGGER_DATA)) + getTriggerMarkerXmlPath(triggerDataXml);
		loadResponses(triggerMarkerXml);
	}
	
	private void loadResponses(String triggerMarkerXml) {
		Document doc = initDocument(triggerMarkerXml);
		XPath xpath = XPathFactory.newInstance().newXPath();	
		try{
			NodeList list = (NodeList)xpath.evaluate(
				"/TriggerMarkerList/TriggerMarker/ResponseValues/Value[starts-with(@key,\"amplitude\")]/@response",
				doc.getDocumentElement(),
				XPathConstants.NODESET
			);
			for(int a=0; a<list.getLength(); a++) {
				Node el = (Node)list.item(a);
				double amp = Double.valueOf(el.getTextContent());
				this.responses.add(new Response(amp));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public TriggerMarkers() {
		this(ImagePanelModel.tmsPath);
	}
	
	private String getTriggerMarkerXmlPath(String path) {
		Document doc = initDocument(path);
		NodeList list = doc.getElementsByTagName(FILE_REF_PATH);
		
		for(int i = 0 ; i < list.getLength() ; i++) {
			Node node = list.item(i);
			if (node.getNodeType() == node.ELEMENT_NODE) {
	            Element el = (Element) node;
	            if(el.getTextContent().contains("_Coil0_")) {
	            	return el.getTextContent();
	            }
	        }
		}
		return null;
	}

	private String getTriggerPath(String path) {
		Document doc = initDocument(path);
		NodeList list = doc.getElementsByTagName(FILE_REF_PATH);
		
		for(int i = 0 ; i < list.getLength() ; i++) {
			Node node = list.item(i);
			if (node.getNodeType() == node.ELEMENT_NODE) {
	            Element el = (Element) node;
	            if(el.getTextContent().contains(TMS_TRIGGER)) {
	            	return el.getTextContent();
	            }
	        }
		}
		return null;
	}

	private String getSessionPath(String path) {
		
		Document doc = initDocument(path + PATIENT_DATA);
		Node node = doc.getElementsByTagName(FILE_REF_PATH).item(0);
		
		if (node.getNodeType() == node.ELEMENT_NODE) {
            Element el = (Element) node;
            return el.getTextContent();
		}
		return null;
	}
	
	private Document initDocument(String path) {
		try {
			File inputFile = new File(path);
	        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document doc = dBuilder.parse(inputFile);
	        doc.getDocumentElement().normalize();
	        return doc;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public double getMaxValue() {
		
		double max = Double.MIN_VALUE;
		
		if(responses != null) {
			if(responses.size() != 0) {
				for (Response response : responses) {
					if(response.getAmplitude() > max)
						max = response.getAmplitude();
				}
			}
		}
		return max;
	}
	
	public double getMinValue() {
		
		double min = Double.MAX_VALUE;
		
		if(responses != null) {
			if(responses.size() != 0) {
				for (Response response : responses) {
					if(response.getAmplitude() < min)
						min = response.getAmplitude();
				}
			}
		}
		return min;
	}
}
