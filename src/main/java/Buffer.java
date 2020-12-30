import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Buffer<E> {
    List<E> get();
    boolean add(E element);
    boolean remove(E element);
    void flush();
    int length();
    public class EmptyBufferException extends Exception {
        public EmptyBufferException(String message) {
            super(message);
        }
    }
}
