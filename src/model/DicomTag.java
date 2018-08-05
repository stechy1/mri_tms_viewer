package model;

public class DicomTag {

	private String group;
	private String element;
	private String description;
	private String vr;
	private String value;

	public DicomTag(String group, String element, String description, String vr, String value) {
		super();
		this.group = group;
		this.element = element;
		this.description = description;
		this.vr = vr;
		this.value = value;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getElement() {
		return element;
	}
	
	public String getGroup() {
		return group;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getVr() {
		return vr;
	}
}
