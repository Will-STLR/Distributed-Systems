package exercise1;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class VSAuctionRMIClient {
    // CLASS VARIABLES
    private static VSAuctionService auctionService;
    private static VSAuctionEventHandler auctionEventHandler;

    // METHODS
    private static void runShell() throws VSAuctionException, InterruptedException, RemoteException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Register Auction");
            System.out.println("2. View Auctions");
            System.out.println("3. Place Bid");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> registerAuction(scanner);
                case 2 -> viewAuctions();
                case 3 -> placeBid(scanner);
                case 4 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
    private static void registerAuction(Scanner scanner) throws VSAuctionException, InterruptedException, RemoteException {
        System.out.println("Enter auction name:");
        String auctionName = scanner.nextLine();

        VSAuction auction = new VSAuction(auctionName);

        System.out.println("Enter auction duration in seconds:");
        int duration = scanner.nextInt();
        scanner.nextLine();

        auctionService.registerAuction(auction, duration, auctionEventHandler);
    }
    private static void viewAuctions() throws RemoteException {
        VSAuction[] array = auctionService.getAuctions();
        for (VSAuction auction : array) {
            System.out.println(auction.getName() + ": " + auction.getPrice());
        }
    }
    private static void placeBid(Scanner scanner) throws VSAuctionException, RemoteException {
        System.out.println("Enter your username:");
        String userName = scanner.nextLine();

        System.out.println("Enter the auction name to place a bid on:");
        String auctionName = scanner.nextLine();

        System.out.println("Enter the bid amount:");
        int bidAmount = scanner.nextInt();
        scanner.nextLine();

        Boolean reply = null;

        reply = auctionService.placeBid(userName, auctionName, bidAmount, auctionEventHandler);

        System.out.println("Highest Bid?:" + reply);
    }

    public static void main(String[] args) throws NotBoundException, RemoteException, VSAuctionException, InterruptedException {
        // Get VSAuctionService
        Registry registry = LocateRegistry.getRegistry("localhost", 12345);
        auctionService = (VSAuctionService) registry.lookup("auctionService");
        // Register auction event handler
        auctionEventHandler = new VSAuctionEventHandlerImpl();
        UnicastRemoteObject.exportObject(auctionEventHandler, 0);
        runShell();
    }
}
