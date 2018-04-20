/*
 * Decompiled with CFR 0_123.
 */
package view.dialogWindow;

import controller.dialogWindow.group.OptionsWindowController;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import view.dialogWindow.options.groups.GroupsOptionPane;

public class OptionsWindow
extends JFrame {
    private JPanel contentPane;
    private JTabbedPane tabbedPane;
    private JPanel controlPane;
    private JButton btnClose;
    private GroupsOptionPane groupsOptionPane;

    public OptionsWindow() {
        this.initComponents();
        this.setTitle("Nastaven\u00ed");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void initComponents() {
        this.setDefaultCloseOperation(2);
        this.setBounds(100, 100, 450, 300);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        OptionsWindowController controller = new OptionsWindowController(this);
        gbl_contentPane.columnWidths = new int[2];
        gbl_contentPane.rowHeights = new int[3];
        gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        this.contentPane.setLayout(gbl_contentPane);
        this.tabbedPane = new JTabbedPane(1);
        GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
        gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
        gbc_tabbedPane.fill = 1;
        gbc_tabbedPane.gridx = 0;
        gbc_tabbedPane.gridy = 0;
        this.contentPane.add((Component)this.tabbedPane, gbc_tabbedPane);
        this.groupsOptionPane = new GroupsOptionPane();
        this.tabbedPane.addTab("Skupiny", null, this.groupsOptionPane, null);
        this.controlPane = new JPanel();
        GridBagConstraints gbc_controlPane = new GridBagConstraints();
        gbc_controlPane.fill = 1;
        gbc_controlPane.gridx = 0;
        gbc_controlPane.gridy = 1;
        this.contentPane.add((Component)this.controlPane, gbc_controlPane);
        this.btnClose = new JButton("Zav\u0159\u00edt");
        this.btnClose.setActionCommand("close");
        this.btnClose.addActionListener(controller);
        this.controlPane.add(this.btnClose);
    }
}

