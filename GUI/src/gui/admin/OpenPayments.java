package gui.admin;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import service.Session;
import util.RemoteProvider;
import util.Utils;

/**
 *
 * @author llama
 */
public class OpenPayments extends javax.swing.JFrame {

    private final Session session;
    private List<Map<String,Object>> payments;
    
    private DefaultTableModel tableModel = 
            new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {"Id", "EVENT", "WINNER", "STATUS"}
            ) 
            {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
                };
                
                boolean[] canEdit = new boolean [] {
                    false, false, false, false
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
                
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            };
    
    public OpenPayments(Session session) {
        initComponents();
        this.session = session;
        Utils.centerFrame(this);
        update();
    }

    public void update(){
        try {
            payments = RemoteProvider.getAdminService().getOpenPayments(session);
            while (tableModel.getRowCount()>0) tableModel.removeRow(0);
            for (Map<String, Object> buf : payments) {
                Object [] row = {
                    buf.get("id"),
                    buf.get("event"),
                    buf.get("winner"),
                    buf.get("status"),
                };
                tableModel.addRow(row);
            }
        } catch (RemoteException ex) {
            throw new IllegalStateException("Can not update event list!",ex);
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Open payments");

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
        int result = JOptionPane.showConfirmDialog(this, "Do you want to change winner before you pay?", "Pay all!",JOptionPane.YES_NO_CANCEL_OPTION);
        if (result==JOptionPane.YES_OPTION) {
            long id = (long)payments.get(resultTable.getSelectedRow()).get("eventId");
            PaymentOutcomes paymentOutcomesFrame = new PaymentOutcomes(session,id,this);
            paymentOutcomesFrame.setVisible(true);
        }
        if (result==JOptionPane.NO_OPTION){
            try {
                long id = (long)payments.get(resultTable.getSelectedRow()).get("id");
                RemoteProvider.getAdminService().payAll(session, id);
                update();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Can not perform operation!\n"+ex.getMessage());
            }
        }
    }//GEN-LAST:event_resultTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable resultTable;
    // End of variables declaration//GEN-END:variables
}
