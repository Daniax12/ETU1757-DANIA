package view;

import java.io.*;
import java.util.*;
import model.*;
import bd.*;
import connection.Skl;
import server.*;

public class Main{
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception{
        
        Server server = new Server();
        new HostPage(server);

        // new Server();
    }
}