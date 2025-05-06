package exercise1;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class VSConnection {
    private final InputStream in;
    private final OutputStream out;
    private final int header = 4;

    public VSConnection(Socket socket) throws IOException {
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    public void sendChunk(byte[] chunk) throws IOException {
        // Buffer erstellen mit Platz für HEADER und CHUNK.
        // Header nötig, damit reveiceChunk() weiß, wann ein Chunk endet.
        // Buffer [ 132 Byte ]
        // [00000000  00000000  00000000 01000000[4] + chunk[128]]
        ByteBuffer buffer =  ByteBuffer.allocate(header + chunk.length);
        // Chunk Länge und Chunk in Buffer schreiben.
        buffer.putInt(chunk.length);  // In den Header = Chunk Größe
        buffer.put(chunk);
        // Buffer in Stream schreiben.
        out.write(buffer.array());
        out.flush();
    }

    public byte[] receiveChunk() throws IOException {
        // HEADER Lesen und Speichern wie groß das Chunk ist.
        // readNBytes() entfernt die Bytes aus dem InputStream
        // Byte Buffer benutzt, da Konvertierung zu Int sonst Manuell gemacht werden muss. Umständlich!!!
        int chunkLength = ByteBuffer.wrap(in.readNBytes(header)).getInt();
        // Chunk lesen und als byte[] array returnen.
        return in.readNBytes(chunkLength);
    }
}
