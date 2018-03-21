package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import enums.Controllers;
import exceptions.NotYetImplementedException;
import interfaces.IController;
import view.MainMenu;
import view.MainWindow;
import view.dialogWindow.AboutWindow;

public class MainMenuController implements IController, ActionListener {

    private MainMenu view;

    public MainMenuController(MainMenu view) {
        this.view = view;
    }

    @Override
    public void notifyController() {
        // TODO Auto-generated method stub

    }

    @Override
    public void notifyAllControllers() {
        // TODO Auto-generated method stub

    }

    @Override
    public Controllers getType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getView() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getModel() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setModel(Object model) {
        // TODO Auto-generated method stub

    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "close":
                ((MainWindowController) MainWindow.getController(Controllers.MAIN_WINDOW_CTRL))
                    .exitProgram();
                break;
            case "contact":
                new AboutWindow();
                break;
            default:
                try {
                    throw new NotYetImplementedException(
                        "Akce u menu nebyla definovana: " + ae.getActionCommand());
                } catch (NotYetImplementedException e) {
                    e.printStackTrace();
                }
        }
    }

}
