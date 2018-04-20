/*
 * Decompiled with CFR 0_123.
 */
package model;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import model.ImagePanelModel;
import model.Response;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TriggerMarkers {
    private ArrayList<Response> responses = new ArrayList();

    public TriggerMarkers(String path) {
        if (!path.endsWith(File.separator)) {
            path = String.valueOf(path) + File.separator;
        }
        String sessionXml = String.valueOf(path) + this.getSessionPath(path);
        String triggerDataXml = String.valueOf(sessionXml.substring(0, sessionXml.indexOf("Session.xml"))) + this.getTriggerPath(sessionXml);
        String triggerMarkerXml = String.valueOf(triggerDataXml.substring(0, triggerDataXml.indexOf("TriggerData"))) + this.getTriggerMarkerXmlPath(triggerDataXml);
        this.loadResponses(triggerMarkerXml);
    }

    private void loadResponses(String triggerMarkerXml) {
        Document doc = this.initDocument(triggerMarkerXml);
        NodeList list = doc.getElementsByTagName("Value");
        int i = 0;
        while (i < list.getLength()) {
            String strResponse;
            Element el;
            Node node = list.item(i);
            if (node.getNodeType() == 1 && (el = (Element)node).getAttribute("key").equals("mepMax") && !(strResponse = el.getAttribute("response")).equals("NaN")) {
                double dResponse = 0.0;
                try {
                    dResponse = Double.parseDouble(strResponse);
                    if (dResponse > 1000.0) {
                        this.responses.add(new Response(dResponse));
                    }
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
            }
            ++i;
        }
    }

    public TriggerMarkers() {
        this(ImagePanelModel.tmsPath);
    }

    private String getTriggerMarkerXmlPath(String path) {
        Document doc = this.initDocument(path);
        NodeList list = doc.getElementsByTagName("fileReferencePath");
        int i = 0;
        while (i < list.getLength()) {
            Element el;
            Node node = list.item(i);
            if (node.getNodeType() == 1 && (el = (Element)node).getTextContent().contains("_Coil0_")) {
                return el.getTextContent();
            }
            ++i;
        }
        return null;
    }

    private String getTriggerPath(String path) {
        Document doc = this.initDocument(path);
        NodeList list = doc.getElementsByTagName("fileReferencePath");
        int i = 0;
        while (i < list.getLength()) {
            Element el;
            Node node = list.item(i);
            if (node.getNodeType() == 1 && (el = (Element)node).getTextContent().contains("TMSTrigger")) {
                return el.getTextContent();
            }
            ++i;
        }
        return null;
    }

    private String getSessionPath(String path) {
        Document doc = this.initDocument(String.valueOf(path) + "PatientData.xml");
        Node node = doc.getElementsByTagName("fileReferencePath").item(0);
        if (node.getNodeType() == 1) {
            Element el = (Element)node;
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
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public double getMaxValue() {
        double max = Double.MIN_VALUE;
        if (this.responses != null && this.responses.size() != 0) {
            for (Response response : this.responses) {
                if (response.getMepMax() <= max) continue;
                max = response.getMepMax();
            }
        }
        return max;
    }

    public double getMinValue() {
        double min = Double.MAX_VALUE;
        if (this.responses != null && this.responses.size() != 0) {
            for (Response response : this.responses) {
                if (response.getMepMax() >= min) continue;
                min = response.getMepMax();
            }
        }
        return min;
    }
}

