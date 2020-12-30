import java.time.LocalDate;
import java.util.*;

public class ReceiptFactory {
    ReceiptRepository rep;
    PurchaseBuffer pbuff = new PurchaseBuffer();
    TagBuffer tbuff = new TagBuffer();

    public static class PurchaseBuffer implements Buffer<Purchase> {
        private final Set<Purchase> purchases = new HashSet<>();

        private boolean add(String name, double price, int quantity) {
            return add(new Purchase(name, price, quantity));
        }

        @Override
        public List<Purchase> get() {
            return List.copyOf(purchases);
        }

        @Override
        public boolean add(Purchase element) {
            return purchases.add(element);
        }

        @Override
        public boolean remove(Purchase element) {
            return false;
        }

        @Override
        public void flush() {
            purchases.clear();
        }

        @Override
        public int length() {
            return purchases.size();
        }
    }

    public static class TagBuffer implements Buffer<String> {
        private final Set<String> buffer = new HashSet<>();

        @Override
        public List<String> get() {
            return List.copyOf(buffer);
        }

        @Override
        public boolean add(String element) {
            return buffer.add(element);
        }

        @Override
        public boolean remove(String element) {
            return false;
        }

        @Override
        public void flush() {
            buffer.clear();
        }

        @Override
        public int length() {
            return buffer.size();
        }
    }


    public ReceiptFactory(ReceiptRepository rep) {
        this.rep = rep;
    }

    public Receipt createReceipt(String shopName, String pathToScan, String scan, LocalDate date) throws Buffer.EmptyBufferException {
        Receipt newr = new Receipt(shopName, tbuff.get(), pbuff.get(), scan, date);
        if (pbuff.length() == 0) {
            throw new Buffer.EmptyBufferException("No purchases in buffer!");
        }
        if (tbuff.length() == 0) {
            throw new Buffer.EmptyBufferException("No tags in buffer!");
        }
        rep.add(newr);
        tbuff.flush();
        pbuff.flush();
        return newr;
    }
}
