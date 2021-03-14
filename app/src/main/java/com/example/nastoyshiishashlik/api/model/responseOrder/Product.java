
package com.example.nastoyshiishashlik.api.model.responseOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("io_product_id")
    @Expose
    private Integer ioProductId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("modificator_id")
    @Expose
    private Object modificatorId;
    @SerializedName("incoming_order_id")
    @Expose
    private Integer incomingOrderId;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getIoProductId() {
        return ioProductId;
    }

    public void setIoProductId(Integer ioProductId) {
        this.ioProductId = ioProductId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Object getModificatorId() {
        return modificatorId;
    }

    public void setModificatorId(Object modificatorId) {
        this.modificatorId = modificatorId;
    }

    public Integer getIncomingOrderId() {
        return incomingOrderId;
    }

    public void setIncomingOrderId(Integer incomingOrderId) {
        this.incomingOrderId = incomingOrderId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
