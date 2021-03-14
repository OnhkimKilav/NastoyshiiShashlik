
package com.example.nastoyshiishashlik.api.model.responseOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("incoming_order_id")
    @Expose
    private Integer incomingOrderId;
    @SerializedName("spot_id")
    @Expose
    private Integer spotId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("client_id")
    @Expose
    private Integer clientId;
    @SerializedName("first_name")
    @Expose
    private Object firstName;
    @SerializedName("last_name")
    @Expose
    private Object lastName;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("sex")
    @Expose
    private Object sex;
    @SerializedName("birthday")
    @Expose
    private Object birthday;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("comment")
    @Expose
    private Object comment;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("transaction_id")
    @Expose
    private Object transactionId;
    @SerializedName("service_mode")
    @Expose
    private Integer serviceMode;
    @SerializedName("delivery_price")
    @Expose
    private Integer deliveryPrice;
    @SerializedName("fiscal_spreading")
    @Expose
    private Integer fiscalSpreading;
    @SerializedName("fiscal_method")
    @Expose
    private String fiscalMethod;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public Integer getIncomingOrderId() {
        return incomingOrderId;
    }

    public void setIncomingOrderId(Integer incomingOrderId) {
        this.incomingOrderId = incomingOrderId;
    }

    public Integer getSpotId() {
        return spotId;
    }

    public void setSpotId(Integer spotId) {
        this.spotId = spotId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getSex() {
        return sex;
    }

    public void setSex(Object sex) {
        this.sex = sex;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(Object comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getServiceMode() {
        return serviceMode;
    }

    public void setServiceMode(Integer serviceMode) {
        this.serviceMode = serviceMode;
    }

    public Integer getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Integer deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Integer getFiscalSpreading() {
        return fiscalSpreading;
    }

    public void setFiscalSpreading(Integer fiscalSpreading) {
        this.fiscalSpreading = fiscalSpreading;
    }

    public String getFiscalMethod() {
        return fiscalMethod;
    }

    public void setFiscalMethod(String fiscalMethod) {
        this.fiscalMethod = fiscalMethod;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
