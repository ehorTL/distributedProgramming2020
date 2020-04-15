import java.io.IOException;
import java.io.StringReader;
import java.net.SocketTimeoutException;
import java.sql.SQLException;

import datastructures.City;
import server.*;
import client.*;

public class Main {
    public static final int port = 5000;
    public static final int timeout = 60000;
    public static final String hostname = "localhost";

    public static void main(String[] args) throws IOException {
        Server server = new Server(port, timeout);
        Client client = new Client(hostname, port);

        new Thread(() -> {
            try{
                server.start();
            } catch (IOException e){
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()-> {
            try {
                client.start();
                client.scriptCustom();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }
}
