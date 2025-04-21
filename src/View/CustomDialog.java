package View;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

public class CustomDialog {
    public static final int CLOSED_OPTION = 0;
    public static final int OK_OPTION = 1;
    public static final int CANCEL_OPTION = 2;
    static JDialog informationDialog;
    // Error
    // Information
    public static void createInformationDialog(JFrame frame, String yourText, String okButtonName){
        informationDialog = new JDialog(frame, "Information dialog :)", true);

        informationDialog.setSize(new Dimension(450, 200));
        informationDialog.setLocationRelativeTo(null);
        informationDialog.setModal(false);
        informationDialog.getContentPane().setBackground(MainFrame.darkBackground);
        informationDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 2.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel confirmation = new JLabel(yourText);
        confirmation.setFont(MainFrame.fontBold.deriveFont(25f));
        confirmation.setForeground(MainFrame.mainColor);
        informationDialog.add(confirmation, gbc);

//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.gridwidth = 1;
//        gbc.gridheight = 1;
//        gbc.weightx = 1.0;
//        gbc.weighty = 1.0;
//        JButton cancle = new JButton("cancle");
//        cancle.setPreferredSize(new Dimension(100, 40));
//        cancle.setBackground(MainFrame.lightBackground);
//        cancle.setForeground(MainFrame.mainColor);
//        cancle.setFont(MainFrame.fontBold.deriveFont(25f));
//        cancle.setBorder(new LineBorder(MainFrame.extraLightColor, 1));
//        cancle.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                informationDialog.dispose();
//                informationDialog = null;
//            }
//        });
//        informationDialog.add(cancle, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        JButton ok = new JButton(okButtonName);
        ok.setPreferredSize(new Dimension(100, 40));
        ok.setBackground(MainFrame.lightBackground);
        ok.setForeground(MainFrame.mainColor);
        ok.setFont(MainFrame.fontBold.deriveFont(25f));
        ok.setBorder(new LineBorder(MainFrame.extraLightColor, 1));
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                informationDialog.dispose();
                informationDialog = null;
            }
        });
        informationDialog.add(ok, gbc);

        informationDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                informationDialog.removeAll();
                informationDialog.dispose();
            }
        });

        informationDialog.setVisible(true);
    }
    // Input Dialog
}
