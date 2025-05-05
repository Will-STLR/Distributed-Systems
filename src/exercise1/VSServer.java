package exercise1;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

public class VSServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(() -> {
                try {
                    VSConnection connection = new VSConnection(clientSocket);
                    VSObjectConnection objectConnection = new VSObjectConnection(connection);

                    while (true) {
                        Serializable object = objectConnection.receiveObject();
                        objectConnection.sendObject(object);
                    }
                } catch (Exception e) {
                    System.out.println("Client hat Verbindung unterbrochen");
                }
            }).start();
        }
    }
}
