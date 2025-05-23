package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BasketServiceTest {

    private ProductBasket basket;
    private StorageService storageService;
    private BasketService basketService;

    @BeforeEach
    void setUp() {
        basket = mock(ProductBasket.class);
        storageService = mock(StorageService.class);
        basketService = new BasketService(basket, storageService);
    }

    @Test
    void addNonExistingProductThrowsException() {
        UUID invalidId = UUID.randomUUID();
        when(storageService.getProductById(invalidId)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class,
                () -> basketService.addProductToBasket(invalidId));
    }

    @Test
    void addExistingProductCallsBasketAddProduct() {
        UUID validId = UUID.randomUUID();
        Product product = new SimpleProduct(validId, "Часы", 1000);
        when(storageService.getProductById(validId)).thenReturn(Optional.of(product));

        basketService.addProductToBasket(validId);

        verify(basket, times(1)).addProduct(validId);
    }

    @Test
    void getUserBasketReturnsEmptyBasket() {
        when(basket.getAllProducts()).thenReturn(Collections.emptyMap());

        UserBasket result = basketService.getUserBasket();

        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotal());
    }

    @Test
    void getUserBasketReturnsCorrectItemsAndTotal() {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();

        Product p1 = new SimpleProduct(id1, "Книга", 500);
        Product p2 = new SimpleProduct(id2, "Ручка", 100);

        Map<UUID, Integer> basketItems = Map.of(
                id1, 2,
                id2, 3
        );

        when(basket.getAllProducts()).thenReturn(basketItems);
        when(storageService.getProductById(id1)).thenReturn(Optional.of(p1));
        when(storageService.getProductById(id2)).thenReturn(Optional.of(p2));

        UserBasket result = basketService.getUserBasket();

        assertEquals(2, result.getItems().size());
        assertEquals(1300, result.getTotal());
    }

    @Test
    void getUserBasketThrowsIfProductIdNotInStorage() {
        UUID id = UUID.randomUUID();
        when(basket.getAllProducts()).thenReturn(Map.of(id, 1));
        when(storageService.getProductById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchProductException.class,
                () -> basketService.getUserBasket());
    }
}
