import java.util.List;

public class Receipt {
    private double TotalPrice;
    private final String ShopName;
    private final List<String> tags;
    private final List<Purchase> purchases;
    private final String scan;

    public Receipt(String shopName, List<String> tags, List<Purchase> purchases, String scan) {
        if (shopName.length() == 0) {
            throw new IllegalArgumentException("Invalid shop name!");
        }
        ShopName = shopName;
        this.tags = tags;
        this.purchases = purchases;
        //dodac wyjątek sprawdzający istnienie pliku
        this.scan = scan;
    }

    public double getTotalPrice() {
        return TotalPrice;
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
}
