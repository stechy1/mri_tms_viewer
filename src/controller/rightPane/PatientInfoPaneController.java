package controller.rightPane;

import controller.centerPane.ImagePaneController;
import enums.Controllers;
import enums.DicomTags;
import ij.plugin.DICOM;
import ij.util.DicomTools;
import interfaces.IController;
import model.DicomTag;
import model.ImagePanelModel;
import view.MainWindow;
import view.rightPane.subPane.PatientInfoPane;

public class PatientInfoPaneController implements IController {

    private PatientInfoPane view;

    public PatientInfoPaneController(PatientInfoPane view) {
        this.view = view;
    }


    @Override
    public void notifyController() {
        if (this.getModel() != null) {
            DICOM model = (DICOM) getModel();
            this.view.getTfName()
                .setText(DicomTools.getTag(model, DicomTags.PATIENT_NAME.getIdentifier()));
            //TODO upravit na datum TMS a MRI...
            this.view.getTfDateMri()
                .setText(DicomTools.getTag(model, DicomTags.IMAGE_DATE.getIdentifier()));
            this.view.getTfDateTMS()
                .setText(DicomTools.getTag(model, DicomTags.IMAGE_DATE.getIdentifier()));

        }
    }

    @Override
    public void notifyAllControllers() {
        // TODO Auto-generated method stub

    }

    @Override
    public Controllers getType() {
        return Controllers.PATIENT_INFO_PANE_CTRL;
    }

    @Override
    public Object getView() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getModel() {
        ImagePanelModel ipm = (ImagePanelModel) MainWindow
            .getController(Controllers.IMAGE_PANE_CTRL).getModel();
        if (ipm != null) {
            return ipm.getMriDicom().get(ipm.getActualSnapshot());
        }
        return null;
    }

    @Override
    public void setModel(Object model) {
        // TODO Auto-generated method stub

    }

}
