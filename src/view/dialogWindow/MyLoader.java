/*
 * Decompiled with CFR 0_123.
 */
package view.dialogWindow;

import enums.Controllers;
import java.io.File;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import view.MainWindow;

public class MyLoader
extends JFrame {
    private JPanel contentPane;
    private JLabel lblTitle;
    private JProgressBar progressBar;
    private JLabel lblImage;

    public MyLoader() {
        this.initComponents();
        this.setTitle("Na\u010d\u00edt\u00e1n\u00ed soubor\u016f");
        this.setLocationRelativeTo((MainWindow)MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView());
        this.pack();
        this.setVisible(true);
    }

    private void initComponents() {
        this.setDefaultCloseOperation(0);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[2];
        gbl_contentPane.rowHeights = new int[3];
        gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        this.contentPane = new JPanel(gbl_contentPane);
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.lblTitle = new JLabel("Prob\u00edh\u00e1 na\u010d\u00edt\u00e1n\u00ed po\u017eadovan\u00fdch soubor\u016f, pros\u00edm \u010dekejte");
        GridBagConstraints gbc_lblTitle = new GridBagConstraints();
        gbc_lblTitle.insets = new Insets(5, 5, 5, 0);
        gbc_lblTitle.gridx = 0;
        gbc_lblTitle.gridy = 0;
        this.contentPane.add((Component)this.lblTitle, gbc_lblTitle);
        this.lblImage = new JLabel(new ImageIcon("resources"+File.separator+"loading.gif"));
        GridBagConstraints gbc_lblImage = new GridBagConstraints();
        gbc_lblImage.gridx = 0;
        gbc_lblImage.gridy = 1;
        this.contentPane.add((Component)this.lblImage, gbc_lblImage);
    }

    public JProgressBar getProgressBar() {
        return this.progressBar;
    }

    public void incProgress() {
        int nextVal = this.progressBar.getValue();
        if (nextVal <= this.progressBar.getMaximum()) {
            this.progressBar.setValue(nextVal);
        }
    }
}

