/*
 * Decompiled with CFR 0_123.
 */
package controller;

import java.awt.Color;
import java.awt.Dimension;

public class Configuration {
    public static int width = 384;
    public static final String TITLE_OF_APP = "TMS/MRI viewer";
    public static final Dimension DIMENSION_OF_APP = new Dimension(800, 450);
    public static final Color BACKGROUD_CONTROLLER = new Color(250, 250, 250);
    public static final String LOADER_GIF_PATH = "resources\\loading.gif";
    public static final int SELECTION_TRESSHOLD = 10;
    public static final int WHITE_PIXEL_TRESSHOLD = 10;
    public static final int MIN_AMPLITUDE_VALUE = 1000;
    public static final String GROUP_SEMICOLON = ";";
    public static final String GROUP_TILDE = "~";
    public static final String GROUP_COMMA = ",";
    public static final String GROUPS = "groups.out";
    public static final String UNASSIGN_GROUP = "nep\u0159i\u0159azen\u00e9 body";
    public static final Color UNASSIGN_COLOR = new Color(255, 10, 10);
    public static final String IGNORE_GROUP = "ignorovan\u00e9 body";
    public static final Color IGNORE_COLOR = new Color(255, 128, 0);
    public static String img_file_path;
    public static double pixelSpace;
    public static double sliceThickness;
    public static final String BIN_DATA = "BinData";
    public static final String PATIENT_DATA = "PatientData.xml";
    public static final String SESSION_XML = "Session.xml";
    public static final String FILE_REF_PATH = "fileReferencePath";
    public static final String ENTRY = "entry";
    public static final String STRING = "string";
    public static final String TMS_TRIGGER = "TMSTrigger";
    public static final String TRIGGER_DATA = "TriggerData";
    public static final String VALUE = "Value";
    public static final String MEP_MAX = "mepMax";
    public static final String RESPONSE = "response";
}

