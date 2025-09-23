import java.io.IOException;
import java.io.InputStream;

public class BitInputStream implements AutoCloseable {
    private final InputStream in;
    private int currentByte = 0;
    private int numBitsRemaining = 0;
    private boolean closed = false;

    public BitInputStream(InputStream in) {
        this.in = in;
    }

    // Lê 1 bit. Retorna 0 ou 1; retorna -1 se chegou no fim do stream.
    public int readBit() throws IOException {
        if (closed) throw new IOException("BitInputStream already closed");
        if (numBitsRemaining == 0) {
            currentByte = in.read();
            if (currentByte == -1) return -1; // EOF
            numBitsRemaining = 8;
        }
        numBitsRemaining--;
        return (currentByte >>> numBitsRemaining) & 1;
    }

    @Override
    public void close() throws IOException {
        if (!closed) {
            in.close();
            closed = true;
        }
    }
}
