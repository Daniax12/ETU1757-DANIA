package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import allpacket.*;
import bd.Table;
import view.*;



import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class RequestListener implements MouseListener{

    private MainPanel mainPanel;

    public RequestListener(MainPanel mainPanel){
        this.setMainPanel(mainPanel);
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        e.getSource();

        String req = this.getMainPanel().getReq().getText();
        Socket socket = this.getMainPanel().getSocket();

        PacketRequest pReq = new PacketRequest("request", req);
        PacketResponse pTemp  = new PacketResponse();
        pTemp.setResponseType(this.getMainPanel().getNameUser());       // Send the nameUser

        try {

            if(socket.isConnected() == true){

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(pReq);
                out.writeObject(pTemp);
    
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                PacketResponse pResp = (PacketResponse) in.readObject();
    
                Table table = pResp.getTable();     // Get the returning table from request
    
                JScrollPane pane = this.getMainPanel().getPane();
    
                Vector<String> title = table.getTitles();
                Vector<Vector<Object>> data = table.getAllData();
                JTable mainTable = new JTable(data, title);
                mainTable.setEnabled(false);
    
                //pane.add(mainTable);
                this.getMainPanel().getPane().setViewportView(mainTable);
             
            } else {
                JOptionPane.showMessageDialog(null, "Connection failed", "Server statut", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }


    public MainPanel getMainPanel() {
        return mainPanel;
    }


    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }


}