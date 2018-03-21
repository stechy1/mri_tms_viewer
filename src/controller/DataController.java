package controller;

import java.util.ArrayList;
import java.util.List;

import enums.Controllers;
import interfaces.IController;

public class DataController {

    private List<IController> controllers = new ArrayList<IController>();

    public void addController(IController controller) {
        this.controllers.add(controller);
    }

    public void notifyController(Controllers ctrl) {
        getController(ctrl).notifyController();
    }

    public IController getController(Controllers ctrl) {
        for (IController controller : this.controllers) {
            if (controller.getType() == ctrl) {
                return controller;
            }
        }
        return null;
    }
}
