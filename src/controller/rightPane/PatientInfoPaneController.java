/*
 * Decompiled with CFR 0_123.
 */
package controller.rightPane;

import enums.Controllers;
import enums.DicomTags;
import ij.plugin.DICOM;
import ij.util.DicomTools;
import interfaces.IController;
import java.util.List;
import javax.swing.JTextField;
import model.ImagePanelModel;
import model.MyDicom;
import view.MainWindow;
import view.rightPane.subPane.PatientInfoPane;

public class PatientInfoPaneController
implements IController {
    private PatientInfoPane view;

    public PatientInfoPaneController(PatientInfoPane view) {
        this.view = view;
    }

    @Override
    public void notifyController() {
        if (this.getModel() != null) {
            DICOM model = (DICOM)this.getModel();
            this.view.getTfName().setText(DicomTools.getTag(model, DicomTags.PATIENT_NAME.getIdentifier()));
            this.view.getTfDateMri().setText(DicomTools.getTag(model, DicomTags.IMAGE_DATE.getIdentifier()));
            this.view.getTfDateTMS().setText(DicomTools.getTag(model, DicomTags.IMAGE_DATE.getIdentifier()));
        }
    }

    @Override
    public void notifyAllControllers() {
    }

    @Override
    public Controllers getType() {
        return Controllers.PATIENT_INFO_PANE_CTRL;
    }

    @Override
    public Object getView() {
        return null;
    }

    @Override
    public Object getModel() {
        ImagePanelModel ipm = (ImagePanelModel)MainWindow.getController(Controllers.IMAGE_PANE_CTRL).getModel();
        if (ipm != null) {
            return ipm.getMriDicom().get(ipm.getActualSnapshot());
        }
        return null;
    }

    @Override
    public void setModel(Object model) {
    }
}

