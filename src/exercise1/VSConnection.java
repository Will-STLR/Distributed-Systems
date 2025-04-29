package exercise1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class VSConnection {
    private int chunkLength = -1;

    public void sendChunk(byte[] chunk) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        chunkLength = chunk.length;
        try {
            out.write(chunk, 0, chunk.length);
            out.flush();
            out.close();
        }catch (NullPointerException | IndexOutOfBoundsException e) {
            throw new IOException("ERROR: Chunk is empty");
        }catch (IOException e) {
            throw new IOException("ERROR: IO/Exception {close or flush}");
        }
    }
    public byte[] receiveChunk() {
        // TODO: Blockieren biss agnzees PPacket vom Outputstream gekommen ist.
        ByteArrayInputStream in = new ByteArrayInputStream(new byte[0]); // TODO: Make size big enough for input
        ByteBuffer buffer = ByteBuffer.allocate(chunkLength);
        while (in.read() != -1) {
            buffer.put((byte) in.read());
        }
        return buffer.array();
    }
}
