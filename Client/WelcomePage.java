package view;

import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.DimensionUIResource;

import view.WelcomePanel;

public class WelcomePage extends JFrame{
    
    private WelcomePanel welcomePanel;

    public WelcomePage(Socket socket){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setWelcomePanel(new WelcomePanel(socket));
        this.setTitle("MySKawhiL");
        this.setResizable(false);
     
        this.setSize(400, 400);
        this.setLayout(null);
        this.getWelcomePanel().setBounds(0, 0, 400, 400);
        this.add(this.getWelcomePanel());
       // this.setLocationRelativeTo(null);

        this.setVisible(true);
    }

    public WelcomePanel getWelcomePanel() {
        return welcomePanel;
    }

    public void setWelcomePanel(WelcomePanel welcomePanel) {
        this.welcomePanel = welcomePanel;
    }



}