package model;

import java.net.ServerSocket;
import java.net.Socket;


import allpacket.*;
import connection.Skl;
import bd.*;

import java.io.File;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import server.Server;


public class MainThread extends Thread {

    private Socket socket;
    private Server server;
    private Skl skl;

    public MainThread(Socket socket, Server server, Skl skl){
        this.setSocket(socket);
        this.setServer(server);
        this.setSkl(skl);
    }
    
    @Override
    public void run() {
        try {

            while(true){
    
                ObjectOutputStream out = new ObjectOutputStream(this.getSocket().getOutputStream());      // Out to send the packet
                ObjectInputStream in = new ObjectInputStream(this.getSocket().getInputStream());             // In whhich receive the packet
        
                PacketRequest rev = (PacketRequest) in.readObject();                            // The received packet from client
        
                String typeAction = rev.getTypeAction();                    // Get the type action from client(createUser/login/request)
                String content = rev.getActionContent();                    // Get the content of the action
        
                PacketResponse pResp = new PacketResponse();                
        
                if(typeAction.equals("socket") == true){            // Close the client socket
                    if(content.equals("closeClient") == true){
                        pResp.setResponseType("canClose");
                        out.writeObject(pResp);

                        socket.close();
                        break;
                    } 
                } else if(typeAction.equals("createUser") == true){     // Create new Username
                    if(content.equals("") == false){
                        int verify = this.getServer().verifyUser(content);
        
                        if(verify == 0){ 
                            pResp.setResponseType("Error");
                        }else{
                            pResp.setResponseType("Yes");
                            this.getServer().createUserFolder(content);  // Create mkdir for new User
                        }
                        out.writeObject(pResp);
                    }
    
                } else if(typeAction.equals("login") == true){      // Login verification
                    int verify = this.getServer().verifyUser(content);
    
                    if(verify == 1){ 
                        pResp.setResponseType("false");
                    }else{
                        pResp.setResponseType("true");
                    }
                    out.writeObject(pResp);
    
                } else if(typeAction.equals("request") == true){        // Request -> Table
                    PacketResponse forUser  = (PacketResponse) in.readObject();
                    String nameUser = forUser.getResponseType();        // The user name
    
                    this.getSkl().setNameUser(nameUser);      // Set the name of the user
    
                    String sklreq = rev.getActionContent();      // Get the main content
                    Table table = skl.connectionQuery(sklreq);      // Execute the query
    
                    pResp.setResponseType("Table");
                    pResp.setTable(table);
    
                    out.writeObject(pResp);     // Send the packet
                
                }
            } 

        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        
    }



    public Socket getSocket() {
        return socket;
    }



    public void setSocket(Socket socket) {
        this.socket = socket;
    }



    public Server getServer() {
        return server;
    }



    public void setServer(Server server) {
        this.server = server;
    }

    public Skl getSkl() {
        return skl;
    }

    public void setSkl(Skl skl) {
        this.skl = skl;
    }
}