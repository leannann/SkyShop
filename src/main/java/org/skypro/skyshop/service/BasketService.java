package org.skypro.skyshop.service;

import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BasketService {

    private final ProductBasket basket;
    private final StorageService storageService;

    public BasketService(ProductBasket basket, StorageService storageService) {
        this.basket = basket;
        this.storageService = storageService;
    }

    public void addProductToBasket(UUID id) {
        Product product = storageService.getProductById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));
        basket.addProduct(id);
    }

    public UserBasket getUserBasket() {
        return new UserBasket(
                basket.getAllProducts().entrySet().stream()
                        .map(entry -> new BasketItem(
                                storageService.getProductById(entry.getKey())
                                        .orElseThrow(() -> new IllegalArgumentException("Товар не найден")),
                                entry.getValue()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
