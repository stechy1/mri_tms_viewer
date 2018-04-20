/*
 * Decompiled with CFR 0_123.
 */
package controller.dialogWindow;

import enums.Controllers;
import exceptions.NotYetImplementedException;
import interfaces.IController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MainWindow;
import view.dialogWindow.ShowDicomTags;

public class ShowDicomTagsController
implements IController,
ActionListener {
    private ShowDicomTags view;

    public ShowDicomTagsController(ShowDicomTags view) {
        this.view = view;
    }

    @Override
    public void notifyController() {
    }

    @Override
    public void notifyAllControllers() {
    }

    @Override
    public Controllers getType() {
        return Controllers.SHOW_DICOM_TAGS_CTRL;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        String string = e.getActionCommand();
        switch (string.hashCode()) {
            case 3548: {
                if (string.equals("ok")) {
                    ((MainWindow)MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView()).setEnabled(true);
                    this.view.dispose();
                    break;
                }
            }
            default: {
                try {
                    throw new NotYetImplementedException("nenadefinovana akce: " + e.getActionCommand());
                }
                catch (NotYetImplementedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

