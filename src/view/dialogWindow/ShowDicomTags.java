package view.dialogWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import controller.UtilityClass;
import controller.dialogWindow.ShowDicomTagsController;
import enums.Controllers;
import ij.plugin.DICOM;

import java.awt.GridBagLayout;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import java.awt.GridBagConstraints;
import model.DicomTagsTable;
import model.ImagePanelModel;
import view.MainWindow;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Insets;

public class ShowDicomTags extends JFrame {
	
	private DicomTagsTable dicomTableMRI;
	private DicomTagsTable dicomTableTMS;
	
	private JPanel contentPane; 
	
	private JTabbedPane tabs;
	private JScrollPane scrollPaneMri;
	private JScrollPane scrollPaneTms;
	private JPanel controlPanel;
	private JButton btnOk;
	
		
	public ShowDicomTags() {
			
		this.initComponents();
		
		this.pack();
		this.setVisible(true);
	}
	
	private void initComponents() {
		
		this.setAlwaysOnTop(true);
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Informace o souboru");
		this.setLocationRelativeTo(null);
		this.setBounds(100, 100, 450, 300);
		
		ShowDicomTagsController controller = new ShowDicomTagsController(this);
		MainWindow.addController(controller);
		
		this.contentPane = new JPanel();
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{116, 0};
		gbl_contentPane.rowHeights = new int[]{33, 33, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		this.contentPane.setLayout(gbl_contentPane);
		
		this.setContentPane(contentPane);
		
		
		
		this.tabs = new JTabbedPane();
		
		GridBagConstraints gbc_tabs = new GridBagConstraints();
		gbc_tabs.fill = GridBagConstraints.BOTH;
		gbc_tabs.insets = new Insets(0, 0, 5, 0);
		gbc_tabs.gridx = 0;
		gbc_tabs.gridy = 0;
		this.contentPane.add(this.tabs, gbc_tabs);
		
		this.controlPanel = new JPanel();
		GridBagConstraints gbc_controlPanel = new GridBagConstraints();
		gbc_controlPanel.fill = GridBagConstraints.BOTH;
		gbc_controlPanel.gridx = 0;
		gbc_controlPanel.gridy = 1;
		this.contentPane.add(this.controlPanel, gbc_controlPanel);
		
		this.btnOk = new JButton("Ok");
		this.btnOk.setActionCommand("ok");
		this.btnOk.addActionListener(controller);
		this.controlPanel.add(this.btnOk);
		
		ImagePanelModel ipm = (ImagePanelModel) MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
				
		this.dicomTableMRI = new DicomTagsTable(ipm.getMriDicom().get(ipm.getActualSnapshot()));
		this.scrollPaneMri = new JScrollPane(dicomTableMRI);
		this.scrollPaneMri.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scrollPaneMri.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.tabs.addTab("MRI info", null, this.scrollPaneMri, null);
		
		//this.dicomTableTMS = new DicomTagsTable(ipm.getTmsDicom().get(ipm.getActualSnapshot()));
		//this.scrollPaneTms = new JScrollPane();
		//this.tabs.addTab("TMS info", null, this.scrollPaneTms, null);
	}
	
	public DicomTagsTable getDicomTableMRI() {
		return dicomTableMRI;
	}
	
	public DicomTagsTable getDicomTableTMS() {
		return dicomTableTMS;
	}
}
