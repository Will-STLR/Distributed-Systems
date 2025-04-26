package exercise1;

import java.rmi.RemoteException;
import java.util.HashMap;

public class VSAuctionServiceImpl implements VSAuctionService {
    private final HashMap<VSAuction, VSAuctionEventHandler> auctionList = new HashMap<>();

    @Override
    public void registerAuction(VSAuction auction, int duration, VSAuctionEventHandler handler) throws VSAuctionException, RemoteException {
        for (VSAuction a : auctionList.keySet()) {
            if(a.getName().equals(auction.getName())) {
                throw new VSAuctionException("ERROR: Auction already exist");
            }
        }
        auctionList.put(auction, handler);

        new Thread (() -> {
            try {
                Thread.sleep(duration * 1000L);
                VSAuctionEventHandler winner = auctionList.get(auction);
                handler.handleEvent(VSAuctionEventType.AUCTION_END, auction);
                winner.handleEvent(VSAuctionEventType.AUCTION_WON, auction);
                auctionList.remove(auction);
            } catch (InterruptedException | RemoteException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    @Override
    public VSAuction[] getAuctions() {
        return auctionList.keySet().toArray(new VSAuction[0]);
    }

    @Override
    public boolean placeBid(String userName, String auctionName, int price, VSAuctionEventHandler handler) throws RemoteException, VSAuctionException {
        for (VSAuction a : auctionList.keySet()) {
            if (a.getName().equals(auctionName)) {
                if (price > a.getPrice()) {
                    a.setPrice(price);
                    auctionList.put(a, handler);
                    VSAuctionEventHandler prevWinner = auctionList.get(a);
                    prevWinner.handleEvent(VSAuctionEventType.HIGHER_BID, a);
                    return true;
                }else {
                    return false;
                }
            }
        }
        throw new VSAuctionException("ERROR: No such action available");
    }
}
