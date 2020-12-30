import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Receipt implements Serializable {
    private final String ShopName;
    private final List<String> tags;
    private final List<Purchase> purchases;
    private final String scan;
    //dodac datę zakupu
    private final LocalDate purchaseDate;
    private final LocalDate addedDate;

    public Receipt(String shopName, List<String> tags, List<Purchase> purchases, String scan, LocalDate purchaseDate) {
        if (shopName.length() == 0) {
            throw new IllegalArgumentException("Invalid shop name!");
        }
        ShopName = shopName;
        this.tags = tags;
        this.purchases = purchases;
        //dodac wyjątek sprawdzający istnienie pliku
        this.scan = scan;
        this.purchaseDate = purchaseDate;
        this.addedDate = LocalDate.now();
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Purchase p: getPurchases()) {
            totalPrice += p.getTotalPurchaseValue();
        }
        return totalPrice;
    }

    public String getShopName() {
        return ShopName;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public String getScan() {
        return scan;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public LocalDate getAddedDate() {
        return addedDate;
    }
}
