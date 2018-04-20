/*
 * Decompiled with CFR 0_123.
 */
package controller;

import enums.Controllers;
import exceptions.NotYetImplementedException;
import interfaces.IController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.MainMenu;
import view.MainWindow;
import view.dialogWindow.AboutWindow;

public class MainMenuController
implements IController,
ActionListener {
    private MainMenu view;

    public MainMenuController(MainMenu view) {
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
        return null;
    }

    @Override
    public Object getView() {
        return null;
    }

    @Override
    public Object getModel() {
        return null;
    }

    @Override
    public void setModel(Object model) {
    }

    /*
     * Exception decompiling
     */
    public void actionPerformed(ActionEvent ae) {
        String var2;
        switch((var2 = ae.getActionCommand()).hashCode()) {
            case 94756344:
                if (var2.equals("close")) {
                    ((MainWindowController) MainWindow.getController(Controllers.MAIN_WINDOW_CTRL)).exitProgram();
                    return;
                }
                break;
            case 951526432:
                if (var2.equals("contact")) {
                    new AboutWindow();
                    return;
                }
        }

        try {
            throw new NotYetImplementedException("Akce u menu nebyla definovana: " + ae.getActionCommand());
        } catch (NotYetImplementedException var4) {
            var4.printStackTrace();
        }
    }
}

