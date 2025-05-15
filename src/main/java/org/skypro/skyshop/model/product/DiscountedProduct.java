package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {

    private final int basePrice;
    private final int discount;

    public DiscountedProduct(UUID id, String productName, int basePrice, int discount) {
        super(id, productName);
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Цена продукта должна быть строго больше 0");
        }
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Скидка должна быть в диапазоне от 0 до 100 включительно.");
        }
        this.basePrice = basePrice;
        this.discount = discount;

    }

    @Override
    public int getPrice() {
        return basePrice - (basePrice * discount / 100);
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public String toString() {
        return getProductName() + ": " + getPrice() + " (" + discount + "%)";
    }
}
