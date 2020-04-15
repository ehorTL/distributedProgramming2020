package client;

import datastructures.Country;
import rmiinterfaces.RMIWorldMap;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Client {
    private RMIWorldMap worldMapRemote = null;
    public static final String RMI_WORLD_MAP_ADDR = "rmi://localhost:1099/worldmap";

    public Client() throws RemoteException, NotBoundException, MalformedURLException {
        worldMapRemote = (RMIWorldMap) Naming.lookup(RMI_WORLD_MAP_ADDR);
        System.out.println("CLIENT STARTED ");
    }

    public void start() throws RemoteException {
        System.out.println("All countrues: ");
        worldMapRemote.getAllCountries().forEach(c -> System.out.println(c));

        System.out.println("\n\nAll cities: ");
        worldMapRemote.getAllCities().forEach(c -> System.out.println(c));
    }
}
