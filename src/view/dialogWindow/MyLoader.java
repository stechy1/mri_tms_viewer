package view.dialogWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.Configuration;
import enums.Controllers;
import view.MainWindow;

import javax.swing.JProgressBar;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.net.URL;

public class MyLoader extends JFrame {

	private JPanel contentPane;
	
	private JLabel lblTitle;
	private JProgressBar progressBar;
	private JLabel lblImage;

	
	/**
	 * Create the frame.
	 */
	public MyLoader() {
		initComponents();
		this.setTitle("Načítání souborů");
		this.setLocationRelativeTo((MainWindow) MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView());
		this.pack();
		this.setVisible(true);
	}
	
	private void initComponents() {
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		
		this.contentPane = new JPanel(gbl_contentPane);
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.setContentPane(this.contentPane);
		
		
		this.lblTitle = new JLabel("Probíhá načítání požadovaných souborů, prosím čekejte");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(5, 5, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		this.contentPane.add(this.lblTitle, gbc_lblTitle);
				
		this.lblImage = new JLabel(new ImageIcon(Configuration.LOADER_GIF_PATH));
		GridBagConstraints gbc_lblImage = new GridBagConstraints();
		gbc_lblImage.gridx = 0;
		gbc_lblImage.gridy = 1;
		this.contentPane.add(this.lblImage, gbc_lblImage);
		
		
		
		//this.progressBar = new JProgressBar(0, 255);
		//this.progressBar.setStringPainted(true);
		//this.progressBar.setValue(0);
		
		//GridBagConstraints gbc_progressBar = new GridBagConstraints();
		//gbc_progressBar.insets = new Insets(5, 5, 5, 5);
		//gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
		//gbc_progressBar.gridx = 0;
		//gbc_progressBar.gridy = 1;
		
		//this.contentPane.add(this.progressBar, gbc_progressBar);
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}
	
	public void incProgress(){
		int nextVal = this.progressBar.getValue();
		if(nextVal <= this.progressBar.getMaximum()){
			this.progressBar.setValue(nextVal);
		}
	}

}
