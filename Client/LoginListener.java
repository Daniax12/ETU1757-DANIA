package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import allpacket.*;
import view.*;


import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class LoginListener implements MouseListener{

    private WelcomePanel welcomePanel;

    public LoginListener(WelcomePanel welcomePanel){
        this.setWelcomePanel(welcomePanel);
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        e.getSource();

        String logger = this.getWelcomePanel().getTexte().getText();    // Get the logger insert7
        Socket socket = this.getWelcomePanel().getSocket();

        PacketRequest pReq = new PacketRequest("login", logger);

        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(pReq);

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            PacketResponse pResp = (PacketResponse) in.readObject();

            String res = pResp.getResponseType();

            if(res.equals("false") == true){
                JOptionPane.showMessageDialog(null, "Please verify username insert", "Error", JOptionPane.INFORMATION_MESSAGE);
            }else{
                
                new MainPage(socket, logger); 

                JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this.getWelcomePanel());
                parent.setVisible(false);
               // parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
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


    public WelcomePanel getWelcomePanel() {
        return welcomePanel;
    }


    public void setWelcomePanel(WelcomePanel welcomePanel) {
        this.welcomePanel = welcomePanel;
    }

}