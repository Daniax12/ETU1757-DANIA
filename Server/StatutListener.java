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
import server.*;

import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class StatutListener implements MouseListener{

    private HostPanel hostPanel;
    private String trig;

    public StatutListener(HostPanel hostPanel, String trig) {
        this.setHostPanel(hostPanel);
        this.setTrig(trig);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        e.getSource();

        HostPanel host = this.getHostPanel();

        try {
            Server server = this.getHostPanel().getServer();
            if(this.getTrig().equals("start") == true){
                host.getStatut().setText("Server statut : Running . . .");
                host.getStart().setEnabled(false);
                host.getStop().setEnabled(true);

                server.start();     
               
            } else if (this.getTrig().equals("stop") == true){
                server.interrupt();
                

                JOptionPane.showMessageDialog(null, "Server is shuting down...", "Server", JOptionPane.INFORMATION_MESSAGE);

                Thread.sleep(1000);
                JFrame parent = (JFrame)SwingUtilities.getWindowAncestor(this.getHostPanel());
                parent.dispatchEvent(new WindowEvent(parent, WindowEvent.WINDOW_CLOSING));
                
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error in starting page of server", "Server", JOptionPane.INFORMATION_MESSAGE);
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

    public HostPanel getHostPanel() {
        return hostPanel;
    }

    public void setHostPanel(HostPanel hostPanel) {
        this.hostPanel = hostPanel;
    }

    public String getTrig() {
        return trig;
    }

    public void setTrig(String trig) {
        this.trig = trig;
    }
}