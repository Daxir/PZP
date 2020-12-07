import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReceiptRepository implements Subject, Serializable {
    private List<Receipt> receipts;
    private final List<Observer> observers = new ArrayList<>();

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

    public void attach(Observer o) {
        observers.add(o);
    }

    public void detach(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}
