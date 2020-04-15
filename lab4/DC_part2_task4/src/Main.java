import client.Client;
import rmiinterfaces.RMIWorldMap;
import server.Server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {
    public static void main(String[] args) {
        new Thread(() -> {
            new Server();
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(10000); //wait until server started
                new Client().start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
