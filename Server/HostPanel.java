package view;

import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.DimensionUIResource;
import server.*;
import controller.*;

public class HostPanel extends JPanel{

    private Server server;
    private JLabel statut = new JLabel("Server statut : Down");
    private JButton start;
    private JButton stop;

    public HostPanel(Server server){
        this.setServer(server);
  
        this.setPreferredSize(new DimensionUIResource(400, 400));
        this.setLayout(null);

        JLabel label = new JLabel("MySKawhiL Admin");
        label.setBounds(150, 70, 200, 25);
        this.add(label);

        this.getStatut().setBounds(100, 125, 250, 25);
        this.add(this.getStatut());

        start = new JButton("Start server");
        start.addMouseListener(new StatutListener(this, "start"));
        start.setBounds(25, 200, 150, 25);
        
        this.add(start);

        stop = new JButton("Stop server");
        stop.setBounds(225, 200, 150, 25);
        stop.addMouseListener(new StatutListener(this, "stop"));
        stop.setEnabled(false);
        this.add(stop);
    }

    public JLabel getStatut() {
        return statut;
    }

    public void setStatut(JLabel statut) {
        this.statut = statut;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public JButton getStart() {
        return start;
    }

    public void setStart(JButton start) {
        this.start = start;
    }

    public JButton getStop() {
        return stop;
    }

    public void setStop(JButton stop) {
        this.stop = stop;
    }



}