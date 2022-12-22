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


public class NewUserListener implements MouseListener{

    private RegisterPanel registerPanel;
    
    public NewUserListener(RegisterPanel registerPanel){
        this.setRegisterPanel(registerPanel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        e.getSource();

        try {
            
            Socket socket = this.getRegisterPanel().getSocket();
            String text = this.getRegisterPanel().getTexte().getText(); // Get the new 

            if(text.trim().equals("") == true){
                JOptionPane.showMessageDialog(null, "Please insert valid user name", "Server", JOptionPane.INFORMATION_MESSAGE);
            } else {
                PacketRequest pr = new PacketRequest("createUser", text);

                try {
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(pr);
        
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    PacketResponse pResp = (PacketResponse) in.readObject();
        
                    String res = pResp.getResponseType();
        
                    if(res.equals("Error") == true){
                        JOptionPane.showMessageDialog(null, "User already used, please use another name", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(null, "User "+ text + " created" , "Server", JOptionPane.INFORMATION_MESSAGE);
                        new WelcomePage(socket); 
        
                        JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this.getRegisterPanel());
                        //parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
                        parent.setVisible(false);
                    }
        
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error on creating new user", "Server", JOptionPane.INFORMATION_MESSAGE);
                }
                
            }
        } catch (Exception se) {
            se.printStackTrace();
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

    public RegisterPanel getRegisterPanel() {
        return registerPanel;
    }

    public void setRegisterPanel(RegisterPanel registerPanel) {
        this.registerPanel = registerPanel;
    }


}