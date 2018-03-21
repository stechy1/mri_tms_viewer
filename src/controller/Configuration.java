package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;

public class Configuration {

	public static int width = 384;
	
	public static final String TITLE_OF_APP = "TMS/MRI viewer";
	public static final Dimension DIMENSION_OF_APP = new Dimension(800,450);
	public static final Color BACKGROUD_CONTROLLER = new Color(250, 250, 250);
	public static final String LOADER_GIF_PATH = "resources\\loading.gif";
	
	public static final int SELECTION_TRESSHOLD = 10;
	
	public static int whitePixelTresshold = 10;
	
	public static final String GROUP_SEMICOLON = ";";
	public static final String GROUP_TILDE = "~";
	public static final String GROUP_COMMA = ",";
	public static final String GROUPS = "groups.out";
	
	public static final String UNASSIGN_GROUP = "nepřiřazené body";
	public static final Color UNASSIGN_COLOR = new Color(255, 10, 10);
	
	public static final String IGNORE_GROUP = "ignorované body";
	public static final Color IGNORE_COLOR = new Color(255, 128, 0);
	
	public static String img_file_path;
	
}
