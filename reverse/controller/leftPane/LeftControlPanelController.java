/*
 * Decompiled with CFR 0_123.
 */
package controller.leftPane;

import controller.UtilityClass;
import controller.centerPane.ImagePaneController;
import enums.Controllers;
import interfaces.IController;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import model.ImagePanelModel;
import view.MainWindow;
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
    @Override
    public void actionPerformed(ActionEvent ae) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:423)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:217)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:162)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:357)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:769)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:701)
        // org.benf.cfr.reader.Main.doJar(Main.java:134)
        // org.benf.cfr.reader.Main.main(Main.java:189)
        throw new IllegalStateException("Decompilation failed");
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

