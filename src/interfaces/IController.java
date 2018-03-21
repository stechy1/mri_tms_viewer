package interfaces;

import enums.Controllers;

public interface IController {
	
	public void notifyController();
	public void notifyAllControllers();
	public Controllers getType();
	public Object getView();
	public Object getModel();
	public void setModel(Object model);
}
