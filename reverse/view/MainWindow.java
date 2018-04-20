/*
 * Decompiled with CFR 0_123.
 */
package view;

import controller.Configuration;
import controller.DataController;
import controller.MainWindowController;
import enums.Controllers;
import interfaces.IController;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import view.MainMenu;
import view.centerPane.ImagePanel;
import view.leftPane.LeftControlPanel;
import view.rightPane.RightControlPanel;

public class MainWindow
extends JFrame {
    private static DataController controllers = new DataController();
    private JPanel contentPane;
    private ImagePanel imagePanel;
    private LeftControlPanel leftControlPanel;
    private RightControlPanel rightControlPanel;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    new view.MainWindow();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainWindow() {
        this.initComponents();
        this.setVisible(true);
    }

    private void initComponents() {
        MainWindowController controller = new MainWindowController(this);
        MainWindow.addController(controller);
        this.setTitle("TMS/MRI viewer");
        this.setSize(Configuration.DIMENSION_OF_APP);
        this.setPreferredSize(Configuration.DIMENSION_OF_APP);
        this.setMinimumSize(Configuration.DIMENSION_OF_APP);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(0);
        this.addWindowListener(controller);
        this.contentPane = new JPanel();
        this.setContentPane(this.contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        int[] arrn = new int[3];
        arrn[1] = 1;
        gbl_contentPane.columnWidths = arrn;
        gbl_contentPane.rowHeights = new int[1];
        gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0};
        gbl_contentPane.rowWeights = new double[]{1.0};
        this.contentPane.setLayout(gbl_contentPane);
        this.leftControlPanel = new LeftControlPanel();
        this.leftControlPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc_controlPanel = new GridBagConstraints();
        gbc_controlPanel.insets = new Insets(0, 0, 0, 5);
        gbc_controlPanel.fill = 1;
        gbc_controlPanel.gridx = 0;
        gbc_controlPanel.gridy = 0;
        this.contentPane.add((Component)this.leftControlPanel, gbc_controlPanel);
        this.imagePanel = new ImagePanel();
        this.imagePanel.setBorder(new EmptyBorder(5, 0, 5, 0));
        GridBagConstraints gbc_imagePanel = new GridBagConstraints();
        gbc_imagePanel.insets = new Insets(0, 0, 0, 5);
        gbc_imagePanel.fill = 1;
        gbc_imagePanel.gridx = 1;
        gbc_imagePanel.gridy = 0;
        this.contentPane.add((Component)this.imagePanel, gbc_imagePanel);
        this.rightControlPanel = new RightControlPanel();
        this.rightControlPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = 1;
        gbc_scrollPane.gridx = 2;
        gbc_scrollPane.gridy = 0;
        this.contentPane.add((Component)this.rightControlPanel, gbc_scrollPane);
        this.setJMenuBar(new MainMenu());
    }

    public static DataController getControllers() {
        return controllers;
    }

    public static void addController(IController ctrl) {
        controllers.addController(ctrl);
    }

    public static IController getController(Controllers ctrl) {
        return controllers.getController(ctrl);
    }

    public static void notifyController(Controllers ctrl) {
        controllers.notifyController(ctrl);
    }

}

