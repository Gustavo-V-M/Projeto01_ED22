import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream implements AutoCloseable {
    private final OutputStream out;
    private int currentByte = 0;
    private int numBitsFilled = 0;
    private boolean closed = false;

    public BitOutputStream(OutputStream out) {
        this.out = out;
    }

    public void writeBit(int bit) throws IOException {
        if (closed) throw new IOException("BitOutputStream already closed");
        if (bit != 0 && bit != 1) throw new IllegalArgumentException("bit must be 0 or 1");
        currentByte = (currentByte << 1) | bit;
        numBitsFilled++;
        if (numBitsFilled == 8) {
            out.write(currentByte);
            numBitsFilled = 0;
            currentByte = 0;
        }
    }

    private void flushCurrentByteWithZeros() throws IOException {
        if (numBitsFilled > 0) {
            currentByte <<= (8 - numBitsFilled);
            out.write(currentByte);
            numBitsFilled = 0;
            currentByte = 0;
        }
    }

    @Override
    public void close() throws IOException {
        if (!closed) {
            flushCurrentByteWithZeros();
            out.flush();
            closed = true;
        }
    }
}
