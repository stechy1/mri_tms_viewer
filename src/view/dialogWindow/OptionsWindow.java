package view.dialogWindow;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import controller.dialogWindow.group.OptionsWindowController;
import exceptions.NotYetImplementedException;
import view.MainWindow;
import view.dialogWindow.options.groups.GroupsOptionPane;
import view.dialogWindow.options.groups.OptionPane;

public class OptionsWindow extends JFrame{

	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private JPanel controlPane;
	private JButton btnClose;
	private GroupsOptionPane groupsOptionPane;
	private OptionPane optionPane;

	
	/**
	 * Create the frame.
	 */
	public OptionsWindow() {
		initComponents();
	
		this.setTitle("Nastavení");
		this.setLocationRelativeTo(null);
		//this.pack();
		this.setVisible(true);
	}
	private void initComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(this.contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		
		OptionsWindowController controller = new OptionsWindowController(this);
		
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		this.contentPane.setLayout(gbl_contentPane);
		
		this.tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		this.contentPane.add(this.tabbedPane, gbc_tabbedPane);
		
		this.groupsOptionPane = new GroupsOptionPane();
		this.optionPane = new OptionPane();
		this.tabbedPane.addTab("Skupiny", null, this.groupsOptionPane, null);
		this.tabbedPane.addTab("Dalsi nastaveni", null, this.optionPane, null);
		
		this.controlPane = new JPanel();
		GridBagConstraints gbc_controlPane = new GridBagConstraints();
		gbc_controlPane.fill = GridBagConstraints.BOTH;
		gbc_controlPane.gridx = 0;
		gbc_controlPane.gridy = 1;
		this.contentPane.add(this.controlPane, gbc_controlPane);
		
		this.btnClose = new JButton("Zavřít");
		this.btnClose.setActionCommand("close");
		this.btnClose.addActionListener(controller);
		this.controlPane.add(this.btnClose);
	}
}
