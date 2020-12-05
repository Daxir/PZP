import java.io.Serializable;

public class Purchase implements Serializable {
    private final String name;
    private final double price;
    private final int quantity;

    public Purchase(String name, double price, int quantity) {
        if (price <= 0) {
            throw new NumberFormatException("Invalid price!");
        }
        if (quantity <= 0) {
            throw new NumberFormatException("Invalid quantity!");
        }
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPurchaseValue() {
        return getPrice() * getQuantity();
    }
}
