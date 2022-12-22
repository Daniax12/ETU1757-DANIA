package server;

import java.net.ServerSocket;
import java.net.Socket;


import allpacket.*;
import connection.Skl;
import model.MainThread;
import bd.*;

import java.io.File;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Server extends Thread{
    private int port  = 3192;
    private ServerSocket sc;


    @Override
    public void run() {
        try {
            
            this.setSc(new ServerSocket(port, 5));
            this.getSc().setReuseAddress(true);
            System.out.println("Server running...");

            while(true){                 
                
                Socket socket = this.getSc().accept();    // Keep the program runnning
                System.out.println("I accept");
                Skl skl = new Skl();
                
                MainThread mt = new MainThread(socket, this, skl);
                mt.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Verify user in database folder
    public int verifyUser(String nameUser) throws Exception{

        try {
            File file = new File("DATABASE");    
            String[] fileName = file.list();

            for (int i = 0; i < fileName.length; i++) {
                if(fileName[i].equals(nameUser) == true){
                    return 0;
                }
            }
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error on verifying USERS");
        }
    }

    // Create new user 
    public void createUserFolder(String name) throws Exception{
        try {
            File folder = new File("DATABASE/" + name);
            if(folder.exists() == false){
                folder.mkdirs();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error on creating user for new User");
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ServerSocket getSc() {
        return sc;
    }

    public void setSc(ServerSocket sc) {
        this.sc = sc;
    }
}
