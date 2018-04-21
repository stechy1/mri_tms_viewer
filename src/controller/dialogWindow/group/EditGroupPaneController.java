/*
 * Decompiled with CFR 0_123.
 */
package controller.dialogWindow.group;

import enums.Controllers;
import interfaces.IController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.dialogWindow.options.groups.sub.EditGroupPane;

public class EditGroupPaneController
implements IController,
ActionListener {
    private EditGroupPane view;
    private GroupModel model;

    public EditGroupPaneController(EditGroupPane view, GroupModel model) {
        this.view = view;
        this.model = model;
        this.notifyController();
    }

    @Override
    public void notifyController() {
        if (this.model != null) {
            this.view.getTfTitle().setText(this.model.getName());
            this.view.getBtnChangeColor().setForeground(this.model.getLayerColor());
        }
        this.view.getComponentPane().revalidate();
        this.view.getComponentPane().updateUI();
	
    }

    @Override
    public void notifyAllControllers() {
    }

    @Override
    public Controllers getType() {
        return null;
    }

    @Override
    public Object getView() {
        return this.view;
    }

    @Override
    public Object getModel() {
        return this.model;
    }

    @Override
    public void setModel(Object model) {
        this.model = (GroupModel)model;
    }

    /*
     * Exception decompiling
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String var2;
        switch((var2 = e.getActionCommand()).hashCode()) {
            case -1680475757:
                if (var2.equals("changeColor")) {
                    Color newColor = JColorChooser
                        .showDialog((MainWindow)MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView(), "Vyberte barvu skupiny", this.model.getLayerColor());
                    if (newColor != null) {
                        this.model.setLayerColor(newColor);
                    }

                    this.notifyController();
                }
                break;
            case 3522941:
                if (var2.equals("save")) {
                    if (this.model != null && this.view != null) {
                        this.model.setName(this.view.getTfTitle().getText());
                    }
                    MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
                    MainWindow.getController(Controllers.SETTING_SNAPSHOT_PANE_CTRL).notifyController();
                    MainWindow.getController(Controllers.GROUPS_OPTION_PANE_CTRL).notifyController();
                    this.view.dispose();
                }
        }

    }
}

