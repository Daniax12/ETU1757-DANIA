package view;

import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.DimensionUIResource;
import controller.*;
import view.RegisterPanel;

public class RegisterPage extends JFrame{
    private RegisterPanel registerPanel;

    public RegisterPage(Socket socket){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setRegisterPanel(new RegisterPanel(socket));
        this.setTitle("MySKawhiL");
        this.setResizable(false);
     
        this.setSize(400, 400);
        this.setLayout(null);
        this.getRegisterPanel().setBounds(0, 0, 400, 400);
        this.add(this.getRegisterPanel());
        this.setLocationRelativeTo(null);


        this.setVisible(true);
    }

    public RegisterPanel getRegisterPanel() {
        return registerPanel;
    }

    public void setRegisterPanel(RegisterPanel registerPanel) {
        this.registerPanel = registerPanel;
    }


}