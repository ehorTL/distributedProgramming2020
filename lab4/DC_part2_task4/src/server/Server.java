package server;
import server.rmiobjects.RMIWorldMapObj;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static final int PORT = 1099;
    public static final String REMOTE_WORLD_MAP_REG_NAME = "worldmap";

    public Server() {
        try {
            Registry registry = LocateRegistry.createRegistry(PORT);
            RMIWorldMapObj worldMapObj = new RMIWorldMapObj();
            registry.rebind(REMOTE_WORLD_MAP_REG_NAME, worldMapObj);

            System.out.println("SERVER STARTED");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
