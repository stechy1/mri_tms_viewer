/*
 * Decompiled with CFR 0_123.
 */
package controller;

import enums.Controllers;
import enums.DicomTags;
import ij.plugin.DICOM;
import ij.util.DicomTools;
import java.awt.Component;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import view.MainWindow;

public class UtilityClass {
    public static void showInfoNotification(String text, String title) {
        UtilityClass.showNotification(text, title, 1);
    }

    public static void showAlertNotification(String text, String title) {
        UtilityClass.showNotification(text, title, 2);
    }

    public static void showInfoNotification(String text) {
        UtilityClass.showInfoNotification(text, "");
    }

    public static void showAlertNotification(String text) {
        UtilityClass.showAlertNotification(text, "");
    }

    private static void showNotification(String text, String title, int type) {
        if (type == 1) {
            System.out.println(text);
        } else {
            System.err.println(text);
        }
        JOptionPane.showMessageDialog((MainWindow)MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView(), text, title, type);
    }

    public static String getTagValue(DICOM dcm, DicomTags tag) {
        return DicomTools.getTag(dcm, tag.getIdentifier()).trim();
    }

    public static Double stringToDouble(String ret) {
        return Double.parseDouble(ret.trim());
    }

    public static int stringToInt(String ret) {
        return Integer.parseInt(ret.trim());
    }

    public static boolean showConfirmDialog(String message, String title) {
        int retVal = JOptionPane.showConfirmDialog((MainWindow)MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView(), message, title, 1);
        return retVal == 0;
    }

    public static boolean showConfirmDialog(String message) {
        return UtilityClass.showConfirmDialog(message, "Info");
    }

    public static String getFolderName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        return sdf.format(date);
    }

    public static File chooseSaveLocation() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setFileSelectionMode(1);
        int retVal = chooser.showOpenDialog((MainWindow)MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView());
        if (retVal == 0) {
            String path = String.valueOf(chooser.getSelectedFile().getPath()) + File.separator + UtilityClass.getFolderName();
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdir();
            }
            return dir;
        }
        return null;
    }
}

