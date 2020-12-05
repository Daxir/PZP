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

    public double getTotalSpendings() {
        double spendings = 0;
        for (Receipt r : receipts) {
            spendings += r.getTotalPrice();
        }
        return spendings;
    }
}
