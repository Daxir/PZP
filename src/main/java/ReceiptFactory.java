import java.util.ArrayList;
import java.util.List;

public class ReceiptFactory {
    ReceiptRepository rep;

    public Receipt createReceipt(String shopName, List<String> tags, String pathToScan, List<Purchase> purchases, String scan) {
        Receipt newr = new Receipt(shopName, tags, purchases, scan);
        rep.add(newr);
        return newr;
    }
}
