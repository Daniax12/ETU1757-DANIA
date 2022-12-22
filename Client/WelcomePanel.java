package view;

import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.plaf.DimensionUIResource;    
import controller.*;

public class WelcomePanel extends JPanel{
    private Socket socket;
    private JTextArea texte;


    public WelcomePanel(Socket socket){
        this.setSocket(socket);
        this.setPreferredSize(new DimensionUIResource(400, 400));
        this.setLayout(null);

        JLabel label = new JLabel("Enter user name: ");
        label.setBounds(125, 50, 200, 25);
        this.add(label);

        this.setTexte(new JTextArea());
        this.getTexte().setBounds(125, 75, 150, 50);
        this.add(this.getTexte());

        JButton login = new JButton("Login");

        login.setBounds(75, 150, 100, 25);
        login.addMouseListener(new LoginListener(this));
        this.add(login);


        JButton register = new JButton("Register");
        register.setBounds(250, 150, 100, 25);
        register.addMouseListener(new RegisterListener(this));
        this.add(register);

        JButton deco = new JButton("Deconnect");
        deco.setBounds(240, 325, 100, 25);
        deco.addMouseListener(new ExitListener(this));
        this.add(deco);
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