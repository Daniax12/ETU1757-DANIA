package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import allpacket.*;
import view.*;



import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class BackListener implements MouseListener{

    private RegisterPanel regist;
    private MainPanel mainPanel;

    public BackListener(RegisterPanel regist){
        this.setRegist(regist);
    }

    public BackListener(MainPanel mainPanel){
        this.setMainPanel(mainPanel);
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        e.getSource();

        Socket socket;

        try {
            if(this.getMainPanel() == null){
                socket = this.getRegist().getSocket();
    
                new WelcomePage(socket);
    
                JFrame parent = (JFrame)SwingUtilities.getWindowAncestor(this.getRegist());
                parent.setVisible(false);
            } else if(this.getRegist() == null){
                socket = this.getMainPanel().getSocket();
    
                new WelcomePage(socket);
    
                JFrame parent = (JFrame)SwingUtilities.getWindowAncestor(this.getMainPanel());
                 parent.setVisible(false);
                //parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
            }
        } catch (Exception ex) {
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


    public RegisterPanel getRegist() {
        return regist;
    }


    public void setRegist(RegisterPanel regist) {
        this.regist = regist;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

}