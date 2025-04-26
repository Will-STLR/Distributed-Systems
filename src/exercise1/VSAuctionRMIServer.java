package exercise1;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class VSAuctionRMIServer {

    // METHODS

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {
        // Auktionsdienst instanziieren
        VSAuctionServiceImpl auctionServiceImpl = new VSAuctionServiceImpl();

        // Auktionsdienst als Remote-Objekt exportieren
        VSAuctionService auctionService = (VSAuctionService) UnicastRemoteObject.exportObject(auctionServiceImpl, 0);

        // Remote-Objekt mittels Registry bekannt machen
        Registry registry = LocateRegistry.createRegistry(12345);
        registry.bind("auctionService", auctionService);

        // Prozess weiterlaufen lassen
        Thread.sleep(Long.MAX_VALUE);
    }
}
