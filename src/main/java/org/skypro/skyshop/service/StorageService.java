package org.skypro.skyshop.service;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.*;

import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;
import org.skypro.skyshop.model.product.Product;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;



@Service
public class StorageService {

    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        initStorage();
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    private void initStorage() {
        // Продукты
        Product product1 = new SimpleProduct(UUID.randomUUID(), "Часы", 2500);
        Product product2 = new DiscountedProduct(UUID.randomUUID(), "Смартфон", 15000, 20);
        Product product3 = new FixPriceProduct(UUID.randomUUID(), "Наушники");

        products.put(product1.getId(), product1);
        products.put(product2.getId(), product2);
        products.put(product3.getId(), product3);

        // Статьи
        Article article1 = new Article(UUID.randomUUID(), "ноутбук", "Обзор лучших ноутбуков 2024 года.");
        Article article2 = new Article(UUID.randomUUID(), "смартфон", "Новинки смартфонов весны.");
        Article article3 = new Article(UUID.randomUUID(), "часы", "Модные умные часы для всех.");

        articles.put(article1.getId(), article1);
        articles.put(article2.getId(), article2);
        articles.put(article3.getId(), article3);
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Article> getAllArticles() {
        return articles.values();
    }

    public Collection<Searchable> getAllSearchable() {
        return Stream.concat(
                products.values().stream(),
                articles.values().stream()
        ).collect(Collectors.toList());
    }
}
