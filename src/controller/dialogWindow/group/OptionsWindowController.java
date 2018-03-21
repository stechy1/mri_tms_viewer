package controller.dialogWindow.group;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import enums.Controllers;
import exceptions.NotYetImplementedException;
import interfaces.IController;
import view.dialogWindow.OptionsWindow;

public class OptionsWindowController implements ActionListener, IController {

    private OptionsWindow view;


    public OptionsWindowController(OptionsWindow view) {
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
        return Controllers.OPTIONS_WINDOW_CTRL;
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
    public void actionPerformed(ActionEvent arg0) {
        switch (arg0.getActionCommand()) {
            case "close":
                this.view.dispose();
                break;
            default:
                try {
                    throw new NotYetImplementedException(
                        "akce nenaimplementovana: " + arg0.getActionCommand());
                } catch (NotYetImplementedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }

}
