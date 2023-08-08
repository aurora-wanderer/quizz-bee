package app.quizbee.component.room;

import app.quizbee.entity.Question;
import app.quizbee.entity.User;
import app.quizbee.material.panel.TransparentPanel;
import app.quizbee.modal.LoginModal;
import app.quizbee.modal.RoomModal;
import app.quizbee.system.SVGIcon;
import app.quizbee.util.FileUtils.Excel;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Created extends JDialog {

    private List<Question> questions;

    public Created(Frame parent, boolean modal) {
        super(parent, false);
        initComponents();
        this.setBackground(new Color(0, 0, 0, 0f));

        TransparentPanel tPanel = new TransparentPanel();
        tPanel.setAlpha(0.5f);
        this.setGlassPane(tPanel);
        choseFileBtn.setIcon(SVGIcon.UPLOADFILE);
        cancelButton.setIcon(SVGIcon.CLOSE);
        setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        roundedPanel1 = new app.quizbee.material.panel.RoundedPanel();
        jLabel1 = new javax.swing.JLabel();
        createButton = new app.quizbee.material.button.RoundedButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        txtName = new app.quizbee.material.field.TextInput();
        txtPass = new app.quizbee.material.field.TextInput();
        choseFileBtn = new app.quizbee.material.button.RoundedButton();
        cancelButton = new app.quizbee.material.button.RoundedButton();
        jLabel6 = new javax.swing.JLabel();
        jSpinner1 = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        roundedPanel1.setBackground(new java.awt.Color(255, 255, 255));
        roundedPanel1.setCorner(20);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(Color.black);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ROOM");

        createButton.setText("Create");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setForeground(Color.black);
        jLabel2.setText("ID");

        jLabel3.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel3.setForeground(Color.black);
        jLabel3.setText("Name");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(Color.black);
        jLabel4.setText("Password");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(Color.black);
        jLabel5.setText("Question");

        lblID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblID.setForeground(Color.black);

        txtName.setPadding(new java.awt.Insets(0, 10, 0, 0));
        txtName.setPlaceholderText("Name of room");
        txtName.setRoundRect(true);
        txtName.setShowClearButton(true);

        txtPass.setPadding(new java.awt.Insets(0, 10, 0, 0));
        txtPass.setPlaceholderText("Password of room (or not)");
        txtPass.setRoundRect(true);
        txtPass.setShowClearButton(true);

        choseFileBtn.setText("Chose file...");
        choseFileBtn.setButtonType(com.formdev.flatlaf.extras.components.FlatButton.ButtonType.none);
        choseFileBtn.setContentAreaFilled(false);
        choseFileBtn.setIconTextGap(15);
        choseFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                choseFileBtnActionPerformed(evt);
            }
        });

        cancelButton.setContentAreaFilled(false);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setForeground(Color.black);
        jLabel6.setText("Total Player");

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(0, 69, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(choseFileBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jSpinner1))))
                .addGap(10, 10, 10))
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(lblID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(choseFileBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE))
                .addGap(13, 13, 13)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSpinner1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(createButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(100, Short.MAX_VALUE)
                .addComponent(roundedPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed


    private void choseFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_choseFileBtnActionPerformed
        JFileChooser fc = new JFileChooser();

        fc.setAcceptAllFileFilterUsed(true);

        int result = fc.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            final String filePath = fc.getSelectedFile().getAbsolutePath();
            Excel.ExcelExtension extension = getExtension(filePath);

            try {
                questions = Excel.readFile(filePath, extension);
            } catch (IOException ex) {
                Logger.getLogger(Created.class.getName())
                        .log(Level.SEVERE, null, ex);
            }

            String fileName = filePath.substring(filePath.lastIndexOf('\\') + 1);
            choseFileBtn.setText(fileName);
            choseFileBtn.setIcon(null);
            choseFileBtn.setHorizontalAlignment(SwingConstants.LEFT);
        }
    }//GEN-LAST:event_choseFileBtnActionPerformed

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed

    }//GEN-LAST:event_createButtonActionPerformed
    public void addEventCreateButton(final ActionListener act) {
        createButton.addActionListener(act);
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            lblID.setText(
                    new DecimalFormat("0000")
                            .format(new Random()
                                    .nextLong(10000) + 1)
            );
            txtName.setText("");
            txtPass.setText("");
            choseFileBtn.setText("Chose file...");
            choseFileBtn.setIcon(SVGIcon.UPLOADFILE);
        }
        super.setVisible(b);
    }

    public RoomModal getRoomModal() {
        return new RoomModal(
                new User(LoginModal.user.toJSON()),
                questions,
                lblID.getText(),
                txtName.getText(),
                txtPass.getText(),
                new String[Integer.parseInt(jSpinner1.getValue().toString())],
                1
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.quizbee.material.button.RoundedButton cancelButton;
    private app.quizbee.material.button.RoundedButton choseFileBtn;
    private app.quizbee.material.button.RoundedButton createButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JLabel lblID;
    private app.quizbee.material.panel.RoundedPanel roundedPanel1;
    private app.quizbee.material.field.TextInput txtName;
    private app.quizbee.material.field.TextInput txtPass;
    // End of variables declaration//GEN-END:variables

    public boolean validation() {
        if (txtName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter Room's name");
            return false;
        }

        if (choseFileBtn.getIcon() != null) {
            JOptionPane.showMessageDialog(this,
                    "Please chose file to play!");

            return false;
        }

        int value = Integer.parseInt(jSpinner1.getValue().toString());
        if (value == 0) {
            JOptionPane.showMessageDialog(this,
                    "Total player must be >= 1!");
            jSpinner1.setValue(1);
            return false;
        }

        return true;
    }

    private Excel.ExcelExtension getExtension(String name) {
        if (name.endsWith("csv")) {
            return Excel.ExcelExtension.CSV;
        }

        if (name.endsWith("xlsx")) {
            return Excel.ExcelExtension.XLSX;
        } else {
            return Excel.ExcelExtension.XLS;
        }
    }
}
