package com.example.nastoyshiishashlik.entities;

import com.example.nastoyshiishashlik.Saleable;
import com.example.nastoyshiishashlik.exceptions.ProductNotFoundException;
import com.example.nastoyshiishashlik.exceptions.QuantityOutOfRangeException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CartEntity implements Serializable {

    private static final long serialVersionUID = 42L;

    private Map<Saleable, Integer> cartItemMap = new HashMap<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private int totalQuantity = 0;
    private int delivery = 70;

    /**
     * Метод для установки цены доставки
     *
     * @param pickup переменная для подсчета доставка, если pickup == true - тогда доставка равна 0,
     *              если pickup == false - доставка учитывается
     * @return возвращает цену доставки
     */
    public int getDelivery(boolean pickup) {
        if(totalPrice.compareTo( BigDecimal.valueOf(500)) > 0 || pickup)
            delivery = 0;
        else delivery = 70;
        return delivery;
    }

    public void add(final Saleable sellable, int quantity) {
        if (cartItemMap.containsKey(sellable)) {
            cartItemMap.put(sellable, cartItemMap.get(sellable) + quantity);
        } else {
            cartItemMap.put(sellable, quantity);
        }

        totalPrice = totalPrice.add(sellable.getFinalPrice());
        totalQuantity += quantity;
    }

    public void update(final Saleable sellable, int quantity) throws ProductNotFoundException, QuantityOutOfRangeException {
        if (!cartItemMap.containsKey(sellable)) throw new ProductNotFoundException();
        if (quantity < 0)
            throw new QuantityOutOfRangeException(quantity + " is not a valid quantity. It must be non-negative.");

        int productQuantity = cartItemMap.get(sellable);
        BigDecimal price = BigDecimal.valueOf(sellable.getPrice());
        BigDecimal productPrice = price.multiply(BigDecimal.valueOf(productQuantity));

        cartItemMap.put(sellable, quantity);

        totalQuantity = totalQuantity - productQuantity + quantity;
        totalPrice = totalPrice.subtract(productPrice).add(sellable.getFinalPrice());
    }

    public void remove(final Saleable sellable, int quantity) throws ProductNotFoundException, QuantityOutOfRangeException {
        if (!cartItemMap.containsKey(sellable)) throw new ProductNotFoundException();

        int productQuantity = cartItemMap.get(sellable);

        if (quantity < 0 || quantity > productQuantity)
            throw new QuantityOutOfRangeException(quantity + " is not a valid quantity. It must be non-negative and less than the current quantity of the product in the shopping cart.");

        if (productQuantity == quantity) {
            cartItemMap.remove(sellable);
        } else {
            cartItemMap.put(sellable, productQuantity - quantity);
        }

        totalPrice = totalPrice.subtract(sellable.getFinalPrice().multiply(BigDecimal.valueOf(quantity)));
        totalQuantity -= quantity;
    }

    public void remove(final Saleable sellable) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(sellable)) throw new ProductNotFoundException();

        int quantity = cartItemMap.get(sellable);
        cartItemMap.remove(sellable);
        totalPrice = totalPrice.subtract(sellable.getFinalPrice());
        totalQuantity -= quantity;
    }

    public void clear() {
        cartItemMap.clear();
        totalPrice = BigDecimal.ZERO;
        totalQuantity = 0;
    }

    public int getQuantity(final Saleable sellable) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(sellable)) throw new ProductNotFoundException();
        return cartItemMap.get(sellable);
    }

    public BigDecimal getCost(final Saleable sellable) throws ProductNotFoundException {
        if (!cartItemMap.containsKey(sellable)) throw new ProductNotFoundException();
        return sellable.getFinalPrice().multiply(BigDecimal.valueOf(cartItemMap.get(sellable)));
    }

    public BigDecimal getTotalPrice() {
        return totalPrice.add(BigDecimal.valueOf(getDelivery(false)));
    }

    public BigDecimal getTotalPriceWithDiscount() {
        return (totalPrice.subtract(totalPrice.multiply(BigDecimal.valueOf(0.1))))
                .add(BigDecimal.valueOf(getDelivery(true)));
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public Set<Saleable> getProducts() {
        return cartItemMap.keySet();
    }

    public Map<Saleable, Integer> getItemWithQuantity() {
        Map<Saleable, Integer> cartItemMap = new HashMap<Saleable, Integer>();
        cartItemMap.putAll(this.cartItemMap);
        return cartItemMap;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        for (Map.Entry<Saleable, Integer> entry : cartItemMap.entrySet()) {
            strBuilder.append(String.format("Product: %s, Unit Price: %f, Quantity: %d%n", entry.getKey().getName(), entry.getKey().getFinalPrice(), entry.getValue()));
        }
        strBuilder.append(String.format("Total Quantity: %d, Total Price: %f", totalQuantity, totalPrice));

        return strBuilder.toString();
    }
}
