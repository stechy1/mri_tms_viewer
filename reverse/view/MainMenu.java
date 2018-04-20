/*
 * Decompiled with CFR 0_123.
 */
package view;

import controller.MainMenuController;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import view.MainWindow;

public class MainMenu
extends JMenuBar {
    private JMenu mFile;
    private JMenuItem miClose;
    private JMenu mAbout;
    private JMenuItem miHelp;

    public MainMenu() {
        this.initComponents();
    }

    private void initComponents() {
        MainMenuController controller = new MainMenuController(this);
        MainWindow.addController(controller);
        this.mFile = new JMenu("Soubor");
        this.add(this.mFile);
        this.miClose = new JMenuItem("Zav\u0159\u00edt");
        this.miClose.setActionCommand("close");
        this.miClose.addActionListener(controller);
        this.mFile.add(this.miClose);
        this.mAbout = new JMenu("O aplikaci");
        this.add(this.mAbout);
        this.miHelp = new JMenuItem("Kontakt");
        this.miHelp.setActionCommand("contact");
        this.miHelp.addActionListener(controller);
        this.mAbout.add(this.miHelp);
    }
}

