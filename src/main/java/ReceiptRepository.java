import java.util.List;

public class ReceiptRepository {
    private List<Receipt> receipts;

    public List<Receipt> getRepository() {
        return receipts;
    }

    public boolean add(Receipt r){
        receipts.add(r);
        return receipts.add(r);
    }
}
