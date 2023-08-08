package app.quizbee.dialog.verify;

import app.quizbee.material.field.TextInput;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class Input extends javax.swing.JPanel {

    private final List<TextInput> inputs;

    public Input() {
        initComponents();
        inputs = List.of(textInput1,
                textInput2,
                textInput3,
                textInput4
        );
        inputEvent();
    }

    private void inputEvent() {
        inputs.forEach(input -> {
            input.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() != KeyEvent.VK_TAB
                            || e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                        input.setFocusable(false);
                    }
                }
            });

            input.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    super.focusGained(e);
                    input.setText("");
                }

                @Override
                public void focusLost(FocusEvent e) {
                    super.focusLost(e);
                    input.setFocusable(true);
                }
            });
        });
    }

    public String getText() {
        StringBuilder values = new StringBuilder();
        inputs.forEach((input) -> values.append(input.getText()));
        return values.toString();
    }

    public void resetText() {
        inputs.forEach(input -> input.setText(""));
    }

    @Override
    public void grabFocus() {
        super.grabFocus();
        textInput1.grabFocus();
        resetText();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textInput1 = new app.quizbee.material.field.TextInput();
        textInput2 = new app.quizbee.material.field.TextInput();
        textInput3 = new app.quizbee.material.field.TextInput();
        textInput4 = new app.quizbee.material.field.TextInput();

        textInput1.setBackground(new java.awt.Color(204, 255, 204));
        textInput1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textInput1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        textInput1.setRoundRect(true);

        textInput2.setBackground(new java.awt.Color(204, 255, 204));
        textInput2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textInput2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        textInput2.setRoundRect(true);

        textInput3.setBackground(new java.awt.Color(204, 255, 204));
        textInput3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textInput3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        textInput3.setRoundRect(true);

        textInput4.setBackground(new java.awt.Color(204, 255, 204));
        textInput4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textInput4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        textInput4.setRoundRect(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(textInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(textInput2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(textInput3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(textInput4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textInput2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textInput3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textInput4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private app.quizbee.material.field.TextInput textInput1;
    private app.quizbee.material.field.TextInput textInput2;
    private app.quizbee.material.field.TextInput textInput3;
    private app.quizbee.material.field.TextInput textInput4;
    // End of variables declaration//GEN-END:variables
}
