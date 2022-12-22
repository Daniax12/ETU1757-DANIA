package view;

import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.DimensionUIResource;

import view.*;

public class MainPage extends JFrame{
    
    private MainPanel mainPanel;

    public MainPage(Socket socket, String userName){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMainPanel(new MainPanel(socket, userName));
        this.setTitle("MySKawhiL");
        this.setResizable(false);
     
        this.setSize(400, 400);
        this.setLayout(null);
        this.getMainPanel().setBounds(0, 0, 400, 400);
        this.add(this.getMainPanel());
       // this.setLocationRelativeTo(null);

        this.setVisible(true);
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

}