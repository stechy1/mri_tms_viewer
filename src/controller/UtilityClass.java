package controller;

import enums.Controllers;
import enums.DicomTags;
import ij.plugin.DICOM;
import ij.util.DicomTools;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import view.MainWindow;

public class UtilityClass {

	/** Methods for show notification in dialog windows and on CLI
	 * @param text text to show
	 * @param title title of dialog
	 */
	public static void showInfoNotification(String text, String title){
		showNotification(text, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showAlertNotification(String text, String title){
		showNotification(text, title, JOptionPane.WARNING_MESSAGE);
	}
	
	public static void showInfoNotification(String text){
		showInfoNotification(text, "");
	}
	
	public static void showAlertNotification(String text){
		showAlertNotification(text, "");
	}
	
	private static void showNotification(String text, String title, int type){
		if(type == JOptionPane.INFORMATION_MESSAGE)
			System.out.println(text);
		else
			System.err.println(text);
		
		JOptionPane.showMessageDialog((MainWindow) MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView(), text, title, type);
	}
	
	public static String getTagValue(DICOM dcm, DicomTags tag){
		return DicomTools.getTag(dcm, tag.getIdentifier()).trim();
	}
	
	public static Double stringToDouble(String ret){
		return Double.parseDouble(ret.trim());
	}
	
	public static int stringToInt(String ret){
		return Integer.parseInt(ret.trim());
	}
	
	public static boolean showConfirmDialog(String message, String title){
		int retVal =  JOptionPane.showConfirmDialog((MainWindow) MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView(), 
				message, title, JOptionPane.INFORMATION_MESSAGE);
		return retVal == JOptionPane.OK_OPTION ? true : false;
	}
	
	public static boolean showConfirmDialog(String message){
		return showConfirmDialog(message, "Info");
	}
	
	public static String getFolderName(){
		DateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        return sdf.format(date);
	}
	
	public static File chooseSaveLocation(){
		JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new File("."));
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    int retVal = chooser.showOpenDialog((MainWindow) MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView());
	    if (retVal == JFileChooser.APPROVE_OPTION) {
	    	String path = chooser.getSelectedFile().getPath() + File.separator + getFolderName();
	    	File dir = new File(path);
	    	if(!dir.exists())
	    		dir.mkdir();
	    	return dir;
	    }
	    else
	    	return null;
		
	}
}
