/*
 * Decompiled with CFR 0_123.
 */
package controller.dialogWindow.group;

import enums.Controllers;
import interfaces.IController;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.dialogWindow.group.GroupModel;
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
}

