package model;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import controller.Configuration;
import controller.UtilityClass;

import org.w3c.dom.*;

import static controller.Configuration.*;

public class TriggerMarkers {

	
	private ArrayList<Response> responses;
	
	// cesta do slozky TMS
	public TriggerMarkers(String path) {
		
		this.responses = new ArrayList<Response>();
		
		if(!path.endsWith("\\")) {
			path += "\\";
		}
		String sessionXml = path + getSessionPath(path);
		String triggerDataXml = sessionXml.substring(0, sessionXml.indexOf(SESSION_XML)) + getTriggerPath(sessionXml);
		String triggerMarkerXml = triggerDataXml.substring(0, triggerDataXml.indexOf(TRIGGER_DATA)) + getTriggerMarkerXmlPath(triggerDataXml);
		loadResponses(triggerMarkerXml);
	}
	
	private void loadResponses(String triggerMarkerXml) {
		Document doc = initDocument(triggerMarkerXml);
		
		NodeList list = doc.getElementsByTagName(VALUE);
		
		for(int i = 0 ; i < list.getLength() ; i++) {
			Node node = list.item(i);
			if (node.getNodeType() == node.ELEMENT_NODE) {
	            Element el = (Element) node;
		        if(el.getAttribute("key").equals(MEP_MAX)) {
		        	String strResponse = el.getAttribute(RESPONSE);
		        	if(!strResponse.equals("NaN")){
		        		double dResponse = 0.0;
			        	try {
			        		dResponse = Double.parseDouble(strResponse);
			        		if(dResponse > MIN_AMPLITUDE_VALUE) {
			        			this.responses.add(new Response(dResponse));
			        		}
						} catch (NumberFormatException e) {
							// TODO: handle exception
						}
		        	}
		        }
	        }
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
					if(response.getMepMax() > max)
						max = response.getMepMax();
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
					if(response.getMepMax() < min)
						min = response.getMepMax();
				}
			}
		}
		return min;
	}
}
