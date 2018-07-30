package view;


import static controller.Configuration.DIMENSION_OF_APP;
import static controller.Configuration.TITLE_OF_APP;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import java.io.File;

import controller.DataController;
import controller.MainWindowController;
import enums.Controllers;
import interfaces.IController;
import view.centerPane.ImagePanel;
import view.leftPane.LeftControlPanel;
import view.rightPane.RightControlPanel;
import view.dialogWindow.MyLoader;
import model.dialogWindow.LoaderTask;
import controller.centerPane.ImagePaneController;

public class MainWindow extends JFrame {

	private static DataController controllers = new DataController();

	private JPanel contentPane;
	private ImagePanel imagePanel;
	private LeftControlPanel leftControlPanel;
	private RightControlPanel rightControlPanel;
	
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainWindow(args);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public MainWindow(String[] args) {
		initComponents();
		this.setVisible(true);
		tryParameters(args);
	}
	
	
	private void initComponents() {
		
		MainWindowController controller = new MainWindowController(this);
		addController(controller);
		
		this.setTitle(TITLE_OF_APP);

		this.setSize(DIMENSION_OF_APP);
		this.setPreferredSize(DIMENSION_OF_APP);
		this.setMinimumSize(DIMENSION_OF_APP);
		
		this.setLocationRelativeTo(null);
//		this.setIconImage(Toolkit.getDefaultToolkit().createImage("resources/webTest.png"));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(controller);
		
		
		this.contentPane = new JPanel();
		setContentPane(this.contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 1, 0};
		gbl_contentPane.rowHeights = new int[]{0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{1.0};
		this.contentPane.setLayout(gbl_contentPane);
		
		
		this.leftControlPanel = new LeftControlPanel();
		this.leftControlPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints gbc_controlPanel = new GridBagConstraints();
		gbc_controlPanel.insets = new Insets(0, 0, 0, 5);
		gbc_controlPanel.fill = GridBagConstraints.BOTH;
		gbc_controlPanel.gridx = 0;
		gbc_controlPanel.gridy = 0;
		this.contentPane.add(this.leftControlPanel, gbc_controlPanel);
		
		this.imagePanel = new ImagePanel();
		this.imagePanel.setBorder(new EmptyBorder(5, 0, 5, 0));
		JTabbedPane jtb = new JTabbedPane();
		jtb.addTab("Rez",this.imagePanel);
		GridBagConstraints gbc_imagePanel = new GridBagConstraints();
		gbc_imagePanel.insets = new Insets(0, 0, 0, 5);
		gbc_imagePanel.fill = GridBagConstraints.BOTH;
		gbc_imagePanel.gridx = 1;
		gbc_imagePanel.gridy = 0;
		this.contentPane.add(jtb, gbc_imagePanel);
		
		this.rightControlPanel = new RightControlPanel();
		this.rightControlPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 0;
		this.contentPane.add(this.rightControlPanel, gbc_scrollPane);
		
		this.setJMenuBar(new MainMenu());
		
		//this.pack();
	}
	
	public static DataController getControllers() {
		return controllers;
	}
	
	public static void addController(IController ctrl){
		controllers.addController(ctrl);
	}
	
	public static IController getController(Controllers ctrl){
		return controllers.getController(ctrl);
	}
	
	public static void notifyController(Controllers ctrl){
		controllers.notifyController(ctrl);
	}

	public void tryParameters(String[] args){
		if(args.length>=1){
			try{
				ImagePaneController ctrl = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
				if(ctrl != null){
					LoaderTask lt = new LoaderTask(new File(args[0]),ctrl.getModel(),true);
					MyLoader ml = new MyLoader();
					lt.linkToProgressBar(ml.getProgressBar());
					lt.linkToJFrame(ml);
					lt.execute();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(args.length>=2){
			try{
				ImagePaneController ctrl = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
				if(ctrl != null){
					LoaderTask lt = new LoaderTask(new File(args[1]),ctrl.getModel(),false);
					MyLoader ml = new MyLoader();
					lt.linkToProgressBar(ml.getProgressBar());
					lt.linkToJFrame(ml);
					lt.execute();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
