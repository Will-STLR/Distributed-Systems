package exercise1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VSAuctionEventHandler extends Remote {
    void handleEvent(VSAuctionEventType event, VSAuction auction) throws RemoteException;
}
