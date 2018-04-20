/*
 * Decompiled with CFR 0_123.
 */
package controller;

import enums.Controllers;
import interfaces.IController;
import java.util.ArrayList;
import java.util.List;

public class DataController {
    private List<IController> controllers = new ArrayList<IController>();

    public void addController(IController controller) {
        this.controllers.add(controller);
    }

    public void notifyController(Controllers ctrl) {
        this.getController(ctrl).notifyController();
    }

    public IController getController(Controllers ctrl) {
        for (IController controller : this.controllers) {
            if (controller.getType() != ctrl) continue;
            return controller;
        }
        return null;
    }
}

