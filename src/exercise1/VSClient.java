package exercise1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Arrays;

public class VSClient {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        Socket socket = new Socket("localhost", 12345);
        VSConnection connection = new VSConnection(socket);
        VSObjectConnection objectConnection = new VSObjectConnection(connection);

        Serializable[] test = {"String", 100, 100.10, new VSAuction("Auction"), new int[]{1, 2, 3, 4, 5, 6,}};

        for (Serializable object : test) {
            objectConnection.sendObject(object);
            Serializable reply = objectConnection.receiveObject();
            /*System.out.println("Gesendet: " + object);
            System.out.println("Bekommen: " + reply);
            System.out.println("Gleich?: " + Objects.deepEquals(object, reply)); // TODO: Doesn't work with Objects, unless you overwrite the equals() and hashcode() methods... Alternative is to compare the byte arrays, aber umständliche Immplementierung.
            */
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
            objectOut.writeObject(object);
            objectOut.flush();
            byte[] obj1 = byteOut.toByteArray();

            ByteArrayOutputStream byteOut2 = new ByteArrayOutputStream();
            ObjectOutputStream objectOut2 = new ObjectOutputStream(byteOut2);
            objectOut2.writeObject(reply);
            objectOut2.flush();
            byte[] obj2 = byteOut2.toByteArray();

            System.out.println("Gesendet: " + object);
            System.out.println("Bekommen: " + reply);
            System.out.println("Gleich?: " + Arrays.equals(obj1, obj2));
        }

        socket.close();
    }
}
