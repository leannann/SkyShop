package org.skypro.skyshop.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Objects;
import java.util.UUID;

public abstract class Product implements Searchable {
    private final UUID id;
    private final String productName;

    public Product(UUID id, String productName) {
        if (productName == null || productName.isBlank())
            throw new IllegalArgumentException("Название продукта не может быть пустым или состоять только из пробелов");
        this.productName = productName;
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public abstract int getPrice();
    public abstract boolean isSpecial();

    @Override
    @JsonIgnore
    public String getSearchTerm() {
        return productName;
    }

    @Override
    @JsonIgnore
    public String getContentType() {
        return "PRODUCT";
    }

    @Override
    public String getName(){
        return  productName;
    }

    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productName);
    }
}
