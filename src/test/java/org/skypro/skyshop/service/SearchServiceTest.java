package org.skypro.skyshop.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.model.product.SimpleProduct;
import org.skypro.skyshop.model.search.SearchResult;
import org.skypro.skyshop.model.search.Searchable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    private StorageService storageService;
    private SearchService searchService;

    @BeforeEach
    void setUp() {
        storageService = mock(StorageService.class);
        searchService = new SearchService(storageService);
    }

    @Test
    void searchReturnsEmptyWhenNoItemsInStorage() {
        when(storageService.getAllSearchable()).thenReturn(Collections.emptyList());

        Collection<SearchResult> results = searchService.search("телефон");

        assertTrue(results.isEmpty(), "Результаты должны быть пустыми при пустом хранилище");
    }

    @Test
    void searchReturnsEmptyWhenNoMatches() {
        List<Searchable> items = List.of(
                new SimpleProduct(UUID.randomUUID(), "Чайник", 1200),
                new Article(UUID.randomUUID(), "Пылесос", "Лучшие модели 2023")
        );

        when(storageService.getAllSearchable()).thenReturn(items);

        Collection<SearchResult> results = searchService.search("смартфон");

        assertTrue(results.isEmpty(), "Результаты должны быть пустыми при отсутствии совпадений");
    }

    @Test
    void searchReturnsMatchWhenAvailable() {
        SimpleProduct product = new SimpleProduct(UUID.randomUUID(), "Смартфон", 15000);
        Article article = new Article(UUID.randomUUID(), "смартфон", "Новинки 2024");

        List<Searchable> items = List.of(product, article);

        when(storageService.getAllSearchable()).thenReturn(items);

        Collection<SearchResult> results = searchService.search("смарТфОн");

        assertEquals(2, results.size(), "Должны быть найдены оба совпадения независимо от регистра");
        assertTrue(results.stream().anyMatch(r -> r.getName().equalsIgnoreCase("смартфон")));
    }

    @Test
    void searchIsCaseInsensitive() {
        Article article = new Article(UUID.randomUUID(), "ноУтБук", "тест");

        when(storageService.getAllSearchable()).thenReturn(List.of(article));

        Collection<SearchResult> results = searchService.search("Ноутбук");

        assertEquals(1, results.size(), "Поиск должен быть нечувствителен к регистру");
    }
}
