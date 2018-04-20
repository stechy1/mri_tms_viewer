/*
 * Decompiled with CFR 0_123.
 */
package view.dialogWindow;

import enums.Controllers;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import view.MainWindow;

public class AboutWindow
extends JFrame {
    private JPanel contentPane;
    private JPanel contactPane;
    private JLabel lblAutorPavelSkala;
    private JLabel lblNewLabel;
    private JPanel controlPane;
    private JButton btnZavt;

    public AboutWindow() {
        this.initComponents();
        this.setTitle("Kontakt");
        this.setLocationRelativeTo((MainWindow)MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView());
        this.setVisible(true);
    }

    private void initComponents() {
        this.setDefaultCloseOperation(3);
        this.setBounds(100, 100, 300, 200);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[2];
        gbl_contentPane.rowHeights = new int[3];
        gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        this.contentPane.setLayout(gbl_contentPane);
        this.contactPane = new JPanel();
        GridBagConstraints gbc_contactPane = new GridBagConstraints();
        gbc_contactPane.insets = new Insets(0, 0, 5, 0);
        gbc_contactPane.fill = 1;
        gbc_contactPane.gridx = 0;
        gbc_contactPane.gridy = 0;
        this.contentPane.add((Component)this.contactPane, gbc_contactPane);
        this.contactPane.setLayout(new GridLayout(0, 1, 0, 0));
        this.lblAutorPavelSkala = new JLabel("Autor: Bc. Pavel Skala");
        this.contactPane.add(this.lblAutorPavelSkala);
        this.lblNewLabel = new JLabel("Email: Skala311@gmail.com");
        this.contactPane.add(this.lblNewLabel);
        this.controlPane = new JPanel();
        FlowLayout flowLayout = (FlowLayout)this.controlPane.getLayout();
        GridBagConstraints gbc_controlPane = new GridBagConstraints();
        gbc_controlPane.anchor = 15;
        gbc_controlPane.fill = 2;
        gbc_controlPane.gridx = 0;
        gbc_controlPane.gridy = 1;
        this.contentPane.add((Component)this.controlPane, gbc_controlPane);
        this.btnZavt = new JButton("Zav\u0159\u00edt");
        this.btnZavt.setVerticalAlignment(3);
        this.btnZavt.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                AboutWindow.this.dispose();
            }
        });
        this.controlPane.add(this.btnZavt);
    }

}

