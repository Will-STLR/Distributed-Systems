package exercise1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VSAuctionService extends Remote {
    void registerAuction ( VSAuction auction , int duration , VSAuctionEventHandler handler ) throws RemoteException, VSAuctionException, InterruptedException;
    VSAuction [] getAuctions () throws RemoteException;
    boolean placeBid ( String userName , String auctionName , int price , VSAuctionEventHandler handler ) throws RemoteException, VSAuctionException ;
}
