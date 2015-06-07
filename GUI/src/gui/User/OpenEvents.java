/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.User;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import service.Session;
import util.RemoteProvider;
import util.Utils;

/**
 *
 * @author llama
 */
public class OpenEvents extends javax.swing.JFrame {

    private final Session session;
    private final List<Map<String,Object>> events;
    
    private DefaultTableModel tableModel = 
            new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {"Id", "Description", "Company", "Expires"}
            ) 
            {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
                };
                
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
                
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            };
    
    /**
     * Creates new form UserBets
     */
    public OpenEvents(Session session) {
        initComponents();
        this.session = session;
        Utils.centerFrame(this);
        try {
            events = RemoteProvider.getUserService().getOpenEvents();
            update();
        } catch (RemoteException ex) {
            throw new IllegalStateException("Can not update event list!",ex);
        }
    }

    public void update(){
            for (Map<String, Object> buf : events) {
                Object [] row = {
                    buf.get("id"),
                    buf.get("description"),
                    buf.get("company"),
                    buf.get("expires"),
                };
                tableModel.addRow(row);
            }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        resultTable.setModel(tableModel);
        resultTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(resultTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resultTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultTableMouseClicked
        if (evt.getClickCount()<2) return;
        MakeBet makeBetFrame = new MakeBet(session, (List) events.get(resultTable.getSelectedRow()).get("outcomes"));
        makeBetFrame.setVisible(true);
    }//GEN-LAST:event_resultTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable resultTable;
    // End of variables declaration//GEN-END:variables
}
