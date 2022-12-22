package view;

import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.DimensionUIResource;
import controller.*;

public class RegisterPanel extends JPanel{
    private Socket socket;
    private JTextArea texte;

    public RegisterPanel(Socket socket){
        this.setSocket(socket);
        this.setPreferredSize(new DimensionUIResource(400, 400));
        this.setLayout(null);

        JLabel label = new JLabel("Enter a user-name");
        label.setBounds(125, 70, 200, 25);
        this.add(label);

        this.setTexte(new JTextArea());
        this.getTexte().setBounds(125, 100, 150, 50);
        this.add(this.getTexte());

        JButton register = new JButton("Register");
        register.addMouseListener(new NewUserListener(this));
        register.setBounds(75, 200, 100, 25);
        
        this.add(register);

        JButton back = new JButton("Return");
        back.setBounds(225, 200, 125, 25);
        back.addMouseListener(new BackListener(this));
        this.add(back);
    }


    public Socket getSocket() {
        return socket;
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public JTextArea getTexte() {
        return texte;
    }
    public void setTexte(JTextArea texte) {
        this.texte = texte;
    }
}