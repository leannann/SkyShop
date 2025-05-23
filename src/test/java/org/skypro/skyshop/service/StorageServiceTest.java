package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class StorageServiceTest {

    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageService();
    }

    @Test
    void shouldReturnAllProducts() {
        Collection<Product> products = storageService.getAllProducts();

        assertNotNull(products, "Коллекция продуктов не должна быть null");
        assertFalse(products.isEmpty(), "Коллекция продуктов не должна быть пустой");
        assertEquals(3, products.size(), "Ожидается 3 продукта из initStorage()");
    }

    @Test
    void shouldReturnAllArticles() {
        Collection<Article> articles = storageService.getAllArticles();

        assertNotNull(articles, "Коллекция статей не должна быть null");
        assertFalse(articles.isEmpty(), "Коллекция статей не должна быть пустой");
        assertEquals(3, articles.size(), "Ожидается 3 статьи из initStorage()");
    }

    @Test
    void shouldReturnSearchableCombinedList() {
        Collection<Searchable> items = storageService.getAllSearchable();

        assertNotNull(items);
        assertEquals(6, items.size(), "Ожидается 3 продукта + 3 статьи");
    }

    @Test
    void shouldReturnProductByIdIfExists() {
        Product expected = storageService.getAllProducts().iterator().next();
        Optional<Product> result = storageService.getProductById(expected.getId());

        assertTrue(result.isPresent(), "Продукт должен быть найден");
        assertEquals(expected.getId(), result.get().getId(), "ID найденного продукта должен совпадать");
    }

    @Test
    void shouldReturnEmptyWhenProductIdNotFound() {
        UUID randomId = UUID.randomUUID();
        Optional<Product> result = storageService.getProductById(randomId);

        assertTrue(result.isEmpty(), "Если продукта с таким ID нет, результат должен быть пустым");
    }
}
