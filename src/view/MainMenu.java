package view;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import controller.MainMenuController;

import javax.swing.JMenu;

public class MainMenu extends JMenuBar {
	private JMenu mFile;
	private JMenuItem miClose;
	private JMenu mAbout;
	private JMenuItem miHelp;
	
	
	
	public MainMenu() {
		initComponents();
	}
	
	private void initComponents() {
		
		
		MainMenuController controller = new MainMenuController(this);
		MainWindow.addController(controller);
		
		this.mFile = new JMenu("Soubor");
		add(this.mFile);
		
		this.miClose = new JMenuItem("Zavřít");
		this.miClose.setActionCommand("close");
		this.miClose.addActionListener(controller);
		this.mFile.add(this.miClose);
		
		this.mAbout = new JMenu("O aplikaci");
		add(this.mAbout);
		
		this.miHelp = new JMenuItem("Kontakt");
		this.miHelp.setActionCommand("contact");
		this.miHelp.addActionListener(controller);
		this.mAbout.add(this.miHelp);
	}

	
}
