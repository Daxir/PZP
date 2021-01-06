import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        return new EqualsBuilder()
                .append(price, purchase.price)
                .append(quantity, purchase.quantity)
                .append(name, purchase.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(price)
                .append(quantity)
                .toHashCode();
    }

    @Override
    public String toString() {
        return getName() + " " + getPrice() + " x " + getQuantity();
    }
}
