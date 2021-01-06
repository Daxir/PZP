import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceiptRepository implements Subject, Serializable {
    private List<Receipt> receipts = new ArrayList<>();
    private final List<Observer> observers = new ArrayList<>();

    public List<Receipt> getRepository() {
        return receipts;
    }

    public boolean add(Receipt r){
        return receipts.add(r);
    }

    public boolean remove(Receipt r) {
        return receipts.remove(r);
    }

    public Receipt get(int index) {
        return receipts.get(index);
    }

    public List<Receipt> getAll() {
        return List.copyOf(receipts);
    }

    public Receipt find(String shopName) {
        for (Receipt r : receipts) {
            if (r.getShopName().equals(shopName)) {
                return r;
            }
        }
        return null;
    }

    public Receipt find(LocalDate date) {
        for (Receipt r : receipts) {
            if (r.getPurchaseDate().equals(date)) {
                return r;
            }
        }
        return null;
    }

    public List<Receipt> findAll(String shopName) {
        List<Receipt> found = new ArrayList<>();
        for (Receipt r : receipts) {
            if ( r.getShopName().equals(shopName)) {
                found.add(r);
            }
        }
        if (found.size() == 0) {
            return null;
        } else {
            return found;
        }
    }

    public List<Receipt> findAll(LocalDate date) {
        List<Receipt> found = new ArrayList<>();
        for (Receipt r : receipts) {
            if ( r.getPurchaseDate().equals(date)) {
                found.add(r);
            }
        }
        if (found.size() == 0) {
            return null;
        } else {
            return found;
        }
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
