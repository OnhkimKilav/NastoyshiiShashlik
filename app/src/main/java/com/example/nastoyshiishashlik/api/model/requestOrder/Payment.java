
package com.example.nastoyshiishashlik.api.model.requestOrder;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("sum")
    @Expose
    private String sum;
    @SerializedName("currency")
    @Expose
    private String currency;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
