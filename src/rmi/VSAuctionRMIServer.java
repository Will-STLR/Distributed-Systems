package vsue.rmi;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class VSAuctionRMIServer {
    // Folie 1.2:11
    // TODO: RemoteException (createRegistry, bind), 
    // TODO: AlreadyBoundException(bind), 
    // TODO: InterruptedException (sleep)

    private static int registryPort = 12345;

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException{
        // Remote-Objekt erzeugen
        VSAuctionServiceImpl auctionServiceImpl = new VSAuctionServiceImpl();
        /*
        Remote-Objekt auf Port 12678 exportieren 
        (Server stellt Anwendung als Remoteobjekt bereit)
        */
        VSAuctionService auctionService = (VSAuctionService) UnicastRemoteObject.exportObject(auctionServiceImpl, 12678);
        // Auktionsdienst muss über eine Registry bekannt gemacht werden
        Registry registry = LocateRegistry.createRegistry(registryPort);
        registry.bind("auctionService", auctionService);

        System.out.println("Server started at port " + registryPort);

        // Prozess weiterlaufen lassen
        Thread.sleep(Long.MAX_VALUE);
    }
}


