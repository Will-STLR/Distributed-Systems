package exercise1;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class VSAuctionRMIServer {
    // METHODS
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {
        // Auktionsdienst instanziieren: Hier brauchen wir keine Klassevariable von VSAuctionService, da wir nur einen
        // Server haben mit einem AuctionSrvice
        VSAuctionServiceImpl auctionServiceImpl = new VSAuctionServiceImpl();
        // Auktionsdienst als Remote-Objekt exportieren: Der hoehrt auf port 0, ob eingehende anrufe da sind.
        // Ports koennen hier sowohl beim Client als auch beim Server 0 sein, solang wir nicht in der selben Klasse
        // den selben Port verwenden, gibt es keine probleme.
        // Wichtig die auctionServiceImpl muss vom eingeltichen Datentyp sein wo das Interface sich befindet.
        // Wird auch in den Folien so gemacht
        VSAuctionService auctionService = (VSAuctionService) UnicastRemoteObject.exportObject(auctionServiceImpl, 0);
        // Remote-Objekt mittels Registry bekannt machen
        Registry registry = LocateRegistry.createRegistry(12345);
        // Hier geben wir der registry die Informationen ueber unsere komplette auctionServiceImpl, sodass alles
        // was die Klasse besitzt per remote von anderen Rechnern verwendet werden kann.
        registry.bind("auctionService", auctionService);
        // Prozess weiterlaufen lassen
        Thread.sleep(Long.MAX_VALUE);
    }
}
