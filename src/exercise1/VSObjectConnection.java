package exercise1;

import java.io.*;

public class VSObjectConnection {
    private final VSConnection connection;

    public VSObjectConnection(VSConnection connection) {
        this.connection = connection;
    }

    // Serialisieren : Object in Format das übertragen werden kann (z.B. Bytes) Object -> Stream -> Byte
    public void sendObject(Serializable object) throws IOException {
        // ByteArrayOutputStream konvertiert Stream zu Byte.
        // Stream -> Byte
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        // ObjectOutputStream konvertiert Object zu Stream. Gleich auf byteOut schreiben.
        // Object -> Stream
        ObjectOutputStream objectOut = new ObjectOutputStream(byteOut); // Bei OutputStreams sagt der Parameter auf welchen anderen OutputStream es geschrieben werden soll.
        // Auf objectOut schreiben.
        objectOut.writeObject(object);
        objectOut.flush();
        // Das was in byteOut steht per VSConnection senden.
        connection.sendChunk(byteOut.toByteArray());

        // object -> objectOut[Stream] -> byteOut[Byte] -> VSConnection[byte[] chunk]
    }

    public Serializable receiveObject() throws IOException, ClassNotFoundException {
        // Chunk von VSConnection holen
        byte[] chunk = connection.receiveChunk();
        // Byte Array -> Stream
        ByteArrayInputStream byteIn = new ByteArrayInputStream(chunk); // Bei InputStreams sagt der Parameter was in den InputStream geschrieben werden soll.
        // Stream -> Object
        ObjectInputStream objectIn = new ObjectInputStream(byteIn);
        return (Serializable) objectIn.readObject(); // Vom objectIn muss das Objekt noch gelesen werden.
    }
}
