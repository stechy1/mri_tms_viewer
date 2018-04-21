/*
 * Decompiled with CFR 0_123.
 */
package controller.dialogWindow.group;

import controller.UtilityClass;
import enums.Controllers;
import exceptions.NotYetImplementedException;
import interfaces.IController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.ImagePanelModel;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.dialogWindow.options.groups.GroupsOptionPane;

public class GroupsOptionPaneController
implements IController,
ActionListener {
    private GroupsOptionPane view;

    public GroupsOptionPaneController(GroupsOptionPane view) {
        this.view = view;
    }

    @Override
    public void notifyController() {
        MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
	this.view.getGroupsPane().initComponents();
        this.view.revalidate();
        this.view.updateUI();
    }

    @Override
    public void notifyAllControllers() {
    }

    @Override
    public Controllers getType() {
        return Controllers.GROUPS_OPTION_PANE_CTRL;
    }

    @Override
    public Object getView() {
        return this.view;
    }

    @Override
    public Object getModel() {
        return null;
    }

    @Override
    public void setModel(Object model) {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        block10 : {
            block9 : {
                String var2_2 = e.getActionCommand();
                switch (var2_2.hashCode()) {
                    case -1408204561: {
                        if (!var2_2.equals("assign")) {
                            break;
                        }
                        break block9;
                    }
                    case -1352294148: {
                        if (var2_2.equals("create")) break;
                        break;
                    }
                    case 96417: {
                        if (var2_2.equals("add")) {
                            this.add();
                            return;
                        }
                        break block10;
                    }
                }
                this.create();
                this.notifyController();
                return;
            }
            this.assign();
            this.notifyController();
            return;
        }
        try {
            throw new NotYetImplementedException("akce nebyla implementovana: " + e.getActionCommand());
        }
        catch (NotYetImplementedException e1) {
            System.err.println(e1.getMessage());
            e1.printStackTrace();
        }
    }

    private void assign() {
    }

    private void add() {
        try {
            this.addGroup();
        }
        catch (Exception e1) {
            UtilityClass.showAlertNotification(e1.getMessage());
        }
    }

    private void create() {
        ImagePanelModel ipm = (ImagePanelModel)MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
        if (ipm.getGroups() != null) {
            if (ipm.getGroups().size() != 0) {
                String count_ret = JOptionPane.showInputDialog((MainWindow)MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView(), (Object)"Po\u010det skupin");
                int count = -1;
                try {
                    count = Integer.parseInt(count_ret);
                }
                catch (NumberFormatException e2) {
                    UtilityClass.showAlertNotification("Mus\u00edte zadat celo\u010d\u00edselnou hodnotu!");
                    return;
                }
                if (count > 0 && ipm != null) {
                    ipm.createGroups(count);
                }
            } else {
                UtilityClass.showAlertNotification("Nelze vytvo\u0159it skupiny, proto\u017ee nejsou na\u010dteny \u017e\u00e1dn\u00e9 body.");
            }
        } else {
            UtilityClass.showAlertNotification("Nelze vytvo\u0159it skupiny, proto\u017ee nejsou na\u010dteny \u017e\u00e1dn\u00e9 body.");
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void addGroup() throws Exception {
        ImagePanelModel ipm = (ImagePanelModel)MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
        if (ipm.getMriDicom() == null) throw new Exception("Nelze p\u0159id\u00e1vat skupiny dokud nen\u00ed vybr\u00e1n zdroj MRI sm\u00ednk\u016f");
        if (ipm.getMriDicom().size() == 0) throw new Exception("Nelze p\u0159id\u00e1vat skupiny dokud nen\u00ed vybr\u00e1n zdroj MRI sm\u00ednk\u016f");
        GroupModel newGroup = new GroupModel("skupina " + ipm.getGroups().size());
        ipm.getGroups().add(newGroup);
        new view.dialogWindow.options.groups.sub.EditGroupPane(newGroup);
    }
}

