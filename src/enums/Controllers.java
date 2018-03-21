package enums;

public enum Controllers {

    MAIN_WINDOW_CTRL("main_window_ctrl", 0),
    MAIN_MENU_CTRL("main_menu_ctrl", 1),

    IMAGE_PANE_CTRL("image_pane_ctrl", 2),
    LEFT_CONTROL_PANE_CTRL("left_control_pane_ctrl", 3),

    SNAPSHOT_PANE_CTRL("snapshot_pane_ctrl", 4),
    ENHANCE_IMAGE_PANE_CTRL("brightness_pane_ctrl", 5),
    SETTING_SNAPSHOT_PANE_CTRL("setting_snapshot_pane_ctrl", 6),
    PATIENT_INFO_PANE_CTRL("patient_info_pane_ctrl", 7),

    SHOW_DICOM_TAGS_CTRL("show_dicom_tags_ctrl", 8),
    GROUPS_OPTION_PANE_CTRL("groups_option_pane_ctrl", 9),

    OPTIONS_WINDOW_CTRL("options_window_ctrl", 10);


    private String title;
    private int index;

    private Controllers(String title, int index) {
        this.title = title;
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public int getIndex() {
        return index;
    }
}
