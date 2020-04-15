package server;

import messageexchanger.MessageExchanger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    public String name = "SERVER";
    private ServerSocket serverSocket = null;
    private Socket clientSocket = null;
    public int port;
    public int timeout;

    public Server(int port, int timeout){
        this.port = port;
        this.timeout = timeout;
    }

    public void start() throws IOException, ClassNotFoundException, SQLException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(timeout);
        clientSocket = serverSocket.accept();
        System.out.println("Client connected");

        serve();
    }

    private void serve() throws IOException, ClassNotFoundException, SQLException {
        while (true){
            sendMessageSerializable(QueryHandler.serveQuery(readMessageSerializable()));
        }
    }



    public MessageExchanger readMessageSerializable() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        MessageExchanger me = (MessageExchanger)in.readObject();
        //in.close();

        return me;
    }

    public void sendMessageSerializable(MessageExchanger me) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.writeObject(me);
    }
}
