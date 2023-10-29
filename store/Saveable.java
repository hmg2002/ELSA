package store;

import java.io.IOException;
import java.io.BufferedWriter;

public interface Saveable {
    public abstract void save(BufferedWriter bw) throws IOException;
}
