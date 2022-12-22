package view;

import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.DimensionUIResource;
import server.*;
//import controller.*;

public class HostPage extends JFrame{
    private HostPanel hostPanel;

    public HostPage(Server server){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setHostPanel(new HostPanel(server));
        this.setTitle("MySKawhiL Server");
     
        this.setSize(400, 400);
        this.setLayout(null);
        this.getHostPanel().setBounds(0, 0, 400, 400);
        this.add(this.getHostPanel());
        this.setResizable(false);

        this.setLocationRelativeTo(null);

        this.setVisible(true);
    }

    public HostPanel getHostPanel() {
        return hostPanel;
    }

    public void setHostPanel(HostPanel hostPanel) {
        this.hostPanel = hostPanel;
    }




}