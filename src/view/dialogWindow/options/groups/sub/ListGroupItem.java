package view.dialogWindow.options.groups.sub;

import javax.swing.JPanel;

import controller.dialogWindow.group.ListGroupItemController;
import model.dialogWindow.group.GroupModel;

public class ListGroupItem extends JPanel {


    public ListGroupItem(GroupModel model) {

        ListGroupItemController controller = new ListGroupItemController(this, model);
    }

}
