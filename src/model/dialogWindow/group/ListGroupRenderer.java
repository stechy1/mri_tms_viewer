package model.dialogWindow.group;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

public class ListGroupRenderer extends JPanel implements ListCellRenderer<GroupModel>,
    ActionListener {

    private JTextField tfGroup;
    private JButton btnEdit;
    private JButton btnDelete;


    public ListGroupRenderer() {
        initComponents();
    }

    private void initComponents() {

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        this.tfGroup = new JTextField();
        GridBagConstraints gbc_tfGroup = new GridBagConstraints();
        gbc_tfGroup.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfGroup.insets = new Insets(0, 0, 0, 5);
        gbc_tfGroup.gridx = 0;
        gbc_tfGroup.gridy = 0;
        add(this.tfGroup, gbc_tfGroup);

        this.btnEdit = new JButton("Upravit");
        this.btnEdit.setActionCommand("edit");
        this.btnEdit.addActionListener(this);
        GridBagConstraints gbc_btnEdit = new GridBagConstraints();
        gbc_btnEdit.insets = new Insets(0, 0, 0, 5);
        gbc_btnEdit.gridx = 1;
        gbc_btnEdit.gridy = 0;
        add(this.btnEdit, gbc_btnEdit);

        this.btnDelete = new JButton("Smazat");
        this.btnDelete.setActionCommand("delete");
        this.btnDelete.addActionListener(this);
        GridBagConstraints gbc_btnDelete = new GridBagConstraints();
        gbc_btnDelete.gridx = 2;
        gbc_btnDelete.gridy = 0;
        add(this.btnDelete, gbc_btnDelete);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends GroupModel> list,
        GroupModel value,
        int index, boolean isSelected, boolean cellHasFocus) {

        this.tfGroup.setText(value.getName());
        this.tfGroup.setBackground(value.getColor());
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "edit":
                //TODO
                System.err.println("Dodelat edit");
                break;
            case "delete":
                //TODO
                System.err.println("dodelat delete");
                break;
        }
    }

}
