/*
 * Decompiled with CFR 0_123.
 */
package controller.leftPane;

import controller.UtilityClass;
import controller.centerPane.ImagePaneController;
import enums.Controllers;
import exceptions.NotYetImplementedException;
import interfaces.IController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JFileChooser;
import model.ImagePanelModel;
import view.MainWindow;
import view.dialogWindow.OptionsWindow;
import view.dialogWindow.ShowDicomTags;
import view.leftPane.LeftControlPanel;

public class LeftControlPanelController
implements IController,
ActionListener {
    private LeftControlPanel view;

    public LeftControlPanelController(LeftControlPanel view) {
        this.view = view;
    }

    @Override
    public Object getView() {
        return this.view;
    }

    @Override
    public Object getModel() {
        return MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
    }

    @Override
    public void setModel(Object model) {
        MainWindow.getController(Controllers.IMAGE_PANE_CTRL).setModel(model);
    }

    @Override
    public void notifyController() {
        ImagePanelModel ipm = (ImagePanelModel)MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
        boolean enabled = ipm.getActualSnapshot()>=0;
        this.view.getBtnSaveCurrentSnapshot().setEnabled(enabled);
        this.view.getBtnSaveAllSnapshots().setEnabled(enabled);
        this.view.getBtnSnapshotInfo().setEnabled(enabled);
	enabled = ipm.getTmsDicom().size()>0;
	this.view.getBtnSaveGroup().setEnabled(enabled);
    }

    @Override
    public void notifyAllControllers() {
    }

    @Override
    public Controllers getType() {
        return Controllers.LEFT_CONTROL_PANE_CTRL;
    }

    /*
     * Exception decompiling
     */
    public void actionPerformed(ActionEvent ae) {
        String var2;
        ImagePaneController ipc;
        switch((var2 = ae.getActionCommand()).hashCode()) {
            case -931844974:
                if (var2.equals("snapshotInfo")) {
                    if (this.checkIfExistData()) {
                        new ShowDicomTags();
                    }

                    return;
                }
                break;
            case 161917698:
                if (var2.equals("saveGroup")) {
                    ipc = (ImagePaneController)MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
                    if (ipc != null) {
                        ipc.exportGroupsToFile();
                    }

                    return;
                }
                break;
            case 336623774:
                if (var2.equals("loadMri")) {
                    this.loadMri();
                    return;
                }
                break;
            case 336630356:
                if (var2.equals("loadTms")) {
                    this.loadTms();
                    return;
                }
                break;
            case 1434631203:
                if (var2.equals("settings")) {
                    new OptionsWindow();
                    return;
                }
                break;
            case 1817599292:
                if (var2.equals("saveCurrent")) {
                    if (this.checkIfExistData()) {
                        try {
                            ipc = (ImagePaneController)MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
                            ipc.getView().saveImg();
                            UtilityClass.showInfoNotification("Obrazek ulozen");
                        } catch (FileNotFoundException var5) {
                            UtilityClass.showInfoNotification("Obrázek nelze uložit, protože nejsou načtena žádná data z TMS");
                        }

                        return;
                    }

                    return;
                }
                break;
            case 1872786148:
                if (var2.equals("saveAll")) {
                    if (this.checkIfExistData()) {
                        ipc = (ImagePaneController)MainWindow.getController(Controllers.IMAGE_PANE_CTRL);
                        ipc.getView().saveImages();
                        UtilityClass.showInfoNotification("Obrazek ulozen");
                    }

                    return;
                }
        }

        try {
            throw new NotYetImplementedException("akce: " + ae.getActionCommand() + " neni dosud definovana");
        } catch (NotYetImplementedException var6) {
            var6.printStackTrace();
        }
    }

    private boolean checkIfExistData() {
        ImagePanelModel ipm = (ImagePanelModel)MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
        if (ipm.getActualSnapshot() == -1) {
            UtilityClass.showAlertNotification("Nelze prov\u00e9st po\u017eadovanou operaci, proto\u017ee nejsou na\u010dtena \u017e\u00e1dn\u00e1 data!", "Chyba");
            return false;
        }
        return true;
    }

    private void loadMri() {
        ImagePaneController imageCtrl;
        File file = this.chooseDirectory("Vyberte MRI adresar");
        if (file != null && (imageCtrl = (ImagePaneController)MainWindow.getController(Controllers.IMAGE_PANE_CTRL)) != null) {
            imageCtrl.loadMriFiles(file);
        }
    }

    private void loadTms() {
        ImagePaneController ctrl;
        File file = this.chooseDirectory("Vyberte TMS adresar");
        if (file != null && (ctrl = (ImagePaneController)MainWindow.getController(Controllers.IMAGE_PANE_CTRL)) != null) {
            ctrl.loadTmsFiles(file);
        }
    }

    private File chooseDirectory(String title) {
        JFileChooser chooser = new JFileChooser(".");
        chooser.setDialogTitle(title);
        chooser.setFileSelectionMode(1);
        if (chooser.showOpenDialog(null) == 0) {
            File f = chooser.getSelectedFile();
            return f;
        }
        return null;
    }
}

