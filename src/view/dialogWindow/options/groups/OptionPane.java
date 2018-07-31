package view.dialogWindow.options.groups;

import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import controller.dialogWindow.group.OptionPaneController;
import controller.Configuration;
import view.MainWindow;

public class OptionPane extends JPanel {
	private JCheckBox check_ruler;
	private JCheckBox check_coords;
	public OptionPane() {

		initComponents();
	}
	private void initComponents() {
		setLayout(new GridLayout(0,1,5,5));
		
		OptionPaneController controller = new OptionPaneController(this);
		MainWindow.addController(controller);
		
		this.check_ruler = new JCheckBox("Zobrazuj pravitko");
		this.check_ruler.setSelected(Configuration.drawRulers);
		this.check_ruler.addActionListener(controller);
		add(this.check_ruler);
		
		this.check_coords = new JCheckBox("Zobrazuj souradnice");
		this.check_coords.setSelected(Configuration.showCoords);
		this.check_coords.addActionListener(controller);
		add(this.check_coords);
		
	}
	public JCheckBox getRulers() {
		return this.check_ruler;
	}
	public JCheckBox getCoords() {
		return this.check_coords;
	}
}
