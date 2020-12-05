import java.util.List;

public class Receipt {
    private String TotalPrice;
    private String ShopName;
    private List<String> tag;
    private String scan;

    public String getTotalPrice() {
        return TotalPrice;
    }

    public String getShopName() {
        return ShopName;
    }

    public List<String> getTag() {
        return tag;
    }

    public String getScan() {
        return scan;
    }
}
