package app.quizbee.component.titlebar;

import app.quizbee.dialog.message.Message;
import app.quizbee.main.Application;
import app.quizbee.material.panel.RoundedPanel;
import app.quizbee.modal.LoginModal;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MacButton extends javax.swing.JPanel {

    public MacButton() {
        initComponents();
        setOpaque(false);
    }

    public void initEvent(JFrame fram, RoundedPanel panel) {
        cmdClose.addActionListener((ActionEvent ae) -> {
            Message ms = new Message(fram);
            ms.showMessage("Exit", "Are you sure exit?");
            if (ms.getMessageType() == Message.MessageType.OK) {
                if (LoginModal.user != null) {
                    Application.getDashboard()
                            .getClient()
                            .getService()
                            .logout(LoginModal.user);
                }
                System.exit(0);
            }
        });

        cmdMi.addActionListener((ActionEvent ae) -> {
            fram.setState(JFrame.ICONIFIED);
        });

        cmdRe.addActionListener((ActionEvent ae) -> {
            if (fram.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                panel.setCorner(20);
                fram.setExtendedState(JFrame.NORMAL);
            } else {
                panel.setCorner(0);
                fram.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmdRe = new app.quizbee.component.titlebar.ButtonTitleBar();
        cmdMi = new app.quizbee.component.titlebar.ButtonTitleBar();
        cmdClose = new app.quizbee.component.titlebar.ButtonTitleBar();

        cmdRe.setBackground(java.awt.Color.green);

        cmdMi.setBackground(java.awt.Color.yellow);

        cmdClose.setBackground(java.awt.Color.red);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cmdMi, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdRe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmdMi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmdRe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.quizbee.component.titlebar.ButtonTitleBar cmdClose;
    private app.quizbee.component.titlebar.ButtonTitleBar cmdMi;
    private app.quizbee.component.titlebar.ButtonTitleBar cmdRe;
    // End of variables declaration//GEN-END:variables
}
