/*
 * Decompiled with CFR 0_123.
 */
package controller;

import enums.Controllers;
import exceptions.NotYetImplementedException;
import interfaces.IController;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;
import view.MainWindow;

public class MainWindowController
implements IController,
WindowListener {
    private MainWindow view;

    public MainWindowController(MainWindow view) {
        this.view = view;
    }

    public void exitProgram() {
        int retVal = JOptionPane.showConfirmDialog(this.view, "Opravdu chcete ukoncit tento program", "Uzavrit aplikaci", 2);
        if (retVal == 0) {
            System.exit(0);
        }
    }

    @Override
    public void notifyController() {
        try {
            throw new NotYetImplementedException();
        }
        catch (NotYetImplementedException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public void notifyAllControllers() {
        try {
            throw new NotYetImplementedException();
        }
        catch (NotYetImplementedException e) {
            e.printStackTrace();
            return;
        }
    }

    @Override
    public Controllers getType() {
        return Controllers.MAIN_WINDOW_CTRL;
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
    public void windowClosing(WindowEvent arg0) {
        this.exitProgram();
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
    }
}

