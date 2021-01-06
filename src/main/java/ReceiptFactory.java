import java.time.LocalDate;
import java.util.*;

public class ReceiptFactory {
    private ReceiptFactory(ReceiptRepository rep) {

    }

    public static Receipt createReceipt(String shopName, List<String> tags, List<Purchase> purchases, String scan, LocalDate date) {
        if (shopName.isEmpty() || scan.isEmpty() || tags.isEmpty() || purchases.isEmpty()) {
            throw new IllegalArgumentException("At least one element provided is empty");
        }
        return new Receipt(shopName, tags, purchases, scan, date);
    }
}
