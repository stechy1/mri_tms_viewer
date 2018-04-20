/*
 * Decompiled with CFR 0_123.
 */
package enums;

import java.util.ArrayList;
import model.DicomTag;

public enum DicomTags {
    IMAGE_DATE("0008", "0023", "Imaget Date", "DA"),
    PATIENT_NAME("0010", "0010", "Patient's Name", "PN"),
    BIRTH_DATE("0010", "0030", "Patient's Birth Date", "DA"),
    IMAGE_NUMBER("0020", "0013", "Image Number", "IS"),
    SLICE_THICKNESS("0018", "0050", "Slice Thickness", "DS"),
    ROWS("0028", "0010", "Rows", "US"),
    PIXEL_SPACING("0028", "0030", "Pixel Spacing", "DS");
    
    private String group;
    private String element;
    private String description;
    private String vr;

    private DicomTags(String group, String element, String description, String vr) {
        this.group = group;
        this.element = (String)element;
        this.description = description;
        this.vr = vr;
    }

    public String getIdentifier() {
        return String.valueOf(this.group) + "," + this.element;
    }

    public String getGroup() {
        return this.group;
    }

    public String getElement() {
        return this.element;
    }

    public String getDescription() {
        return this.description;
    }

    public String getVr() {
        return this.vr;
    }

    public String toString() {
        return this.getIdentifier();
    }

    public static ArrayList<DicomTag> getDicomTags() {
        ArrayList<DicomTag> list = new ArrayList<DicomTag>();
        DicomTags[] arrdicomTags = DicomTags.values();
        int n = arrdicomTags.length;
        int n2 = 0;
        while (n2 < n) {
            DicomTags tag = arrdicomTags[n2];
            list.add(new DicomTag(tag.group, tag.element, tag.description, tag.vr, null));
            ++n2;
        }
        return list;
    }
}

