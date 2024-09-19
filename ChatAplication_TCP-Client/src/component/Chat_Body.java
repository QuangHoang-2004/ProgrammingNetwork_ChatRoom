/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package component;

import app.MessageType;
import emoji.Emogi;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import model.Model_Receive_Message;
import model.Model_Send_Message;
import net.miginfocom.swing.MigLayout;
import swing.ScrollBar;

/**
 *
 * @author HOANG
 */
public class Chat_Body extends javax.swing.JPanel {

    /**
     * Creates new form Chat_Body
     */
    public Chat_Body() {
        initComponents();
        init();
    }

    private void init() {
        body.setLayout(new MigLayout("fillx", "", "5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }

    // add Item Left
    public void addItemLeft(Model_Receive_Message data) {
        if (data.getMessageType() == MessageType.TEXT) {
            Chat_Left item = new Chat_Left();
            item.setText(data.getText());
            item.setTime("10:30 PM");
            body.add(item, "wrap, w 100::80%");
        } else if (data.getMessageType() == MessageType.EMOJI) {
            Chat_Left item = new Chat_Left();
            item.setEmoji(Emogi.getInstance().getImoji(Integer.valueOf(data.getText())).getIcon());
            item.setTime("10:30 PM");
            body.add(item, "wrap, w 100::80%");
        }
        repaint();
        revalidate();
    }

    public void addItemLeft(String text, String user, String[] image) {
        Chat_Left_With_Frofile item = new Chat_Left_With_Frofile();
        item.setText(text);
        item.setImage(image);
        item.setTime("10:30 PM");
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        // ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }

    public void addItemFile(String text, String user, String fileName, String fileSize) {
        Chat_Left_With_Frofile item = new Chat_Left_With_Frofile();
        item.setText(text);
        item.setFile(fileName, fileSize);
        item.setTime("10:30 PM");
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        // ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }

    // add Item Right
    public void addItemRight(Model_Send_Message data) {
        if (data.getMessageType() == MessageType.TEXT) {
            Chat_Right item = new Chat_Right();
            item.setText(data.getText());
            item.setTime("10:30 PM");
            body.add(item, "wrap, al right, w 100::80%");
        } else if (data.getMessageType() == MessageType.EMOJI) {
            Chat_Right item = new Chat_Right();
            item.setEmoji(Emogi.getInstance().getImoji(Integer.valueOf(data.getText())).getIcon());
            item.setTime("10:30 PM");
            body.add(item, "wrap, al right, w 100::80%");
        }
        repaint();
        revalidate();
        scrollToBottom();
    }

    public void addItemFileRight(String text, String fileName, String fileSize) {
        Chat_Right item = new Chat_Right();
        item.setText(text);
        item.setFile(fileName, fileSize);
//        item.setTime("10:30 PM");
        body.add(item, "wrap, al right, w 100::80%");
        // ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }

    public void addDate(String date) {
        Chat_Date item = new Chat_Date();
        item.setDate(date);
        body.add(item, "wrap, al center");
        body.repaint();
        body.revalidate();
    }

    public void cliearChat() {
        body.removeAll();
        repaint();
        revalidate();
    }

    private void scrollToBottom() {
        JScrollBar verticalBar = sp.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        body = new javax.swing.JPanel();

        sp.setBorder(null);

        body.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 755, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
        );

        sp.setViewportView(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}