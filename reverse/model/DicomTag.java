/*
 * Decompiled with CFR 0_123.
 */
package model;

public class DicomTag {
    private String group;
    private String element;
    private String description;
    private String vr;
    private String value;

    public DicomTag(String group, String element, String description, String vr, String value) {
        this.group = group;
        this.element = element;
        this.description = description;
        this.vr = vr;
        this.value = value;
    }

    public String getDescription() {
        return this.description;
    }

    public String getElement() {
        return this.element;
    }

    public String getGroup() {
        return this.group;
    }

    public String getValue() {
        return this.value;
    }

    public String getVr() {
        return this.vr;
    }
}

