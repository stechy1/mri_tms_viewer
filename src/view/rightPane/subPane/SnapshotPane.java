package view.rightPane.subPane;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.Configuration;
import controller.rightPane.SnapshotPaneController;
import controller.centerPane.ImagePaneController;
import enums.Controllers;
import view.MainWindow;

import javax.swing.SwingConstants;

public class SnapshotPane extends JPanel {
	
	private final String[] labels = {"I->S","R->L","A->P","I->S","R->L","A->P"};
	private final int buttons_per_line = 3;
	private final JButton[] labels_buttons = new JButton[labels.length];
	private JLabel lblSnapshot;

	private JPanel panel;
	private JSlider slider;
	private JLabel lblValue;

	/**
	 * Create the panel.
	 */
	public SnapshotPane() {

		initComponents();
	}
	private void initComponents() {
		
		SnapshotPaneController controller = new SnapshotPaneController(this);
		MainWindow.addController(controller);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		this.lblSnapshot = new JLabel("Snímek");
		GridBagConstraints gbc_lblSnapshot = new GridBagConstraints();
		gbc_lblSnapshot.anchor = GridBagConstraints.WEST;
		gbc_lblSnapshot.insets = new Insets(0, 0, 5, 0);
		gbc_lblSnapshot.gridx = 0;
		gbc_lblSnapshot.gridy = 0;
		add(this.lblSnapshot, gbc_lblSnapshot);
		
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0};
		gbl_panel.columnWeights = new double[]{0.8, 0.2, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		
		this.panel = new JPanel(gbl_panel);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		
		add(this.panel, gbc_panel);
		
		this.slider = new JSlider();
		this.slider.setValue(0);
		this.slider.setPaintTicks(true);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.insets = new Insets(0, 0, 0, 5);
		gbc_slider.gridx = 0;
		gbc_slider.gridy = 0;
		gbc_slider.gridwidth = buttons_per_line-1;
		this.panel.add(this.slider, gbc_slider);
		this.slider.addChangeListener(controller);
		this.slider.addMouseWheelListener(controller);
		this.slider.setMaximum(1000);

		GridBagConstraints gbc_sides = new GridBagConstraints();
		gbc_sides.fill = GridBagConstraints.HORIZONTAL;
		gbc_sides.insets = new Insets(0, 0, 0, 5);
		gbc_sides.gridy = 1;
		for(int x=0; x<labels.length; x++){
			final int a=x;	
			gbc_sides.gridy = 1+a/buttons_per_line;
			gbc_sides.gridx = a%buttons_per_line;
			(labels_buttons[a] = new JButton(labels[a])).addActionListener((e)->{
				for(JButton b: labels_buttons){
					b.setEnabled(true);
				}
				labels_buttons[a].setEnabled(false);
				ImagePaneController ctrl = (ImagePaneController) MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
				ctrl.changeSide(a);
			});
			this.panel.add(labels_buttons[a],gbc_sides);
		}
		this.lblValue = new JLabel("0");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.fill = GridBagConstraints.VERTICAL;
		gbc_label.anchor = GridBagConstraints.CENTER;
		gbc_label.gridx = 2;
		gbc_label.gridy = 0;
		this.panel.add(this.lblValue, gbc_label);
	}
	
	public JSlider getSlider() {
		return slider;
	}
	
	public JLabel getLblValue() {
		return lblValue;
	}
}
