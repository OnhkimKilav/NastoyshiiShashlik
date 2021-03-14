
package com.example.nastoyshiishashlik.api.model.requestOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("modificator_id")
    @Expose
    private String modificatorId;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("price")
    @Expose
    private String price;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getModificatorId() {
        return modificatorId;
    }

    public void setModificatorId(String modificatorId) {
        this.modificatorId = modificatorId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
