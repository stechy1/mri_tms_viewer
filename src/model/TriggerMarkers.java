package model;

import static controller.Configuration.FILE_REF_PATH;
import static controller.Configuration.KEY;
import static controller.Configuration.MATRIX;
import static controller.Configuration.PATIENT_DATA;
import static controller.Configuration.RESPONSE;
import static controller.Configuration.SESSION_XML;
import static controller.Configuration.TMS_TRIGGER;
import static controller.Configuration.TRIGGER_DATA;
import static controller.Configuration.TRIGGER_MARKER;
import static controller.Configuration.VALUE;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Function;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TriggerMarkers {

    private final ArrayList<Response> responses = new ArrayList<>();

	public TriggerMarkers() {
		this(ImagePanelModel.tmsPath);
	}

	public ArrayList<Response> getResponses(){
		return responses;
	}

	public TriggerMarkers(String path) {
		if(!path.endsWith(File.separator)) {
			path += File.separator;
		}
		String sessionXml = path + getSessionPath(path);
		String triggerDataXml = sessionXml.substring(0, sessionXml.indexOf(SESSION_XML)) + getTriggerPath(sessionXml);
		String triggerMarkerXml = triggerDataXml.substring(0, triggerDataXml.indexOf(TRIGGER_DATA)) + getTriggerMarkerXmlPath(triggerDataXml);
		loadResponses(triggerMarkerXml);
	}

	private String forEach(String doc,String tagName,Function<Node,String> func){
		Document dc = initDocument(doc);
		NodeList list = dc.getElementsByTagName(tagName);
		return forEach(func,list);
	}

	private String forEach(Node node,String tagName,Function<Node,String> func){
		NodeList list = ((Element)node).getElementsByTagName(tagName);
		return forEach(func,list);
	}

	private String forEach(Function<Node,String> func,NodeList list){
		for(int a=0; a<list.getLength(); a++){
			Node node = list.item(a);
			String ret = func.apply(node);
			if (ret!=null){
				return ret;
			}
		}
		return null;
	}

	private void loadResponses(String triggerMarkerXml) {
		forEach(triggerMarkerXml,TRIGGER_MARKER,(n1)->{
			Response res = new Response();
			forEach(n1,MATRIX,(n2)->{
				for(int a=0,c=0; a<4; a++){
					for(int b=0; b<4; b++, c++){
						res.getMatrix().getData()[c] = Double.valueOf(n2.getAttributes().getNamedItem("data"+a+b).getTextContent());
					}
				}
				return null;
			});
			forEach(n1,VALUE,(n2)->{
				res.getData().put(n2.getAttributes().getNamedItem(KEY).getTextContent(),Double.valueOf(n2.getAttributes().getNamedItem(RESPONSE).getTextContent()));
				return null;
			});
			responses.add(res);
			return null;
		});
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
			System.err.println("Nenalezeno validni XML: "+path);
			return null;
		}
	}

}
