package controller.dialogWindow.group;

import enums.Controllers;
import interfaces.IController;
import model.dialogWindow.group.GroupModel;
import view.dialogWindow.options.groups.sub.ListGroupItem;

public class ListGroupItemController implements IController {

    private ListGroupItem view;
    private GroupModel model;


    public ListGroupItemController(ListGroupItem view, GroupModel model) {
        this.model = model;
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

}
