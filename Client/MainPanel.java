package view;

import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.DimensionUIResource;
import controller.*;

public class MainPanel extends JPanel{
    private Socket socket;
    private JTextArea req;
    private String nameUser;
    private JScrollPane pane = new JScrollPane();

    public MainPanel(Socket socket, String nameUser){
        this.setSocket(socket);
        this.setNameUser(nameUser);
        this.setPreferredSize(new DimensionUIResource(400, 400));

        this.setLayout(null);

        JLabel label = new JLabel("Request here: ");
        label.setBounds(5, 5, 300, 25);
        this.add(label);

        JButton back = new JButton("Back");
        back.setBounds(275, 5, 100, 25);
        back.addMouseListener(new BackListener(this));
        this.add(back);

        this.setReq(new JTextArea());
        this.getReq().setLineWrap(true);
        this.getReq().setBounds(10, 40, 300, 50);
        this.add(this.getReq());

        JButton request = new JButton("Exec");
        request.addMouseListener(new RequestListener(this));
        request.setBounds(320, 40, 70, 50);
        this.add(request);

        JLabel response = new JLabel("Response: ");
        response.setBounds(5, 100, 300, 25);
        this.add(response);

        this.getPane().setPreferredSize(new DimensionUIResource(390, 225));
        this.getPane().setBounds(5, 130, 370, 225);
        this.add(this.getPane());
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public JTextArea getReq() {
        return req;
    }

    public void setReq(JTextArea req) {
        this.req = req;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public JScrollPane getPane() {
        return pane;
    }

    public void setPane(JScrollPane pane) {
        this.pane = pane;
    }

    
}