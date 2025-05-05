package exercise1;

import java.io.*;

public class VSObjectConnection {
    private final VSConnection connection;

    public VSObjectConnection(VSConnection connection) {
        this.connection = connection;
    }

    public void sendObject(Serializable object) throws IOException {
        // ByteArrayOutputStream konvertiert Stream zu Byte Array.
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        // ObjectOutputStream konvertiert Object zu Stream. Gleich auf byteOut schreiben.
        ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
        // Auf objectOut schreiben.
        objectOut.writeObject(object);
        objectOut.flush();
        // Das was in byteOut steht per VSConnection senden.
        connection.sendChunk(byteOut.toByteArray());
    }

    public Serializable receiveObject() throws IOException, ClassNotFoundException {
        // Chunk von VSConnection holen
        byte[] chunk = connection.receiveChunk();
        // Byte Array -> Stream
        ByteArrayInputStream byteIn = new ByteArrayInputStream(chunk);
        // Stream -> Object
        ObjectInputStream objectIn = new ObjectInputStream(byteIn);
        return (Serializable) objectIn.readObject();
    }
}
