package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import allpacket.*;
import view.WelcomePanel;


import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ExitListener implements MouseListener{

    private WelcomePanel welcome;
    
    public ExitListener(WelcomePanel welcome){
        this.setWelcome(welcome);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        e.getSource();

        PacketRequest pr = new PacketRequest("socket", "closeClient");
        Socket socket = this.getWelcome().getSocket();

        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(pr);
           // socket.close();   // Close the socket

           ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
           PacketResponse pResp = (PacketResponse) in.readObject();

           if(pResp.getResponseType().equals("canClose") == true){
                JFrame parent = (JFrame)SwingUtilities.getWindowAncestor(this.getWelcome());
                 //parent.setVisible(false);
                parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Connection failed", "Server statut", JOptionPane.ERROR_MESSAGE);
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

    public WelcomePanel getWelcome() {
        return welcome;
    }

    public void setWelcome(WelcomePanel welcome) {
        this.welcome = welcome;
    }

}