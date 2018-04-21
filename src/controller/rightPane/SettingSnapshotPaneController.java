/*
 * Decompiled with CFR 0_123.
 */
package controller.rightPane;

import enums.Controllers;
import interfaces.IController;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import model.ImagePanelModel;
import model.MyPoint;
import model.dialogWindow.group.GroupModel;
import view.MainWindow;
import view.rightPane.subPane.SettingSnapshotPane;

public class SettingSnapshotPaneController
implements IController,
ItemListener,
FocusListener,
KeyListener {
    private SettingSnapshotPane view;
    private MyPoint model;

    public SettingSnapshotPaneController(SettingSnapshotPane view) {
        this.view = view;
    }

    @Override
    public void notifyController() {
        if (this.model != null) {
            ImagePanelModel ipm = (ImagePanelModel)MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
            this.view.getCbGroup().setModel(new DefaultComboBoxModel<GroupModel>(ipm.getGroups().toArray(new GroupModel[ipm.getGroups().size()])));
            this.view.getCbGroup().setSelectedItem(this.model.getGroup());
            this.view.getTfAmplitude().setText(String.valueOf(this.model.getAmplitude()));
            this.view.getTfLatency().setText(String.valueOf(this.model.getLatency()));
        }
    }

    @Override
    public void notifyAllControllers() {
    }

    @Override
    public Controllers getType() {
        return Controllers.SETTING_SNAPSHOT_PANE_CTRL;
    }

    @Override
    public Object getView() {
        return null;
    }

    @Override
    public Object getModel() {
        return this.model;
    }

    @Override
    public void setModel(Object model) {
        if (model != null) {
            this.model = (MyPoint)model;
            this.notifyController();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        GroupModel group;
        if (e.getStateChange() == 2) {
            group = (GroupModel)e.getItem();
            group.getPoints().remove(this.model);
        }
        if (e.getStateChange() == 1) {
            group = (GroupModel)e.getItem();
            this.model.setGroup(group);
            if (!group.getPoints().contains(this.model)) {
                group.getPoints().add(this.model);
            }
        }
        MainWindow.getController(Controllers.IMAGE_PANE_CTRL).notifyController();
    }

    @Override
    public void focusGained(FocusEvent arg0) {
    }

    @Override
    public void focusLost(FocusEvent fe) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getSource().equals(this.view.getTfAmplitude()) && ke.getKeyCode() == 10) {
            try {
                double newAmplitude = Double.parseDouble(this.view.getTfAmplitude().getText().trim());
                this.model.setAmplitude(newAmplitude);
            }
            catch (NumberFormatException newAmplitude) {
                // empty catch block
            }
        }
        if (ke.getSource().equals(this.view.getTfLatency()) && ke.getKeyCode() == 10) {
            try {
                double newLatency = Double.parseDouble(this.view.getTfLatency().getText().trim());
                this.model.setLatency(newLatency);
            }
            catch (NumberFormatException newLatency) {
                // empty catch block
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

