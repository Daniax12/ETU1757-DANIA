package client;

import java.net.Socket;

import javax.swing.JOptionPane;

import view.*;

public class Client{
    public static void main(String[] args) throws Exception{

        String ip = JOptionPane.showInputDialog(null, "Enter Server IP", "Server", JOptionPane.QUESTION_MESSAGE);

        try {
            Socket socket = new Socket(ip, 3192);
            new WelcomePage(socket);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Server not running", "Server", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}