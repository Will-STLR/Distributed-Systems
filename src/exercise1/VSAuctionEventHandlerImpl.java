package exercise1;

import java.rmi.RemoteException;

public class VSAuctionEventHandlerImpl implements VSAuctionEventHandler {
    public void handleEvent(VSAuctionEventType event, VSAuction auction) throws RemoteException {
        switch (event) {
            case HIGHER_BID -> {
                System.out.println("Your bid on the auction " + auction.getName() + " wos overbidden.");
            }
            case AUCTION_END -> {
                System.out.println("Your auction " + auction.getName() + " has ended.");
            }
            case AUCTION_WON -> {
                System.out.println("Congratulations! You won the auction " + auction.getName() + ".");
            }
        }
    }
}
