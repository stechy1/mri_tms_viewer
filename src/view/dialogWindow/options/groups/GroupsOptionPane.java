/*
 * Decompiled with CFR 0_123.
 */
package view.dialogWindow.options.groups;

import controller.dialogWindow.group.GroupsOptionPaneController;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import view.MainWindow;
import view.dialogWindow.options.groups.sub.GroupsPane;

public class GroupsOptionPane
extends JPanel {
    private GroupsPane groupsPane;
    private JPanel controlPane;
    private JButton btnCreate;

    public GroupsOptionPane() {
        this.initComponents();
    }

    private void initComponents() {
        this.setLayout(new BorderLayout(0, 0));
        GroupsOptionPaneController controller = new GroupsOptionPaneController(this);
        MainWindow.addController(controller);
        this.groupsPane = new GroupsPane();
        this.add((Component)this.groupsPane, "Center");
        this.controlPane = new JPanel();
        FlowLayout flowLayout = (FlowLayout)this.controlPane.getLayout();
        flowLayout.setAlignment(2);
        this.add((Component)this.controlPane, "South");
        this.btnCreate = new JButton("Vygeneruj skupiny");
        this.btnCreate.setActionCommand("create");
        this.btnCreate.addActionListener(controller);
        this.controlPane.add(this.btnCreate);
    }

    public GroupsPane getGroupsPane() {
        return this.groupsPane;
    }
}

