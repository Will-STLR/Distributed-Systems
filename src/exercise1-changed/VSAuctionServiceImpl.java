package vsue.rmi;

import java.rmi.RemoteException;
import java.util.HashMap;

public class VSAuctionServiceImpl implements VSAuctionService{

    // alle Aktionen
    private final HashMap <VSAuction, VSAuctionEventHandler> auctions = new HashMap<>();

    /*
    1. Ein Aufruf von registerAuction() ermöglicht es einem Client,
     - eine Auktion auction zu starten,
     - die nach duration Sekunden abläuft;
    2. der Parameter handler dient der Übergabe einer (Remote-)Referenz über die der Auktionsdienst den Client später bei Ereignissen zurückrufen kann.
    3. Sollte eine Auktion mit demselben Namen bereits existieren, wird dies per VSAuctionException signalisiert
     */
    @Override
    public void registerAuction(VSAuction auction, int duration, VSAuctionEventHandler handler) throws VSAuctionException, RemoteException {
        // siehe interface
        if (duration < 0) throw new VSAuctionException("[ERROR] - \'duration\' is negative");
        // 3. Teilaufgabe:
        for(VSAuction actn: auctions.keySet()) {
            if (actn.getName().equals(auction.getName())) {
                throw new VSAuctionException("[ERROR] - Auction already registered");
            }
        }
        auctions.put(auction, handler);

        // 1. + 2. Teilaufgabe
        // TODO: Threads benutzen
        // Aus 0. Uebung:
        Thread test = new Thread(() -> {
            try {
                Thread.sleep(duration * 1000L);
                handler.handleEvent(VSAuctionEventType.AUCTION_END, auction);
                auctions.get(auction).handleEvent(VSAuctionEventType.AUCTION_WON, auction);
                auctions.remove(auction);
            } catch (InterruptedException e) {
                // for sleep
                throw new RuntimeException(e);
            } catch (RemoteException e) {
                // for handleEvent
                throw new RuntimeException(e);
            }
        });
        test.start();
    }

    /*
    Die Methode getAuctions() liefert alle Auktionen zurück, die zum Zeitpunkt des Aufrufs laufen.
     */
    @Override
    public VSAuction[] getAuctions() throws RemoteException {
        VSAuction[] result = new VSAuction[auctions.size()];
        int i = 0;
        for (VSAuction auction : auctions.keySet()) {
            result[i++] = auction;
        }
        return result;
    }    

    /*
    1. Mittels placeBid() kann ein Client userName ein neues Gebot price für eine Auktion auctionName einreichen.
    2. Am Rückgabewert lässt sich anschließend erkennen, ob der Client das aktuell höchste Gebot abgegeben hat.
    3. Existiert keine Auktion mit dem angegebenen Namen, wirft die Methode eine VSAuctionException.
     */
    @Override
    public boolean placeBid(String userName, String auctionName, int price, VSAuctionEventHandler handler) throws VSAuctionException, RemoteException {
        for(VSAuction actn: auctions.keySet()) {
            if (actn.getName().equals(auctionName)) {
                if (actn.getPrice() < price) {
                    handler.handleEvent(VSAuctionEventType.HIGHER_BID, actn);
                    actn.setPrice(price);
                    auctions.put(actn, handler);
                    return true;
                }
                return false;
            }
        }
        throw new VSAuctionException("[ERROR] - \'auctionName\' doesn't match any of the auctions");
    }
}
