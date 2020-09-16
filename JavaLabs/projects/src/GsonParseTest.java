import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class GsonParseTest {
    public static void main(String[] args) {

        Gson gson = new Gson();
        DepopProductRequest request = new DepopProductRequest();
        request.variants = new HashMap<>();
        request.variants.put("test", 1);
        System.out.println(gson.toJson(request));
    }
}

class DepopProductRequest {
    @SerializedName("price_amount")
    public String priceAmount;
    @SerializedName("hand_delivery")
    private boolean handDelivery;
    @SerializedName("national_shipping_cost")
    public String nationalShippingCost;
    @SerializedName("international_shipping_cost")
    public String internationalShippingCost;
    @SerializedName("brand_id")
    public Integer brandId;
    public String address;
    public int[] categories;
    public String description;
    private Integer quantity;
    public String[] pictures;
    @SerializedName("video_ids")
    private int[] videoIds = null;
    @SerializedName("share_on_tw")
    private boolean shareOnTw = false;
    @SerializedName("purchase_via_paypal")
    private boolean purchaseViaPaypal = true;
    @SerializedName("shipping_methods")
    private Object shippingMethods = null;
    @SerializedName("price_currency")
    private String priceCurrency = "USD";
    private boolean shippable = true;
    public HashMap<String, Integer> variants = new HashMap<>();
}
