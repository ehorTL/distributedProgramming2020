package client;

import datastructures.City;
import datastructures.Country;
import messageexchanger.MessageExchanger;
import server.QueryHandler;

import java.io.*;
import java.net.Socket;

public class Client {
    private String hostname = "localhost";
    private int port = 5000;
    private Socket clientSocket = null;

    public Client(String hostname, int port){
        this.port = port;
        this.hostname = hostname;
    }

    public void start() throws IOException {
        clientSocket = new Socket(hostname, port);
    }

    private void showStatusMessage(MessageExchanger me){
        System.out.println("RESPONSE STATUS: " + (me.responseStatus ? "ok" : "Operation failed or result set is empty"));
    }

    public void scriptCustom(){
        try {
            MessageExchanger messResponse = null;

            sendMessageSerializable(QueryConstructor.getAllCountries());
            messResponse = readMessageSerializable();
            showStatusMessage(messResponse);
            if (messResponse.responseStatus){
                messResponse.countries.forEach(c -> System.out.println(c));
            }

            sendMessageSerializable(QueryConstructor.getAllCities());
            messResponse = readMessageSerializable();
            showStatusMessage(messResponse);
            if (messResponse.responseStatus){
                messResponse.cities.forEach(c -> System.out.println(c));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMessageSerializable(MessageExchanger me) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        out.writeObject(me);
    }

    public MessageExchanger readMessageSerializable() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        MessageExchanger me = (MessageExchanger) in.readObject();

        return me;
    }
}
