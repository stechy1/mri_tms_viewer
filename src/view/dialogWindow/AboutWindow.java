package view.dialogWindow;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import enums.Controllers;
import ij.gui.MultiLineLabel;
import view.MainWindow;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class AboutWindow extends JFrame {

    private JPanel contentPane;
    private JPanel contactPane;
    private JLabel lblAutorPavelSkala;
    private JLabel lblNewLabel;
    private JPanel controlPane;
    private JButton btnZavt;

    /**
     * Create the frame.
     */
    public AboutWindow() {
        initComponents();

        this.setTitle("Kontakt");
        this.setLocationRelativeTo(
            (MainWindow) MainWindow.getController(Controllers.MAIN_WINDOW_CTRL).getView());
        this.setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 300, 200);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(this.contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        this.contentPane.setLayout(gbl_contentPane);

        this.contactPane = new JPanel();
        GridBagConstraints gbc_contactPane = new GridBagConstraints();
        gbc_contactPane.insets = new Insets(0, 0, 5, 0);
        gbc_contactPane.fill = GridBagConstraints.BOTH;
        gbc_contactPane.gridx = 0;
        gbc_contactPane.gridy = 0;
        this.contentPane.add(this.contactPane, gbc_contactPane);
        this.contactPane.setLayout(new GridLayout(0, 1, 0, 0));

        this.lblAutorPavelSkala = new JLabel("Autor: Bc. Pavel Skala");
        this.contactPane.add(this.lblAutorPavelSkala);

        this.lblNewLabel = new JLabel("Email: Skala311@gmail.com");
        this.contactPane.add(this.lblNewLabel);

        this.controlPane = new JPanel();
        FlowLayout flowLayout = (FlowLayout) this.controlPane.getLayout();
        GridBagConstraints gbc_controlPane = new GridBagConstraints();
        gbc_controlPane.anchor = GridBagConstraints.SOUTH;
        gbc_controlPane.fill = GridBagConstraints.HORIZONTAL;
        gbc_controlPane.gridx = 0;
        gbc_controlPane.gridy = 1;
        this.contentPane.add(this.controlPane, gbc_controlPane);

        this.btnZavt = new JButton("Zavřít");
        this.btnZavt.setVerticalAlignment(SwingConstants.BOTTOM);
        this.btnZavt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.controlPane.add(this.btnZavt);
    }


}
