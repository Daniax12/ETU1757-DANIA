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


public class RegisterListener implements MouseListener{

    private WelcomePanel welcomePanel;

    public RegisterListener(WelcomePanel welcomePanel){
        this.setWelcomePanel(welcomePanel);
    }
    

    @Override
    public void mouseClicked(MouseEvent e) {
        e.getSource();

        try {
            new RegisterPage(this.getWelcomePanel().getSocket());

            JFrame parent = (JFrame)SwingUtilities.getWindowAncestor(this.getWelcomePanel());
            parent.setVisible(false);
        // } catch (SocketException ex) {
        //     ex.printStackTrace();
        //     JOptionPane.showMessageDialog(null, "Connection failed", "Server statut", JOptionPane.ERROR_MESSAGE);

        } catch (Exception exx){
            exx.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error on displaying register page", "Server", JOptionPane.INFORMATION_MESSAGE);
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